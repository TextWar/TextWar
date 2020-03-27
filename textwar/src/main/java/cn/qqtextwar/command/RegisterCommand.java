package cn.qqtextwar.command;

import cn.qqtextwar.Server;

public class RegisterCommand {

    private Server server;

    public RegisterCommand(Server server) {
        this.server = server;
    }

    public void execute(String ip, long qq){
        //TODO 未来的数据库连接
        server.registerPlayer(ip,qq,server.getGameMap());
    }
}
