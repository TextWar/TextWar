package cn.qqtextwar;

import cn.qqtextwar.entity.Mob;
import cn.qqtextwar.entity.impl.Slime;
import cn.qqtextwar.entity.player.Player;
import cn.qqtextwar.math.Vector;

public class Example {

    private static String EXAMPLE_MAP = "{\n" +
            "\t\"hashmap\": [\"*aa\", \"bb\", \"cc\", \"dd\"],\n" +
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
            "}";

    public static void main(String[] args) {
        GameMap map = new GameMap(EXAMPLE_MAP);
        Player player = new Player(new Vector(0,0),192992929,1,1,1);
        player.move(new Vector(1,2),map).update();
        Mob mob = new Slime(new Vector(1,1),0);
        mob.move(new Vector(2,1),map).update();
        System.out.println(map);
        System.out.println(map.getValue(map.randomVector()));
    }
}
