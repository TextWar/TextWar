package cn.textwar.client;

import cn.qqtextwar.Server;
import cn.qqtextwar.api.Application;
import cn.textwar.console.ServerConsole;
import cn.textwar.langs.PluginServer;
import cn.textwar.protocol.TextWarProtocol;
import cn.textwar.protocol.events.PacketReceiveEvent;
import cn.textwar.protocol.events.PacketSendEvent;
import com.alibaba.fastjson.JSONObject;

import java.io.File;

// tcp 连接
public class ClientApplication implements Application {

    private Server server;

    private ServerConsole console;


    private ClientConfigParser parser;

    @Override
    public void init(Server server) {
        this.server = server;
        this.console = new ServerConsole(server);
        server.getRegister().register("client.cfg");
        File file = server.getRegister().getConfig("client.cfg");
        this.parser = new ClientConfigParser(file);
    }

    @Override
    public void run() {
        console.start();
        PluginServer.newServer(this,server,server.getEventExecutor()).start();
        new ClientServer(server,(thread,sc)->{
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
                    thread.getServer().logOut(server.getPlayer(id));
                }
            }
        },(int)parser.getValue("client.maxPlayer",100)[0],(int)parser.getValue("client.port",8765)[0],500)
                .start();
    }

    @Override
    public void sendMessage(long qq, String message) {

    }
}
