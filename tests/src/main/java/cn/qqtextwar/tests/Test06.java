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
                while (true) {
                    TextWarProtocol protocol1 = new TextWarProtocol().addJSONCode("name","xming");
                    socket11.getOutputStream().write(protocol1.encode());
                    Thread.sleep(100);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }).start();
    }
}
