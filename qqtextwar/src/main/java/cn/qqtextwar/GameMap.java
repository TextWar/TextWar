package cn.qqtextwar;

import cn.qqtextwar.blocks.Block;
import cn.qqtextwar.entity.Entity;
import cn.qqtextwar.math.Vector;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import groovy.transform.ToString;

import java.util.*;

/**
 * 游戏的地图对象，否则操控和计算地图的怪物分配及玩家分配
 *
 * @author MagicLu550 @ 卢昶存
 */
@ToString
public class GameMap{


    private static final int WHITE_SPACE = 0;

    private static final String CROSS_LABEL = "*";

    private List<Vector> vectors = new ArrayList<>();

    private String file;

    private Long[][] mapData;

    private Random random;

    private Map<Vector,Block> blocks;

    private String version;

    private String author;

    private String name;

    private int type;

    private List<String> hashMap;

    private Map<UUID,Vector> entityVector;

    public GameMap(String json) {
        this.init(json);
        this.random = new Random();
        this.blocks = new HashMap<>();
        this.entityVector = new HashMap<>();
    }



    public Vector randomVector(){
        return vectors.get(random.nextInt(vectors.size()-1));
    }

    //玩家移动时，需要判断一下方块可穿过
    //这里默认方块都是可穿过的，因为在先前就可以判断
    public void addEntity(Entity e){
        if(!entityVector.containsKey(e.getUuid())){
            entityVector.put(e.getUuid(),e.getVector());
            mapData[e.getY()][e.getX()] = e.getId();
        }else{
            //先前的vector
            Vector vector = entityVector.get(e.getUuid());
            long id;
            if(blocks.get(vector)!=null){
                id = blocks.get(vector).getId();
            }else{
                id = 0L;
            }
            mapData[vector.getY()][vector.getX()] = id;
            mapData[e.getY()][e.getX()] = e.getId();
        }
    }


    public Long[][] getMapData() {
        return mapData;
    }

    public String getFile() {
        return file;
    }

    public List<Vector> getVectors() {
        return vectors;
    }

    public Map<Vector,Block> getBlocks() {
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

    private void init(String json){
        JSONObject object = JSONObject.parseObject(json);
        this.hashMap = ((JSONArray) object.get("hashmap")).toJavaList(String.class);
        this.type = (Integer) object.get("type");
        this.name = (String) object.get("name");
        this.author = (String)object.get("author");
        this.version = (String)object.get("version");
        this.mapData = toData(((JSONArray) object.get("map")).toArray());
        this.getVectors(mapData);
    }

    private void getVectors(Long[][] mapData){
        for(int i = 0;i<mapData.length;i++){
            Long[] map = mapData[0];
            for(int j = 0;j<map.length;j++){
                long number = map[j];
                if(number == WHITE_SPACE){
                    vectors.add(new Vector(j,i));
                }else{
                    Vector vector = new Vector(j,i);
                    Block block = new Block(vector,number, canCross(hashMap.get((int)number)));
                    blocks.put(vector,block);
                    if(block.isCross()){
                        vectors.add(vector);
                    }
                }
            }
        }
    }

    private static Long[][] toData(Object[] map){
        List<Long[]> data = new ArrayList<>(map.length);
        for(Object arr : map){
            data.add(getLongArray((JSONArray) arr));
        }
        return data.toArray(new Long[0][0]);
    }

    private static Long[] getLongArray(JSONArray array){
        return array.toJavaList(Long.class).toArray(new Long[0]);
    }

    private static boolean canCross(String name){
        return name.startsWith(CROSS_LABEL);
    }
}
