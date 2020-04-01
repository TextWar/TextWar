package cn.textwar.events.types;

import cn.qqtextwar.entity.player.Player;

public class PlayerJoinEvent {

    private Player player;

    public PlayerJoinEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
