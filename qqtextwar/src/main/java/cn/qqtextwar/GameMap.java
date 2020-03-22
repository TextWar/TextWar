package cn.qqtextwar;

import cn.qqtextwar.blocks.Block;
import cn.qqtextwar.math.Vector;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import groovy.transform.ToString;

import java.util.ArrayList;
import java.util.Arrays;
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

    private Integer[][] mapData;

    private Random random;

    private List<Block> blocks;

    private String version;

    private String author;

    private String name;

    private int type;

    private List<String> hashMap;

    public GameMap(String json) {
        JSONObject object = JSONObject.parseObject(json);
        this.hashMap = ((JSONArray) object.get("hashmap")).toJavaList(String.class);
        this.type = (Integer) object.get("type");
        this.name = (String) object.get("name");
        this.author = (String)object.get("author");
        this.version = (String)object.get("version");
        this.mapData = toData(((JSONArray) object.get("map")).toArray());
        this.getVectors(mapData);
        this.random = new Random();
        this.blocks = new ArrayList<>();
    }

    private static Integer[][] toData(Object[] map){
        List<Integer[]> data = new ArrayList<>(map.length);
        for(Object arr : map){
            data.add(((JSONArray)arr).toArray(new Integer[0]));
        }
        return data.toArray(new Integer[0][0]);
    }


    public void getVectors(Integer[][] mapData){
        for(int i = 0;i<mapData.length;i++){
            Integer[] map = mapData[0];
            for(int j = 0;j<map.length;j++){
                int number = map[j];
                if(number == WHITE_SPACE){
                    vectors.add(new Vector(j,i));
                }else{
                    blocks.add(new Block(new Vector(j,i),number, hashMap.get(number).startsWith("*")));
                }
            }
        }
    }

    public Vector randomVector(){
        return vectors.get(random.nextInt(vectors.size()-1));
    }


    public Integer[][] getMapData() {
        return mapData;
    }

    public String getFile() {
        return file;
    }

    public List<Vector> getVectors() {
        return vectors;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public String getVersion() {
        return version;
    }

    public String getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
