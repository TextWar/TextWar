package cn.textwar.plugins.events;

import cn.qqtextwar.command.Action;
import cn.qqtextwar.entity.player.Player;
import cn.textwar.plugins.Cancellable;

public abstract class PlayerActionEvent extends PlayerEvent implements Cancellable {


    private Action action;

    public PlayerActionEvent(String name,Player player,Action action) {
        super(name, player);
        this.action = action;
    }

    public Action getAction() {
        return action;
    }
}
