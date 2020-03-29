package cn.textwar.langs;

import cn.qqtextwar.Server;
import cn.textwar.plugin.event.EventExecutor;
import cn.textwar.protocol.ConnectServer;
import cn.textwar.protocol.Connecting;
import cn.textwar.protocol.TextWarProtocol;
import com.alibaba.fastjson.JSONObject;

//TODO 指令交互的实现
//TODO 其他函数的实现
public class PluginServer extends ConnectServer {

    public PluginServer(Server server, EventExecutor eventExecutor, Connecting connecting){
        super(server,connecting,10);
        server.getRegister().register("plugin.cfg");
        PluginConfigParser parser = new PluginConfigParser(server.getRegister().getConfig("plugin.cfg"));
        eventExecutor.registerEvents(new PluginListener(this),null);
        this.setPort((int)parser.getValue("plugin.port",8760)[0]);
    }

    public static PluginServer newServer(Server server,EventExecutor eventExecutor){
        return new PluginServer(server,eventExecutor,(thread,cs)->{
            TextWarProtocol tw = thread.getProtocol().decode(thread.getSocket().getInputStream());
            JSONObject object = tw.getJsonObject();//TODO call Handler
        });
    }


}
