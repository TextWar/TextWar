package cn.textwar.client;

import cn.qqtextwar.Server;

public class ServerRunner {

    public static void main(String[] args) {
        Server.start(new ClientApplication());
    }
}
