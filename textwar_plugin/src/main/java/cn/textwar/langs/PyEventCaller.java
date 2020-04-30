package cn.textwar.langs;

import cn.qqtextwar.Server;

public class PyEventCaller {

    private Server server;

    public PyEventCaller(Server server) {
        this.server = server;
    }

    public void callPyEvent(Object o,int type){
        server.getEventExecutor().callEvent(new PythonEvent(o),type);
    }
}
