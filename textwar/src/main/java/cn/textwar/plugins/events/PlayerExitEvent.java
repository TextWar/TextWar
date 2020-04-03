package cn.textwar.plugins.events;

import cn.qqtextwar.entity.player.Player;

public class PlayerExitEvent extends PlayerEvent {

    public PlayerExitEvent(Player player) {
        super(PlayerExitEvent.class.getSimpleName(),player);
    }
}
