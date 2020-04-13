package cn.textwar.client;

import cn.qqtextwar.Server;
import cn.qqtextwar.api.Application;
import cn.qqtextwar.entity.player.Player;
import cn.qqtextwar.sql.DAOFactory;
import cn.textwar.console.ServerConsole;
import cn.textwar.langs.PluginServer;
import cn.textwar.langs.python.Py4jServer;
import cn.textwar.plugins.Listener;
import cn.textwar.plugins.events.PlayerExitEvent;
import cn.textwar.plugins.events.PlayerMessageEvent;
import cn.textwar.protocol.ConnectServer;
import cn.textwar.protocol.Handler;
import cn.textwar.protocol.TextWarProtocol;
import cn.textwar.protocol.events.PacketReceiveEvent;
import cn.textwar.protocol.events.PacketSendEvent;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

// tcp 连接
public class ClientApplication implements Application, Listener {

    private Server server;

    private ServerConsole console;


    private ClientConfigParser parser;

    private ClientServer clientServer;

    private ClientEventExecutor eventExecutor;

    private DAOFactory daoFactory;

    @Override
    public void init(Server server) {
        this.server = server;
        this.daoFactory = server.getDaoFactory();
        this.console = new ServerConsole(server);
        server.getRegister().register("client.cfg");
        server.getRegister().createDir("python");
        File file = server.getRegister().getConfig("client.cfg");
        this.parser = new ClientConfigParser(file);
        this.server.getLogger().info("the python plugins are loading...");
        String path = parser.getHeadValue("client.pythonPath");
        File pathFile = new File(path);
        if(pathFile.exists()){
            this.eventExecutor = new ClientEventExecutor(new Py4jServer());
            this.server.setEventExecutor(eventExecutor);
            new Thread(()->{
                this.server.getLogger().info("The Python Plugin Thread has started");
                CommandUtils.execute(parser.getHeadValue("client.pythonPath"));
            }).start();
            CommandUtils.sleep(1000);
            try {
                eventExecutor.getPython().getLoader().setPluginsDir(parser.getHeadValue("client.pythonExecutorMain").trim());
                eventExecutor.getPython().getLoader().getServer(server);
                eventExecutor.getPython().getLoader().refreshPlugins();
                this.server.setExecutor(new ClientCommandExecutor(this.server, eventExecutor));
                eventExecutor.getPython().setFind(true);
            }catch (Exception e){
                this.server.getLogger().info("The python plugin executor is not found");
            }
        }else{
            this.server.getLogger().info("The python plugin executor is not found");
        }

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
            Long id = (Long) thread.getProperties().get("id");
            if(id != null){
                Player player;
                if((player = server.getPlayerReturnNull(id)) != null){
                    server.getEventExecutor().callEvent(new PlayerExitEvent(player),0);
                    thread.getServer().logOut(player);
                    server.getEventExecutor().callEvent(new PlayerExitEvent(player),1);
                    //退出的时候的，注销玩家的Socket信息;
                }
                try {
                    //发送CLOSE包
                    ((ClientServer) sc).getPlayerSocketMap().get(thread.getSocket().getInetAddress().getHostName())
                            .get(1)
                            .getOutputStream()
                            .write(ConnectServer.CLOSE.encode());
                }catch (SocketException ignore){}
                ((ClientServer)sc).getPlayerSocketMap().remove(thread.getSocket().getInetAddress().getHostName());
            }
        },(int)parser.getValue("client.maxPlayer",100)[0],(int)parser.getValue("client.port",8765)[0],100);
        clientServer.start();
    }

    /**
     * 这个是服务端的身份私聊发送给特定客户端信息
     */
    @Override
    public void sendMessage(long qq, String message) {
        try {
            Player player = server.getPlayer(qq);
            server.getEventExecutor().callEvent(new PlayerMessageEvent(player,message),0);
            Socket socket = getSocket(player.getIp());
            TextWarProtocol protocol = new TextWarProtocol();
            JSONObject messageJson = new JSONObject();
            messageJson.put("id", qq);
            messageJson.put("action", "sendToPlayer");
            messageJson.put("message", message);
            protocol.addAll(Handler.createResponse(Handler.SUCCESS, "id: " + qq + " send a message", messageJson).toJSONString());
            socket.getOutputStream().write(protocol.encode());
            server.getEventExecutor().callEvent(new PlayerMessageEvent(player,message),1);
        }catch (Exception e){
            server.getLogger().error(e.getMessage());
        }
    }



    /**
     * 这个是以玩家的身份，即id所指定的身份，发送信息，最终是广播到服务端的
     * 客户端需要接收，并将其显示在公共区域即可
     *
     */
    @Override
    public void playerChat(long qq, String message) {
        Player player = server.getPlayer(qq);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",qq);
        jsonObject.put("action","playerChat");
        jsonObject.put("message",message);
        clientServer.callMessage(new TextWarProtocol().addAll(Handler.createResponse(Handler.SUCCESS,player.getName(),jsonObject).toJSONString()));
    }

    /**
     * 服务端直接广播信息
     */
    @Override
    public void broadcast(String message) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action","broadcast");
        jsonObject.put("message",message);
        clientServer.callMessage(new TextWarProtocol().addAll(Handler.createResponse(Handler.SUCCESS,"broadcast",jsonObject).toJSONString()));
    }

    @Override
    public void reload() {
        clientServer.reload();
    }

    public DAOFactory getDaoFactory() {
        return daoFactory;
    }

    private Socket getSocket(String ip){
        return clientServer.getPlayerSocketMap().get(ip).get(1);
    }
}
