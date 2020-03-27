package cn.textwar.client;

import cn.qqtextwar.Server;
import cn.qqtextwar.api.Application;

// tcp 连接
public class ClientApplication implements Application {

    private Server server;

    @Override
    public void init(Server server) {
        this.server = server;
    }

    @Override
    public void run() {

    }

    @Override
    public void sendMessage(long qq, String message) {

    }
}
