package cn.textwar.plugins.events;


import cn.qqtextwar.CommandSender;

public class CommandExecuteEvent extends CommandEvent {

    private String commandName;

    private String[] args;

    private CommandSender sender;

    public CommandExecuteEvent(String name,String[] args,CommandSender sender) {
        super(CommandExecuteEvent.class.getSimpleName());
        this.commandName = name;
        this.args = args;
        this.sender = sender;
    }

    public String getCommandName() {
        return commandName;
    }

    public String[] getArgs() {
        return args;
    }

    public CommandSender getSender() {
        return sender;
    }
}
