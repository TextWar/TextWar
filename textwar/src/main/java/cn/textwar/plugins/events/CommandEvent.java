package cn.textwar.plugins.events;

import cn.textwar.plugins.Cancellable;
import cn.textwar.plugins.Event;

public class CommandEvent extends Event implements Cancellable {

    public CommandEvent(String name) {
        super(name);
    }
}
