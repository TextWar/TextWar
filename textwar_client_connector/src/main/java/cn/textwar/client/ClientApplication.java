package cn.textwar.client;

import cn.qqtextwar.Server;
import cn.qqtextwar.api.Application;
import cn.qqtextwar.command.CommandExecutor;
import cn.qqtextwar.entity.player.Player;
import cn.textwar.console.ServerConsole;
import cn.textwar.langs.PluginServer;
import cn.textwar.plugins.Listener;
import cn.textwar.plugins.events.PlayerExitEvent;
import cn.textwar.protocol.TextWarProtocol;
import cn.textwar.protocol.events.PacketReceiveEvent;
import cn.textwar.protocol.events.PacketSendEvent;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.util.Arrays;

// tcp 连接
public class ClientApplication implements Application, Listener {

    private Server server;

    private ServerConsole console;


    private ClientConfigParser parser;

    private ClientServer clientServer;

    private ClientEventExecutor eventExecutor;

    @Override
    public void init(Server server) {
        this.server = server;
        this.console = new ServerConsole(server);
        server.getRegister().register("client.cfg");
        server.getRegister().createDir("python");
        File file = server.getRegister().getConfig("client.cfg");
        this.parser = new ClientConfigParser(file);
        this.eventExecutor = new ClientEventExecutor();
        this.server.setEventExecutor(eventExecutor);
        File python = this.server.getRegister().getConfig("python");
        File[] files = python.listFiles();
        if(files!=null){
            Arrays.stream(files).forEach((x)->
                    eventExecutor.getPython().getLoader().loadPlugin(x.toString())
            );
        }
        this.server.setExecutor(new CommandExecutor(server));

    }

    @Override
    public void run() {
        console.start();
        if((Boolean) parser.getValue("client.startPluginServer",false)[0]) {
            PluginServer.newServer(this, server, server.getEventExecutor(),eventExecutor.getPython()).start();
        }
        this.clientServer = new ClientServer(server,(thread,sc)->{
            TextWarProtocol tw = thread.whenGetProtocol();
            JSONObject json = tw.getJsonObject();
            server.getEventExecutor().callEvent(new PacketReceiveEvent(tw),1);
            TextWarProtocol protocol = new TextWarProtocol().addAll(sc.getHandlerExecutor().callHandler(this,thread,(String) json.get("type"),json,server,server.getEventExecutor()).toJSONString());
            server.getEventExecutor().callEvent(new PacketSendEvent(tw),1);
            thread.getSocket().getOutputStream().write(protocol.encode());
        },(thread,sc)->{
            synchronized (this){
                Long id = (Long) thread.getProperties().get("id");
                if(id != null){
                    Player player = server.getPlayer(id);
                    server.getEventExecutor().callEvent(new PlayerExitEvent(player),0);
                    thread.getServer().logOut(player);
                    server.getEventExecutor().callEvent(new PlayerExitEvent(player),1);

                }
            }
        },(int)parser.getValue("client.maxPlayer",100)[0],(int)parser.getValue("client.port",8765)[0],500);
        clientServer.start();
    }

    @Override
    public void sendMessage(long qq, String message) {
        //TODO 完成发送信息的功能
    }

    @Override
    public void reload() {
        clientServer.reload();
    }
}
