package cn.textwar.client;

import cn.qqtextwar.Server;
import cn.qqtextwar.command.CommandExecutor;

public class ClientCommandExecutor extends CommandExecutor {


    private ClientEventExecutor eventExecutor;

    public ClientCommandExecutor(Server server,ClientEventExecutor eventExecutor) {
        super(server);
        this.eventExecutor = eventExecutor;
    }

    @Override
    public String doCommandOrAction(String name, long qq, String[] args) {
        if("/help".equals(name)){
            StringBuilder builder = new StringBuilder();
            eventExecutor.getPython().getLoader().getActions().forEach(
                    (x,y)->builder.append(x+":"+y)
            );
            eventExecutor.getPython().getLoader().getCommands().forEach(
                    (x,y)->builder.append(x+":"+y)
            );
            return builder.append(super.doCommandOrAction(name, qq, args)).toString();
        }
        return super.doCommandOrAction(name, qq, args);
    }

}
