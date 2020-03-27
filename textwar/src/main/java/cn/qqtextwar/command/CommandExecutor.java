package cn.qqtextwar.command;

import cn.qqtextwar.CommandSender;
import cn.qqtextwar.Server;
import cn.qqtextwar.command.actions.MoveAction;
import cn.qqtextwar.command.commands.UpdateMapCommand;
import cn.qqtextwar.entity.player.Player;

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
    }

    private Map<String,CommandBase> commands = new HashMap<>();

    public void registerCommand(CommandBase base){
        base.getCommand().forEach((x)->commands.put(x,base));
        base.initServer(server);
    }

    //注册玩家时的命令
    public void registerPlayer(String ip,long qq){
        this.command.execute(ip,qq);
    }

    public void doCommandOrAction(String name,long qq,String[] args){
        Player player = server.getPlayer(qq);
        CommandBase cmd = commands.get(name);
        if(cmd!=null){
            if(cmd instanceof Action){
                Action action = (Action) cmd;
                action.execute(player,name);
            }
            if(cmd instanceof Command){
                Command command = (Command)cmd;
                command.execute(player,command,args);
            }
        }else{
            player.sendMessage("No such command");
        }
    }

    //控制台执行用
    public void doCommand(CommandSender sender,String name,String[] args){
        CommandBase cmd = commands.get(name);
        if(cmd instanceof Command){
            Command command = (Command)cmd;
            command.execute(sender,command,args);
        }
    }

    public Map<String, CommandBase> getCommands() {
        return commands;
    }
}
