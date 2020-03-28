package cn.qqtextwar.command.commands

import cn.qqtextwar.CommandSender
import cn.qqtextwar.command.Command
import cn.qqtextwar.entity.player.Player

class ExitCommand extends Command {

    ExitCommand() {
        super(["/exit"], "player exit")
    }

    @Override
    String execute(CommandSender player, Command command, String[] args) {
        getServer().logOut(player as Player)
        return "${(player as Player).id} has exited"
    }
}
