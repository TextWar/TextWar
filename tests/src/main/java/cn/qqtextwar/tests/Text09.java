package cn.qqtextwar.tests;

import cn.textwar.protocol.Protocol;
import cn.textwar.protocol.TextWarProtocol;
import com.alibaba.fastjson.JSONObject;

import java.net.InetSocketAddress;
import java.net.Socket;

public class Text09 {

    public static void main(String[] args) throws Exception{
        Socket socket = new Socket();
        Protocol protocol = new Protocol();
        socket.connect(new InetSocketAddress("127.0.0.1",8765));
        new Thread(()->{
            while (true) {
                try {
                    TextWarProtocol protocol1 = new TextWarProtocol()
                            .addJSONCode("type", "player")
                            .addJSONCode("action", "login")
                            .addJSONCode("name", "laolu11334")
                            .addJSONCode("password", "12345")
                            .addJSONCode("rad", 5);
                    socket.getOutputStream().write(protocol1.encode());
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
        new Thread(()->{
            while(true){
                try {
                    JSONObject jsonObject = protocol.decode(socket.getInputStream()).getJsonObject();
                    if((int)jsonObject.get("state") == 101){
                        System.out.println("heartbeat!!");
                    }else{
                        System.out.println(jsonObject);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
