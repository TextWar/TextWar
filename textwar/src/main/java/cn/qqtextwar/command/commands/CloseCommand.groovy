package cn.qqtextwar.command.commands

import cn.qqtextwar.CommandSender
import cn.qqtextwar.command.Command


class CloseCommand extends Command {

    CloseCommand() {
        super(["/close"],"closed")
    }

    @Override
    String execute(CommandSender player, Command command, String[] args) {
        getServer().close0(null)
        return "the server has closed"
    }
}
