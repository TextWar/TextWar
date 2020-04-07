package cn.textwar.langs;

import cn.qqtextwar.Server;
import cn.qqtextwar.api.Application;
import cn.textwar.langs.python.Py4jServer;
import cn.textwar.plugins.EventExecutor;
import cn.textwar.protocol.*;



public class PluginServer extends ConnectServer {


    public PluginServer(Server server, EventExecutor eventExecutor, Connecting connecting, Connecting whenOut, Py4jServer py4jServer){
        super(server,connecting,whenOut,10,500);
        server.getRegister().register("plugin.groovy");
        PluginConfigParser parser = new PluginConfigParser(server.getRegister().getConfig("plugin.groovy"));
        eventExecutor.registerEvents(new PluginListener(this,py4jServer),null);
        this.setPort((int)parser.getValue("plugin.port",8760)[0]);
    }

    @Override
    public void registerHandlers(HandlerExecutor executor) {

    }

    public static PluginServer newServer(Application application,Server server, EventExecutor eventExecutor,Py4jServer py4jServer){
        return new PluginServer(server,eventExecutor,(thread,connectServer)->{
            TextWarProtocol tw = thread.whenGetProtocol();
            String type = (String) tw.getJsonObject().get("type");
            thread.getSocket().getOutputStream().write(
                    new TextWarProtocol().addAll(
                            connectServer.getHandlerExecutor()
                                    .callHandler(application,thread,type,tw.getJsonObject(),server,eventExecutor)
                                    .toJSONString()).encode()
            );
        },(thread,connectServer)->{},py4jServer);
    }


}
