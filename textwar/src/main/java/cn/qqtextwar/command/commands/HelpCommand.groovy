package cn.qqtextwar.command.commands

import cn.qqtextwar.CommandSender
import cn.qqtextwar.command.Command

class HelpCommand extends Command{


    HelpCommand() {
        super(["/help"],"help command")
    }

    @Override
    String execute(CommandSender player, Command command, String[] args) {
        StringBuilder builder = new StringBuilder("-------HELP----------\n")
        getServer().executor.commands.forEach{
            x,y->
                builder.append(x).append(":").append(y.description).append("\n")
        }
        return builder.toString()
    }
}
