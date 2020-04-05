package cn.textwar.plugins.events;

import cn.qqtextwar.entity.player.Player;

public class PlayerMessageEvent extends PlayerEvent {

    private String message;

    public PlayerMessageEvent(Player player,String message) {
        super(PlayerMessageEvent.class.getSimpleName(), player);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
