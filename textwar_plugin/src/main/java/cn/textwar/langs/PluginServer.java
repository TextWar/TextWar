package cn.textwar.langs;

import cn.qqtextwar.Server;
import cn.textwar.events.EventExecutor;
import cn.textwar.protocol.*;


//TODO 指令交互的实现
//TODO 其他函数的实现
public class PluginServer extends ConnectServer {


    public PluginServer(Server server, EventExecutor eventExecutor, Connecting connecting){
        super(server,connecting,10,500);
        server.getRegister().register("plugin.cfg");
        PluginConfigParser parser = new PluginConfigParser(server.getRegister().getConfig("plugin.cfg"));
        eventExecutor.registerEvents(new PluginListener(this),null);
        this.setPort((int)parser.getValue("plugin.port",8760)[0]);
    }

    @Override
    public void registerHandlers(HandlerExecutor executor) {

    }

    public static PluginServer newServer(Server server, EventExecutor eventExecutor){
        return new PluginServer(server,eventExecutor,(thread,connectServer)->{
            TextWarProtocol tw = thread.whenGetProtocol();
            String type = (String) tw.getJsonObject().get("type");
            thread.getSocket().getOutputStream().write(
                    new TextWarProtocol().addAll(
                            connectServer.getHandlerExecutor()
                                    .callHandler(thread,type,tw.getJsonObject(),server,eventExecutor)
                                    .toJSONString()).encode()
            );
        });
    }


}
