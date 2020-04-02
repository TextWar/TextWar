package cn.textwar.events.types;

import cn.qqtextwar.entity.player.Player;
import cn.textwar.events.Event;

public class PlayerJoinEvent extends Event {

    private Player player;

    public PlayerJoinEvent(Player player) {
        super("player_join_event");
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
