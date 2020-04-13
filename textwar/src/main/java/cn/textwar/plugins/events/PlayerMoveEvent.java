package cn.textwar.plugins.events;

import cn.qqtextwar.command.Action;
import cn.qqtextwar.entity.player.Player;

public class PlayerMoveEvent extends PlayerActionEvent{

    private String move;

    public PlayerMoveEvent(Player player, Action action,String move) {
        super(player, action);
        this.move = move;
    }

    public String getMove() {
        return move;
    }
}
