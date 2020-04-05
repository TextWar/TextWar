package cn.qqtextwar.command;

import cn.qqtextwar.Server;
import cn.qqtextwar.api.Application;
import cn.qqtextwar.entity.player.Player;

public class RegisterCommand {

    private Server server;

    public RegisterCommand(Server server) {
        this.server = server;
    }

    public Player execute(Application application,String ip, long qq){
        return server.registerPlayer(application,ip,qq,server.getGameMap());
    }
}
