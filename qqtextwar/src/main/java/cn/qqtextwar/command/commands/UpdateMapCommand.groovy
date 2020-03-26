package cn.qqtextwar.command.commands

import cn.qqtextwar.CommandSender
import cn.qqtextwar.command.Command
import cn.qqtextwar.entity.player.Player


class UpdateMapCommand extends Command {

    UpdateMapCommand() {
        super(["update"],"a command for updating the map")
    }

    @Override
    void execute(CommandSender player, Command command, String[] args) {
        if(player instanceof Player){
            getServer().wantUpdate()
        }
    }
}
