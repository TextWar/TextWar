package cn.qqtextwar.command.commands

import cn.qqtextwar.CommandSender
import cn.qqtextwar.command.Command
import cn.qqtextwar.command.Permission

class ReloadCommand extends Command{


    ReloadCommand() {
        super(["/reload"], "reload_cmd", Permission.ADMIN)
    }

    @Override
    String execute(CommandSender player, Command command, String[] args) {
        server.reload()
        return "the server has reloaded"
    }
}
