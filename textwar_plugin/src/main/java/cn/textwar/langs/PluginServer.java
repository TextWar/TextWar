package cn.textwar.langs;

import cn.qqtextwar.Server;
import cn.textwar.plugin.event.EventExecutor;
import cn.textwar.protocol.ConnectServer;
import cn.textwar.protocol.Connecting;
import cn.textwar.protocol.TextWarProtocol;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//TODO 指令交互的实现
//TODO 其他函数的实现
public class PluginServer extends ConnectServer {

    private Map<String,Handler> handlers;

    public PluginServer(Server server, EventExecutor eventExecutor, Connecting connecting){
        super(server,connecting,10,500);
        handlers = new ConcurrentHashMap<>();

        server.getRegister().register("plugin.cfg");
        PluginConfigParser parser = new PluginConfigParser(server.getRegister().getConfig("plugin.cfg"));
        eventExecutor.registerEvents(new PluginListener(this),null);
        this.setPort((int)parser.getValue("plugin.port",8760)[0]);
    }

    public void registerHandler(Handler handler){
        handler.getTypes().forEach((type)->handlers.put(type,handler));
    }

    public static PluginServer newServer(Server server,EventExecutor eventExecutor){
        return new PluginServer(server,eventExecutor,(thread,connectServer)->{
            TextWarProtocol tw = thread.whenGetProtocol();
            String type = (String) tw.getJsonObject().get("type");
            PluginServer ps = (PluginServer)connectServer;
            thread.getSocket().getOutputStream().write(
                    new TextWarProtocol().addAll(
                            ps.handlers.get(type).execute(
                                    type,tw.getJsonObject()).toJSONString()
                    ).encode()
            );
        });
    }


}
