package cn.textwar.console;

import cn.qqtextwar.CommandSender;
import cn.qqtextwar.Server;
import cn.qqtextwar.ex.ServerException;
import cn.qqtextwar.log.ServerLogger;
import cn.qqtextwar.utils.Utils;
import jline.console.ConsoleReader;

import java.io.IOException;

public class ServerConsole extends Thread implements CommandSender {

    private Server server;

    private ServerLogger logger;

    private ConsoleReader reader;

    private ConsoleConfigParser parser;

    public ServerConsole(Server server){
        this.server = server;
        this.server.getEventExecutor().registerNativeEvents(new ServerListener(this));
        this.logger = new ServerLogger();
        this.server.getRegister().register("console.cfg");
        this.parser = new ConsoleConfigParser(server.getRegister().getConfig("console.cfg"));
        boolean start = (Boolean)this.parser.getValue("console.startManager",false)[0];
        if(start)GroovyRun.runManager(server, cn.textwar.console.Utils.getProcessId());
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
        try {
            server.getExecutor().doCommand(this, ts[0], Utils.getArgs(ts));
        }catch (ServerException e){
            logger.info(e.getMessage());
        }
    }

    public ServerLogger getLogger() {
        return logger;
    }
}
