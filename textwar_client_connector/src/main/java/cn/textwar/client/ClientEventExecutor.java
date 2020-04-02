package cn.textwar.client;

import cn.textwar.plugins.Event;
import cn.textwar.plugins.EventExecutor;
import cn.textwar.langs.python.Py4jServer;


public class ClientEventExecutor extends EventExecutor {

    private Py4jServer server;

    public ClientEventExecutor(){
        this.server = new Py4jServer();
    }

    @Override
    public void callEvent(final Event event,final int type) {
        try {
            super.callEvent(event, type);
            server.getLoader().callEvent(event.getEventName(), type,event);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
