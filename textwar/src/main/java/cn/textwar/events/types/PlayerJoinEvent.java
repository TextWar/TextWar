package cn.textwar.events.types;

import cn.qqtextwar.entity.player.Player;
import cn.textwar.events.Event;

public class PlayerJoinEvent extends Event {

    private Player player;

    public PlayerJoinEvent(Player player) {
        super(PlayerJoinEvent.class.getSimpleName());
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
