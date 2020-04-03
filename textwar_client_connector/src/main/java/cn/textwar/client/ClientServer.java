package cn.textwar.client;

import cn.qqtextwar.Server;
import cn.textwar.client.handlers.MapHandler;
import cn.textwar.client.handlers.PlayerHandler;
import cn.textwar.protocol.ConnectServer;
import cn.textwar.protocol.Connecting;
import cn.textwar.protocol.HandlerExecutor;

public class ClientServer extends ConnectServer {

    public ClientServer(Server server, Connecting runnable,Connecting whenOut, int threads,int port,int time) {
        super(server, runnable,whenOut, threads,time);
        this.setPort(port);
        if(server != null) server.getLogger().info("the server port: "+getPort());
    }

    @Override
    public void registerHandlers(HandlerExecutor executor) {
        executor.registerHandler(new MapHandler());
        executor.registerHandler(new PlayerHandler());
    }
}
