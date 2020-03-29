package cn.qqtextwar.core;

import cn.qqtextwar.Server;

public class Main {

    public static void main(String[] args) {
        Server.start(new QQApplication()).rpcMap();
    }
}
