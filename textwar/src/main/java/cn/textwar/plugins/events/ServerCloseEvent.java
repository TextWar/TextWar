package cn.textwar.plugins.events;

import cn.textwar.plugins.Cancellable;
import cn.textwar.plugins.Event;

public class ServerCloseEvent extends Event implements Cancellable {

    public ServerCloseEvent() {
        super(ServerCloseEvent.class.getSimpleName());
    }
}
