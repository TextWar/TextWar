package cn.textwar.plugins.events;

import cn.qqtextwar.entity.player.Player;
import cn.textwar.plugins.Event;

public abstract class PlayerEvent extends Event {

    private Player player;

    public PlayerEvent(String name,Player player) {
        super(name);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }


}
