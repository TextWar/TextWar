package cn.qqtextwar.tests;

import cn.textwar.protocol.Protocol;
import cn.textwar.protocol.TextWarProtocol;

import java.net.InetSocketAddress;
import java.net.Socket;

public class Test06 {

    public static void main(String[] args) {
        new Thread(()->{
            try {
                System.out.println("启动");
                Socket socket11 = new Socket();
                socket11.connect(new InetSocketAddress("127.0.0.1",8765));
                Protocol protocol = new Protocol();
                TextWarProtocol protocol1 = new TextWarProtocol()
                        .addJSONCode("type","player")
                        .addJSONCode("action","register")
                        .addJSONCode("name","laolu11334")
                        .addJSONCode("password","12345")
                        .addJSONCode("rad",5);
                socket11.getOutputStream().write(protocol1.encode());
                System.out.println(protocol.decode(socket11.getInputStream()).getJson());
            }catch (Exception e){
                e.printStackTrace();
            }
        }).start();
    }
}
