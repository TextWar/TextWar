package cn.qqtextwar;

import cn.qqtextwar.entity.player.Player;

public interface CommandSender {

    default boolean isPlayer(){
        return this instanceof Player;
    }

    default Player toPlayer(){
        if(isPlayer()) return (Player)this;
        return null;
    }

    void sendMessage(String message);

    String getSenderName();
}
