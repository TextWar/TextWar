package cn.textwar.plugins.events;

import cn.textwar.plugins.Event;

public class ServerReloadEvent extends Event {

    public ServerReloadEvent() {
        super(ServerReloadEvent.class.getSimpleName());
    }
}
