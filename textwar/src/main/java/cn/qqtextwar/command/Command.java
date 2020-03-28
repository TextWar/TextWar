package cn.qqtextwar.command;

import cn.qqtextwar.CommandSender;

import java.util.List;

public abstract class Command extends CommandBase {

    public Command(List<String> command,String desc) {
        super(command,desc);
    }

    public abstract String execute(CommandSender player, Command command,String[] args);

}
