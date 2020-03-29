package cn.textwar.client;


import cn.textwar.protocol.TextWarProtocol;

public class ServerRunner {

    public static void main(String[] args) {
        new ClientServer(null,(thread,sc)->{
            System.out.println(sc.getStreamList());
            sc.callMessage(new TextWarProtocol().addJSONCode("hello","world"));
        },100,8765).start();
    }
}