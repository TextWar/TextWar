package cn.qqtextwar.command;

import cn.qqtextwar.entity.player.Player;

import java.util.List;

/**
 * 负责执行玩家的动作等交互行为
 *
 * @author MagicLu550
 */
public abstract class Action extends CommandBase {

    public Action(List<String> command,String desc) {
        super(command,desc);
    }

    public abstract void execute(Player player, String command);

}
