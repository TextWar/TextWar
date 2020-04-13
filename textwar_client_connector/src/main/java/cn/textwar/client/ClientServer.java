package cn.textwar.client;

import cn.qqtextwar.Server;
import cn.textwar.client.handlers.CommandHandler;
import cn.textwar.client.handlers.MapHandler;
import cn.textwar.client.handlers.PlayerHandler;
import cn.textwar.protocol.ConnectServer;
import cn.textwar.protocol.Connecting;
import cn.textwar.protocol.HandlerExecutor;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ClientServer 用于监听客户端的信息，并且将客户端进行组合，分组进行协议的解析
 * 因此要注意，TextWar协议不是一次请求一次连接，因此不再创建新的Socket。
 * 同一个服务端必须加入两个Socket。
 * 第一次加入的Socket作为Response和request互通
 * 第二次加入的Socket作为服务端广播的信息
 * @author MagicLu550
 *
 */
public class ClientServer extends ConnectServer {

    //这个用于玩家的信息发送所设计的
    private Map<String, List<Socket>> playerSocketMap;

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
    public void addStream(Socket socket) throws IOException {
        if(playerSocketMap.get(socket.getInetAddress().getHostName()).size()==1) {
            super.addStream(socket);
        }
    }

    @Override
    public void whenJoin(Socket socket) {
        if (!playerSocketMap.containsKey(socket.getInetAddress().getHostName())){
            List<Socket> sockets = new ArrayList<>();
            sockets.add(socket);
            playerSocketMap.put(socket.getInetAddress().getHostName(),sockets);
        }else{
            playerSocketMap.get(socket.getInetAddress().getHostName()).add(socket);
        }
    }

    public Map<String,List<Socket>> getPlayerSocketMap() {
        return playerSocketMap;
    }


    @Override
    public boolean heartBeat(ClientThread thread) {
        try{
            String ip = thread.getSocket().getInetAddress().getHostName();
            if(playerSocketMap.get(ip).size() == 1){
                thread.getProperties().put("heartbeat",false);
                return true;
            }
            if(playerSocketMap.get(ip).size() == 2) {
                playerSocketMap.get(ip).get(1).getOutputStream().write(ALIVE.encode());
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }


}
