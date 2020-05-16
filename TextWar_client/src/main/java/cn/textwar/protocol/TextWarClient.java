package cn.textwar.protocol;

import cn.textwar.TextWar;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TextWarClient {

    public static final int HEARTBEAT = 101;

    private Map<Integer,ClientHandler> handlerMap = new ConcurrentHashMap<>();

    private int serverPort;

    private String ip;

    private Socket socket;

    public TextWarClient(String ip,int port){
        this.ip = ip;
        this.serverPort = port;
        this.handlerMap.put(HEARTBEAT,new HeartBeatHandler());
    }

    public TextWarClient connect() {
        try {
            this.socket = new Socket();
            this.socket.connect(new InetSocketAddress(ip, serverPort));
        }catch (IOException e){
            e.printStackTrace();
        }
        return  this;
    }
    public void sync(){
        Protocol protocol = new Protocol();
        new Thread(()->{
           while (TextWar.isStarting){
               try {
                   JSONObject jsonObject = protocol.decode(socket.getInputStream()).getJsonObject();
                   JSONObject object = handlerMap.get((int)jsonObject.get("state")).handle(jsonObject);
                   if(object != null){
                       socket.getOutputStream().write(new TextWarProtocol().addAll(object.toJSONString()).encode());
                   }
               }catch (Exception e){
                   e.printStackTrace();
               }
           }
        });
    }
}
