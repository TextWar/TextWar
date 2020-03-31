package cn.textwar.client;

import cn.qqtextwar.Server;
import cn.qqtextwar.api.Application;
import cn.textwar.langs.PluginServer;
import cn.textwar.plugin.event.EventExecutor;
import cn.textwar.protocol.TextWarProtocol;
import com.alibaba.fastjson.JSONObject;

import java.io.File;

// tcp 连接
public class ClientApplication implements Application {

    private Server server;

    private EventExecutor eventExecutor;

    private ClientConfigParser parser;

    @Override
    public void init(Server server) {
        this.server = server;
        this.eventExecutor = new EventExecutor();
        server.getRegister().register("client.cfg");
        File file = server.getRegister().getConfig("client.cfg");
        this.parser = new ClientConfigParser(file);
    }

    @Override
    public void run() {
        PluginServer.newServer(server,eventExecutor).start();
        new ClientServer(server,(thread,sc)->{
            TextWarProtocol tw = thread.whenGetProtocol();
            JSONObject json = tw.getJsonObject();
            thread.getSocket().getOutputStream().write(new TextWarProtocol().addAll(sc.getHandlerExecutor().callHandler((String) json.get("type"),json,server).toJSONString()).encode());
        },(int)parser.getValue("client.port",100)[0],(int)parser.getValue("client.maxPlayer",8765)[0])
                .start();
    }

    @Override
    public void sendMessage(long qq, String message) {

    }
}
