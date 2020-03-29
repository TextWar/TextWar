package cn.textwar.langs;

import cn.qqtextwar.Server;
import cn.textwar.plugin.event.EventExecutor;
import cn.textwar.protocol.Protocol;
import cn.textwar.protocol.TextWarProtocol;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

//TODO 指令交互的实现
//TODO 其他函数的实现
public class PluginServer extends Thread {

    private PluginConfigParser parser;

    private Executor executor;

    private List<OutputStream> streamList;

    private EventExecutor eventExecutor;

    public PluginServer(Server server, EventExecutor eventExecutor){
        server.getRegister().register("plugin.cfg");
        this.parser = new PluginConfigParser(server.getRegister().getConfig("plugin.cfg"));
        this.executor = Executors.newFixedThreadPool(10);
        this.streamList = new ArrayList<>();
        this.eventExecutor = new EventExecutor();
        this.eventExecutor.registerEvents(new PluginListener(this),null);
    }

    @Override
    public void run() {
        try {
            ServerSocket server = new ServerSocket((int)this.parser.getValue("plugin.port",8760)[0]);
            while (true){
                Socket socket = server.accept();
                streamList.add(socket.getOutputStream());
                executor.execute(new PluginThread(socket));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public List<OutputStream> getStreamList() {
        return streamList;
    }

    class PluginThread implements Runnable{

        private Socket socket;

        private Protocol protocol;

        PluginThread(Socket socket){
            this.socket = socket;
            this.protocol = new Protocol();
        }

        @Override
        public void run() {

            while (true) {
                try {
                    TextWarProtocol tw = protocol.decode(socket.getInputStream());
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
