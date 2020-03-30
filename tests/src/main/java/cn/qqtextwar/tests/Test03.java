package cn.qqtextwar.tests;


import cn.textwar.protocol.Protocol;
import cn.textwar.protocol.TextWarProtocol;

import java.net.InetSocketAddress;
import java.net.Socket;

public class Test03 {
    public static void main(String[] args) {
        new Thread(()->{
            try {
                System.out.println("启动");
                Socket socket11 = new Socket();
                socket11.connect(new InetSocketAddress("127.0.0.1",8765));
                Protocol protocol = new Protocol();
                socket11.getOutputStream().write(new TextWarProtocol().encode());
                TextWarProtocol protocol1;
                while ((protocol1 = protocol.decode(socket11.getInputStream()))==null);
                System.out.println(protocol1.getJson());
//                while (true) {
//                    TextWarProtocol protocol1;
//                    while((protocol1 = protocol.decode(socket11.getInputStream()))==null);
//                    System.out.println(protocol1);
//                    Thread.sleep(500);
//                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }).start();
    }
}
