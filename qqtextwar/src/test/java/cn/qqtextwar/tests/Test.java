package cn.qqtextwar.tests;

import cn.qqtextwar.GameMap;
import cn.qqtextwar.Server;
import cn.qqtextwar.entity.player.Player;

public class Test {

    void test(){
        GameMap map = new GameMap("{\n" +
                "\t\"hashmap\": [\"aa\", \"*bb\", \"cc\", \"dd\"],\n" +
                "\t\"type\": 1,\n" +
                "\t\"name\": \"some\",\n" +
                "\t\"author\": \"someone behind the screen\",\n" +
                "\t\"version\": \"b1\",\n" +
                "\t\"map\": [\n" +
                "\t\t[0, 0, 0, 0, 0, 0, 1, 1],\n" +
                "\t\t[0, 0, 0, 0, 0, 0, 1, 1],\n" +
                "\t\t[0, 0, 0, 0, 0, 0, 1, 1],\n" +
                "\t\t[0, 0, 0, 0, 0, 0, 1, 1],\n" +
                "\t\t[0, 0, 0, 0, 0, 0, 0, 0],\n" +
                "\t\t[2, 2, 1, 1, 1, 1, 0, 0],\n" +
                "\t\t[3, 3, 3, 3, 3, 0, 0, 0]\n" +
                "\t]\n" +
                "}");
        Server.start();
        Server.getServer().registerPlayer(843983728,map);
        Player player = Server.getServer().getPlayer(843983728);
        System.out.println(map);
        player.left(map);
        player.update(map);
        System.out.println(map);
    }
}
