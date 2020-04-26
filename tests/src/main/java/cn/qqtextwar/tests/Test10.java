package cn.qqtextwar.tests;

import cn.qqtextwar.Server;
import cn.qqtextwar.entity.player.Player;
import cn.textwar.client.ClientApplication;
import cn.textwar.utils.test.Client;
import cn.textwar.utils.test.TextWarServerRunner;
import cn.textwar.utils.test.TextWarServerTest;

@Client({ClientApplication.class})
public class Test10 extends TextWarServerRunner {

    public static void main(String[] args) {
        doTest(Test10.class);
    }

    @TextWarServerTest
    public void test(Server server){
        Player player1 = server.registerPlayer(server.getApplications().get(0),"",10001,server.getGameMap());
        Player player2 = server.registerPlayer(server.getApplications().get(0),"",10002,server.getGameMap());
        System.out.println(server.getGameMap());

        System.out.println(player1.haveObstacle(player2,server.getGameMap()));
    }
}
