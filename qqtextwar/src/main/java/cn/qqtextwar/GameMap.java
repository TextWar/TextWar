package cn.qqtextwar;

import cn.qqtextwar.math.Vector;
import groovy.transform.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 游戏的地图对象，否则操控和计算地图的怪物分配及玩家分配
 *
 * @author MagicLu550 @ 卢昶存
 */
@ToString
public class GameMap {


    private static int WHITE_SPACE = 0;

    private List<Vector> vectors = new ArrayList<>();

    private String file;

    private int[][] mapData;

    private Random random;

    public GameMap(String file, int[][] mapData) {
        this.file = file;
        this.mapData = mapData;
        this.getVectors(mapData);
        this.random = new Random();
    }

    public void getVectors(int[][] mapData){
        for(int i = 0;i<mapData.length;i++){
            int[] map = mapData[0];
            for(int j = 0;j<map.length;j++){
                int number = map[j];
                if(number == WHITE_SPACE){
                    vectors.add(new Vector(j,i));
                }
            }
        }
    }

    public Vector randomVector(){
        return vectors.get(random.nextInt(vectors.size()-1));
    }


    public int[][] getMapData() {
        return mapData;
    }

    public String getFile() {
        return file;
    }

}
