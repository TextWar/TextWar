package cn.textwar.plugins.events;


import cn.qqtextwar.entity.player.Player;

public class PlayerJoinEvent extends PlayerEvent {


    public PlayerJoinEvent(Player player) {
        super(PlayerJoinEvent.class.getSimpleName(),player);
    }
}
