package cn.textwar.client;

import cn.qqtextwar.Server;
import cn.textwar.client.handlers.CommandHandler;
import cn.textwar.client.handlers.ImageHandler;
import cn.textwar.client.handlers.MapHandler;
import cn.textwar.client.handlers.PlayerHandler;
import cn.textwar.protocol.ConnectServer;
import cn.textwar.protocol.Connecting;
import cn.textwar.protocol.HandlerExecutor;

import java.io.IOException;
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
    private Map<String, List<ClientThread>> playerSocketMap;

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
        executor.registerHandler(new ImageHandler());
    }

    @Override
    public void addStream(ClientThread socket) throws IOException {
        if(isSplittingMode()){
            if(playerSocketMap.get(socket.getSocket().getInetAddress().getHostName()).size()==1) {
                super.addStream(socket);
            }
        }else{
            super.addStream(socket);
        }
    }

    @Override
    public void whenJoin(ClientThread socket) {
        if (!playerSocketMap.containsKey(socket.getSocket().getInetAddress().getHostName())){
            List<ClientThread> sockets = new ArrayList<>();
            sockets.add(socket);
            playerSocketMap.put(socket.getSocket().getInetAddress().getHostName(),sockets);
        }else{
            playerSocketMap.get(socket.getSocket().getInetAddress().getHostName()).add(socket);
        }
    }

    public Map<String,List<ClientThread>> getPlayerSocketMap() {
        return playerSocketMap;
    }


    @Override
    public boolean heartBeat(ClientThread thread) {
        try{
            String ip = thread.getSocket().getInetAddress().getHostName();
            if(isSplittingMode()){
                if(playerSocketMap.get(ip).get(0).getProperties().get("heartbeat") == null){
                    playerSocketMap.get(ip).get(0).getProperties().put("heartbeat",false);
                    if(thread.equals(playerSocketMap.get(ip).get(0))){
                        return true;
                    }
                }
                if(playerSocketMap.get(ip).size() == 2) {
                    playerSocketMap.get(ip).get(1).write(ALIVE);
                }
            }else{
                playerSocketMap.get(ip).get(0).write(ALIVE);
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }


}
