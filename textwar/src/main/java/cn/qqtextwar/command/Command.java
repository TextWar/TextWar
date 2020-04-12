package cn.qqtextwar.command;

import cn.qqtextwar.CommandSender;

import java.util.List;

public abstract class Command extends CommandBase {

    private Permission permission;

    public Command(List<String> command,String desc,Permission permission) {
        super(command,desc);
        this.permission = permission;
    }

    public Permission getPermission() {
        return permission;
    }

    public abstract String execute(CommandSender player, Command command, String[] args);

}
