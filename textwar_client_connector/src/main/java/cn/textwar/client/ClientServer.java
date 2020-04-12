package cn.textwar.client;

import cn.qqtextwar.Server;
import cn.qqtextwar.ex.ProtocolException;
import cn.textwar.client.handlers.CommandHandler;
import cn.textwar.client.handlers.MapHandler;
import cn.textwar.client.handlers.PlayerHandler;
import cn.textwar.protocol.ConnectServer;
import cn.textwar.protocol.Connecting;
import cn.textwar.protocol.HandlerExecutor;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ClientServer 用于监听客户端的信息，并且将客户端进行组合，分组进行协议的解析
 * 因此要注意，TextWar协议不是一次请求一次连接，因此不再创建新的Socket。
 * 同一个服务端不建议加入多个Socket。
 * @author MagicLu550
 *
 */
public class ClientServer extends ConnectServer {

    //这个用于玩家的信息发送所设计的
    private Map<String,Socket> playerSocketMap;

    public ClientServer(Server server, Connecting runnable,Connecting whenOut, int threads,int port,int time) {
        super(server, runnable,whenOut, threads,time);
        this.setPort(port);
        this.playerSocketMap = new ConcurrentHashMap<>();
        if(server != null) server.getLogger().info("the server port: "+getPort());
    }

    @Override
    public void registerHandlers(HandlerExecutor executor) {
        executor.registerHandler(new MapHandler());
        executor.registerHandler(new PlayerHandler());
        executor.registerHandler(new CommandHandler());
    }

    @Override
    public void whenJoin(Socket socket) {
        if (!playerSocketMap.containsKey(socket.getInetAddress().getHostName())){
            playerSocketMap.put(socket.getInetAddress().getHostName(), socket);
        }else{
            System.err.println("The Client has existed!: "+socket.getInetAddress().getHostName());
            try {
                socket.getOutputStream().write(CLOSE.encode());
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public Map<String, Socket> getPlayerSocketMap() {
        return playerSocketMap;
    }
}
