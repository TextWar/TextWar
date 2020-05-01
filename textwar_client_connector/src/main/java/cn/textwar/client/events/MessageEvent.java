package cn.textwar.client.events;

import cn.qqtextwar.entity.player.Player;
import cn.textwar.plugins.Cancellable;
import cn.textwar.plugins.events.PlayerEvent;

public abstract class MessageEvent extends PlayerEvent implements Cancellable {

    protected String message;

    public MessageEvent(String name, String message, Player player) {
        super(name,player);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
