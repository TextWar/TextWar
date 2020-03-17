package cn.qqtextwar.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameMap {


    private static int WHITE_SPACE = 0;

    private List<Vector> vectors = new ArrayList<>();

    private String file;

    private List<List<Integer>> mapData;

    private Random random;

    public GameMap(String file, List<List<Integer>> mapData) {
        this.file = file;
        this.mapData = mapData;
        this.getVectors(mapData);
        this.random = new Random();
    }

    public void getVectors(List<List<Integer>> mapData){
        for(int i = 0;i<mapData.size();i++){
            List<Integer> map = mapData.get(0);
            for(int j = 0;j<vectors.size();j++){
                int number = map.get(j);
                if(number == WHITE_SPACE){
                    vectors.add(new Vector(j,i));
                }
            }
        }
    }

    public Vector randomVector(){
        return vectors.get(random.nextInt(vectors.size()-1));
    }


    public List<List<Integer>> getMapData() {
        return mapData;
    }

    public String getFile() {
        return file;
    }

}
