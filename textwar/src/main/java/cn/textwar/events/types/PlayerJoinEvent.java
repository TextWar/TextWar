package cn.textwar.events.types;


import cn.qqtextwar.entity.player.Player;

public class PlayerJoinEvent extends PlayerEvent {


    public PlayerJoinEvent(Player player) {
        super(PlayerJoinEvent.class.getSimpleName(),player);
    }
}
