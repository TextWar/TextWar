package cn.textwar.plugins.events;


import cn.qqtextwar.command.CommandBase;

public class FoundCommandEvent extends CommandEvent {


    private CommandBase command;
    public FoundCommandEvent(CommandBase command) {
        super(FoundCommandEvent.class.getSimpleName());
        this.command = command;
    }

    public CommandBase getCommand() {
        return command;
    }
}
