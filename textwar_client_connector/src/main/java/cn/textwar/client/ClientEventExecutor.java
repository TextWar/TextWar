package cn.textwar.client;

import cn.qqtextwar.entity.player.Player;
import cn.textwar.plugins.Event;
import cn.textwar.plugins.EventExecutor;
import cn.textwar.langs.python.Py4jServer;
import cn.textwar.plugins.events.CommandExecuteEvent;


public class ClientEventExecutor extends EventExecutor {

    private Py4jServer server;

    public ClientEventExecutor(Py4jServer server){
        this.server = server;
    }

    @Override
    public void callEvent(final Event event,final int type) {
        try {
            super.callEvent(event, type);
            if(server.isFind()){
                server.getLoader().callEvent(event.getEventName(), type,event);
                if(event instanceof CommandExecuteEvent && !event.isCancelled()){
                    if(((CommandExecuteEvent) event).getSender() instanceof Player) {
                        server.getLoader().commandExecutor(((CommandExecuteEvent) event).getCommandName(),((CommandExecuteEvent) event).getArgs(),(Player) ((CommandExecuteEvent) event).getSender());
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Py4jServer getPython() {
        return server;
    }
}
