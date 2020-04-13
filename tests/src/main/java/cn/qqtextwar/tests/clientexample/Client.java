package cn.qqtextwar.tests.clientexample;

import cn.textwar.protocol.Handler;
import cn.textwar.protocol.Protocol;
import cn.textwar.protocol.TextWarProtocol;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws IOException {
        Socket socket1 = new Socket();
        Socket socket2 = new Socket();
        Protocol protocol = new Protocol();
        socket1.connect(new InetSocketAddress("127.0.0.1",8765));
        socket2.connect(new InetSocketAddress("127.0.0.1",8765));
        //第一个socket是发request和接受response
        new Thread(()->{
            while (true) {
                try {
                    TextWarProtocol protocol1 = new TextWarProtocol()
                            .addJSONCode("type", "player")
                            .addJSONCode("action", "login")
                            .addJSONCode("name", "laolu11334")
                            .addJSONCode("password", "12345")
                            .addJSONCode("rad", 5);
                    socket1.getOutputStream().write(protocol1.encode());
                    TextWarProtocol response = protocol.decode(socket1.getInputStream());
                    if ((int) response.getJsonObject().get("state") == Handler.CLOSE) {
                        System.out.println("disconnect");
                        break;
                    } else {
                        System.out.println(response.getJson());
                    }
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                    break;

                }
            }
            try {
                socket1.close();
            }catch (Exception e1){
                e1.printStackTrace();
            }
        }).start();
        //第二个socket是接受服务端的数据包
        new Thread(()->{
            while(true){
                try{
                    TextWarProtocol data;
                    while ((data = protocol.decode(socket2.getInputStream())) == null);
                    if((int) data.getJsonObject().get("state") == Handler.ALIVE){
                        System.out.println("alive");
                    }else{
                        if ((int) data.getJsonObject().get("state") == Handler.CLOSE) {
                            System.out.println("disconnect");
                            break;
                        } else {
                            System.out.println(data.getJson());
                        }
                    }
                    Thread.sleep(500);
                }catch (Exception e){
                    e.printStackTrace();
                    break;

                }
            }
            try {
                socket2.close();
            }catch (Exception e1){
                e1.printStackTrace();
            }
        }).start();
    }
}
