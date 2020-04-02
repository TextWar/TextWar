package cn.qqtextwar;

import cn.qqtextwar.api.Application;
import cn.qqtextwar.log.ServerLogger;
import cn.textwar.events.types.MapLoadEvent;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

import static cn.qqtextwar.Server.CLOSED;

public class Threads {

    static class ApplicationRunThread extends Thread{

        private Server server;

        private Application application;

        public ApplicationRunThread(Server server, Application application){
            this.server = server;
            this.application = application;
        }

        @Override
        public void run() {
            try {
                application.init(server);
                application.run();
            }catch (Throwable e){
                server.close0(e);
            }
        }
    }


    static class MapThread extends Thread{

        public static final String TYPE = "server.game.type";

        private ServerLogger logger = new ServerLogger();

        private Server server;

        private AtomicInteger people;

        private TimeThread thread;

        public MapThread(Server server){
            this.server = server;
            this.people = new AtomicInteger(0);
            this.thread = new TimeThread();
            this.thread.start();
        }

        public void wantUpdate(){
            people.addAndGet(1);
        }

        @Override
        public void run() {
            try{
                //server.preparePicture();//更新图片
                while (server.getState().get() != CLOSED){
                    //若为null，则获得，获得不了，则报错，关闭服务端
                    if(server.getGameMap() == null){
                        server.setGameMap(server.getMap((Integer) server.getParser().getValue(TYPE,1)[0]));
                        server.getEventExecutor().callEvent(new MapLoadEvent(server.getGameMap()),1);
                    }
                    while (thread.time.get() == 3 && server.getState().get() != CLOSED){
                        if(people.get() >= 2){
                            break;
                        }
                    }
                    if(server.getState().get() != CLOSED){
                        //更新地图
                        server.putImage(server.updateMap(new File(server.getBaseFile(),FileRegister.getIMAGE()).toString(),server.getGameMap())+".jpg");
                    }
                }
                logger.info(server.translate("map_thread_closed"));
            }catch (Exception e){
                server.close0(e);
                e.printStackTrace();
            }
        }

        static class TimeThread extends Thread{

            private AtomicInteger time;

            TimeThread(){
                this.time = new AtomicInteger();
            }


            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(1000);
                        time.addAndGet(1);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
