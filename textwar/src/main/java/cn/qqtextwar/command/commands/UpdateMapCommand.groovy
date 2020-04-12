package cn.qqtextwar.command.commands

import cn.qqtextwar.CommandSender
import cn.qqtextwar.command.Command
import cn.qqtextwar.command.Permission
import cn.qqtextwar.entity.player.Player


class UpdateMapCommand extends Command {

    UpdateMapCommand() {
        super(["/update"],"update_cmd", Permission.ADMIN)
    }

    @Override
    String execute(CommandSender player, Command command, String[] args) {
        if(player instanceof Player){
            getServer().wantUpdate()
        }
    }
}
