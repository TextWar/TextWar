package cn.textwar.plugins.events;


import cn.textwar.plugins.Event;

public class CommandExecuteEvent extends Event {

    private String commandName;

    private String[] args;

    public CommandExecuteEvent(String name,String[] args) {
        super(CommandExecuteEvent.class.getSimpleName());
        this.commandName = name;
        this.args = args;
    }

    public String getCommandName() {
        return commandName;
    }

    public String[] getArgs() {
        return args;
    }
}
