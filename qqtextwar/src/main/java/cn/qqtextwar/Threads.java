package cn.qqtextwar;

import cn.qqtextwar.log.ServerLogger;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

import static cn.qqtextwar.Server.CLOSED;

public class Threads {


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
                while (server.getState().get() != CLOSED){
                    if(server.getGameMap() == null){
                        server.setGameMap(server.getMap((Integer) server.getParser().getValue(TYPE,1)[0]));
                    }
                    System.out.println(server.getState());
                    while (thread.time.get() == 3 && server.getState().get() != CLOSED){
                        if(people.get() >= 2){
                            break;
                        }
                    }
                    if(server.getState().get() != CLOSED){
                        //更新地图
                        server.updateMap(new File(server.getBaseFile(),FileRegister.getIMAGE()).toString(),server.getGameMap());
                    }
                }
                logger.info("The map thread has closed");
            }catch (Exception e){
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
