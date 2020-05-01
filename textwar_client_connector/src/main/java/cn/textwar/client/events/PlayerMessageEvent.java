package cn.textwar.client.events;

import cn.qqtextwar.entity.player.Player;

public class PlayerMessageEvent extends MessageEvent {

    private String message;

    public PlayerMessageEvent(Player player,String message) {
        super(PlayerMessageEvent.class.getSimpleName(),message, player);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
