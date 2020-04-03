package cn.qqtextwar.tests;

import cn.textwar.client.ClientServer;

public class Test05 {

    public static void main(String[] args) {
        new ClientServer(null,(thread, sc)->{
            System.out.println(thread.whenGetProtocol().getJson());
            System.out.println(sc.getTPS());
        },(a,b)->{},100,8765,500).start();
    }
}
