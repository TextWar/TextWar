package cn.textwar.plugins;

import cn.qqtextwar.ex.ServerException;

public class Event {

    private String name;

    private boolean isCancelled;

    public Event(String name) {
        this.name = name;
    }

    public final String getEventName() {
        return name;
    }

    //TODO 所有的0事件将支持
    public void setCancelled(boolean cancelled) {
        if(this instanceof Cancellable)
            isCancelled = cancelled;
        else throw new ServerException("this event could not be cancelled");
    }

    public boolean canCancell(){
        return this instanceof Cancellable;
    }

    public boolean isCancelled() {
        return isCancelled;
    }
}
