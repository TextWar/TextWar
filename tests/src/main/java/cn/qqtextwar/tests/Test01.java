package cn.qqtextwar.tests;

import cn.qqtextwar.GameMap;
import cn.qqtextwar.Server;
import cn.qqtextwar.entity.player.Player;
import cn.qqtextwar.math.Vector;

public class Test01 {

        public static void main(String[] args) {
                //Server.start(new QQApplication());
                GameMap map = new GameMap("{\n" +
                        "  \"author\": \"someone behind the screen\",\n" +
                        "  \"hashmap\": [\n" +
                        "    \"*aa\",\n" +
                        "    \"bb\",\n" +
                        "    \"cc\",\n" +
                        "    \"dd\"\n" +
                        "  ],\n" +
                        "  \"map\": [\n" +
                        "    [\n" +
                        "      0,\n" +
                        "      0,\n" +
                        "      0,\n" +
                        "      0,\n" +
                        "      0,\n" +
                        "      0,\n" +
                        "      1,\n" +
                        "      1\n" +
                        "    ],\n" +
                        "    [\n" +
                        "      0,\n" +
                        "      0,\n" +
                        "      0,\n" +
                        "      0,\n" +
                        "      0,\n" +
                        "      0,\n" +
                        "      1,\n" +
                        "      1\n" +
                        "    ],\n" +
                        "    [\n" +
                        "      0,\n" +
                        "      1,\n" +
                        "      0,\n" +
                        "      0,\n" +
                        "      0,\n" +
                        "      0,\n" +
                        "      1,\n" +
                        "      1\n" +
                        "    ],\n" +
                        "    [\n" +
                        "      0,\n" +
                        "      0,\n" +
                        "      0,\n" +
                        "      0,\n" +
                        "      0,\n" +
                        "      0,\n" +
                        "      1,\n" +
                        "      1\n" +
                        "    ],\n" +
                        "    [\n" +
                        "      0,\n" +
                        "      0,\n" +
                        "      0,\n" +
                        "      0,\n" +
                        "      0,\n" +
                        "      0,\n" +
                        "      0,\n" +
                        "      0\n" +
                        "    ],\n" +
                        "    [\n" +
                        "      2,\n" +
                        "      2,\n" +
                        "      1,\n" +
                        "      1,\n" +
                        "      1,\n" +
                        "      1,\n" +
                        "      0,\n" +
                        "      0\n" +
                        "    ],\n" +
                        "    [\n" +
                        "      3,\n" +
                        "      3,\n" +
                        "      3,\n" +
                        "      3,\n" +
                        "      3,\n" +
                        "      0,\n" +
                        "      0,\n" +
                        "      0\n" +
                        "    ]\n" +
                        "  ],\n" +
                        "  \"name\": \"some\",\n" +
                        "  \"type\": 1,\n" +
                        "  \"version\": \"b1\"\n" +
                        "}");

                Player player1 = new Player("",Server.getServer(),new Vector(1,0),1003883838,100,100,100)
                        .addInto(map)
                        .as(Player.class);

//                Player player2 = new Player("",Server.getServer(),new Vector(2,3),1003883838,100,100,100)
//                        .addInto(map)
//                        .as(Player.class);
                System.out.println(map);
                //System.out.println(player1.haveObstacle(player2,map));
        }
}
