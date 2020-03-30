package cn.textwar.client;

import cn.qqtextwar.Server;
import cn.textwar.protocol.ConnectServer;
import cn.textwar.protocol.Connecting;
import cn.textwar.protocol.HandlerExecutor;

public class ClientServer extends ConnectServer {

    public ClientServer(Server server, Connecting runnable, int threads,int port) {
        super(server, runnable, threads,500);
        this.setPort(port);
    }

    @Override
    public void registerHandlers(HandlerExecutor executor) {

    }
}
