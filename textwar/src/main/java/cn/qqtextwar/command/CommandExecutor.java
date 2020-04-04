package cn.qqtextwar.command;

import cn.qqtextwar.CommandSender;
import cn.qqtextwar.Server;
import cn.qqtextwar.api.Application;
import cn.qqtextwar.command.actions.MoveAction;
import cn.qqtextwar.command.commands.*;
import cn.qqtextwar.entity.player.Player;
import cn.qqtextwar.ex.ServerException;

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
        CommandBase cmd = commands.get(name);
        if(cmd!=null){
            if(cmd instanceof Action){
                Action action = (Action) cmd;
                return action.execute(player,name);
            }
            if(cmd instanceof Command){
                Command command = (Command)cmd;
                return command.execute(player,command,args);
            }
        }else{
            player.sendMessage("No such command");
        }
        return "";
    }

    //控制台执行用
    public void doCommand(CommandSender sender,String name,String[] args){
        CommandBase cmd = commands.get(name);
        if(cmd == null){
            throw new ServerException("No such command");
        }
        if(cmd instanceof Command){
            Command command = (Command)cmd;
            command.execute(sender,command,args);
        }
    }

    public Map<String, CommandBase> getCommands() {
        return commands;
    }
}
