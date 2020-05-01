package cn.textwar.client.events;

import cn.qqtextwar.entity.player.Player;

public class PlayerChatEvent extends MessageEvent {

    private Player player;

    public PlayerChatEvent(Player player,String message) {
        super(PlayerChatEvent.class.getSimpleName(),message,player);
        this.player = player;
    }

    public String getMessage() {
        return message;
    }


    public Player getPlayer() {
        return player;
    }

    public void setMessage(String message){
        this.message = message;
    }
}
