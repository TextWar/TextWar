package cn.qqtextwar.tests;

import cn.textwar.client.ClientServer;
import cn.textwar.protocol.TextWarProtocol;

public class Test05 {

    public static void main(String[] args) {
        new ClientServer(null,(thread, sc)->{
            thread.whenGetProtocol();
            thread.getSocket().getOutputStream().write(new TextWarProtocol().addJSONCode("response","1").encode());
        },100,8765).start();
    }
}
