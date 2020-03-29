package cn.qqtextwar.tests;


import cn.textwar.protocol.Protocol;

import java.net.ServerSocket;
import java.net.Socket;

public class Test02 {

    public static void main(String[] args) throws Exception{

        ServerSocket socket = new ServerSocket(8000);

        Socket socket1 = socket.accept();
        Protocol protocol = new Protocol();
        System.out.println(protocol.decode(socket1.getInputStream()).getJson());
    }
}
