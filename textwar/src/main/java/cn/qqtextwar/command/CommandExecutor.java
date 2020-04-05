package cn.qqtextwar.command;

import cn.qqtextwar.CommandSender;
import cn.qqtextwar.Server;
import cn.qqtextwar.api.Application;
import cn.qqtextwar.command.actions.MoveAction;
import cn.qqtextwar.command.commands.*;
import cn.qqtextwar.entity.player.Player;
import cn.qqtextwar.ex.ServerException;
import cn.textwar.plugins.events.CommandExecuteEvent;
import cn.textwar.plugins.events.FoundCommandEvent;

import java.util.HashMap;
import java.util.Map;

public class CommandExecutor {

    private RegisterCommand command;

    private Server server;

    public CommandExecutor(Server server){
        this.server = server;
        this.command = new RegisterCommand(server);
        registerCommands();
    }

    private void registerCommands(){
        registerCommand(new MoveAction());
        registerCommand(new UpdateMapCommand());
        registerCommand(new ExitCommand());
        registerCommand(new HelpCommand());
        registerCommand(new CloseCommand());
        registerCommand(new ReloadCommand());
    }

    private Map<String,CommandBase> commands = new HashMap<>();

    public void registerCommand(CommandBase base){
        base.initServer(server);
        base.getCommand().forEach((x)->commands.put(x,base));

    }

    //注册玩家时的命令
    public void registerPlayer(String ip, long qq, Application application){
        this.command.execute(application,ip,qq);
    }

    public String doCommandOrAction(String name,long qq,String[] args){
        Player player = server.getPlayer(qq);
        server.getEventExecutor().callEvent(new CommandExecuteEvent(name,args,player),0);
        CommandBase cmd = commands.get(name);
        if(cmd!=null){
            server.getEventExecutor().callEvent(new FoundCommandEvent(cmd),0);
            if(cmd instanceof Action){
                Action action = (Action) cmd;
                String result = action.execute(player,name);
                server.getEventExecutor().callEvent(new FoundCommandEvent(cmd),1);
                return result;
            }
            if(cmd instanceof Command){
                Command command = (Command)cmd;
                String result = command.execute(player,command,args);
                server.getEventExecutor().callEvent(new FoundCommandEvent(cmd),1);
                return result;
            }
        }else{
            player.sendMessage("No such command");
        }
        server.getEventExecutor().callEvent(new CommandExecuteEvent(name,args,player),1);
        return "";
    }

    //控制台执行用
    public void doCommand(CommandSender sender,String name,String[] args){
        server.getEventExecutor().callEvent(new CommandExecuteEvent(name,args,sender),0);
        CommandBase cmd = commands.get(name);
        if(cmd == null){
            throw new ServerException("No such command");
        }
        if(cmd instanceof Command){
            Command command = (Command)cmd;
            command.execute(sender,command,args);
        }
        server.getEventExecutor().callEvent(new CommandExecuteEvent(name,args,sender),1);
    }

    public Map<String, CommandBase> getCommands() {
        return commands;
    }
}
