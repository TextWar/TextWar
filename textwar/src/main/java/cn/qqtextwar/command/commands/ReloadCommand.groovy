package cn.qqtextwar.command.commands

import cn.qqtextwar.CommandSender
import cn.qqtextwar.command.Command

class ReloadCommand extends Command{


    ReloadCommand() {
        super(["/reload"], "reloaded")
    }

    @Override
    String execute(CommandSender player, Command command, String[] args) {
        server.reload()
        return "the server has reloaded"
    }
}
