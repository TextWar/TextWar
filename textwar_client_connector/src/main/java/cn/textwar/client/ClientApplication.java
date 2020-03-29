package cn.textwar.client;

import cn.qqtextwar.Server;
import cn.qqtextwar.api.Application;
import cn.textwar.langs.PluginServer;
import cn.textwar.plugin.event.EventExecutor;

// tcp 连接
public class ClientApplication implements Application {

    private Server server;

    private EventExecutor eventExecutor;

    @Override
    public void init(Server server) {
        this.server = server;
        this.eventExecutor = new EventExecutor();
        PluginServer.newServer(server,eventExecutor);
    }

    @Override
    public void run() {

    }

    @Override
    public void sendMessage(long qq, String message) {

    }
}
