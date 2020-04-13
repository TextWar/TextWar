package cn.textwar.plugins.events;

import cn.qqtextwar.command.Action;
import cn.qqtextwar.entity.player.Player;

public abstract class PlayerActionEvent extends PlayerEvent{


    private Action action;

    public PlayerActionEvent( Player player,Action action) {
        super(PlayerActionEvent.class.getSimpleName(), player);
        this.action = action;
    }

    public Action getAction() {
        return action;
    }
}
