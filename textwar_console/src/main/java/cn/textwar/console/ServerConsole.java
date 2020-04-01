package cn.textwar.console;

import cn.qqtextwar.CommandSender;
import cn.qqtextwar.Server;
import cn.qqtextwar.log.ServerLogger;
import cn.qqtextwar.utils.Utils;
import jline.console.ConsoleReader;

import java.io.IOException;

public class ServerConsole extends Thread implements CommandSender {

    private Server server;

    private ServerLogger logger;

    private ConsoleReader reader;

    public ServerConsole(Server server){
        this.server = server;
        this.logger = new ServerLogger();
        try {
            this.reader = new ConsoleReader();
        }catch (IOException e){
            this.logger.error(e.getMessage());
        }
    }

    public void run(){
        while (server.getState().get() != Server.CLOSED){
            try{
                execute(reader.readLine(">"));
            }catch (IOException e){
                this.logger.error(e.getMessage());
            }
        }
    }

    public void execute(String cmd){
        String[] ts = cmd.split(" ");
        server.getExecutor().doCommand(this,ts[0],Utils.getArgs(ts));
    }

    public ServerLogger getLogger() {
        return logger;
    }
}
