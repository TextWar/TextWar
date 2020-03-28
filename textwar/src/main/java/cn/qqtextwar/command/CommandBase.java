package cn.qqtextwar.command;

import cn.qqtextwar.Server;

import java.util.List;

/**
 * 指令的父类,根据君和的构想，我们将指令分为Action和Command
 * 一种是偏向于动作方面，一种是偏向于非动作的执行方面，
 * 在后面实现会区分
 *
 * @author MagicLu550 @ 卢昶存
 * @author junhe-fafa
 */
public abstract class CommandBase {

    private Server server;

    protected String description;

    private List<String> command;

    public CommandBase(List<String> command,String description) {
        this.command = command;
        this.description = description;
    }

    public List<String> getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    public void initServer(Server server){
        this.server = server;
        this.description = translate(server,description);
    }

    public Server getServer() {
        return server;
    }

    public static String translate(Server server,String key){
        return server.translate(key);
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
