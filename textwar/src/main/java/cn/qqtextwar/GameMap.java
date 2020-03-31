package cn.qqtextwar;

import cn.qqtextwar.blocks.Block;
import cn.qqtextwar.entity.Entity;
import cn.qqtextwar.entity.player.Player;
import cn.qqtextwar.ex.MapDataException;
import cn.qqtextwar.math.Vector;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import groovy.transform.ToString;

import java.util.*;

import static cn.qqtextwar.ProtocolVar.CROSS_LABEL;

/**
 * 游戏的地图对象，否则操控和计算地图的怪物分配及玩家分配
 *
 * @author MagicLu550 @ 卢昶存
 */
@ToString
public class GameMap{

    private List<Vector> vectors = new ArrayList<>();

    private String file;

    private volatile Long[][] mapData;

    private Random random;

    private Map<Vector,Block> blocks;

    private String version;

    private String author;

    private String name;

    private int type;

    private List<String> hashMap;

    private Map<UUID,Vector> entityVector;

    public GameMap(String json) {
        this.random = new Random();
        this.blocks = new HashMap<>();
        this.entityVector = new HashMap<>();
        this.init(json);
    }



    public Vector randomVector(){
        Vector vector = vectors.get(random.nextInt(vectors.size()-1));
        while (!blocks.get(vector).isCross()){
            vector = vectors.get(random.nextInt(vectors.size()-1));
        }
        return vector;
    }

    public GameMap interceptForPlayer(Player player,int rad){
        JSONObject object = new JSONObject();
        object.put("hashmap",getHashMap());
        object.put("type",getType());
        object.put("name",getName());
        object.put("author","system");
        object.put("version",getVersion());
        int x1 = this.getXBound((int)(player.getX() - rad));
        int y1 = this.getYBound((int)(player.getY() - rad));
        int x2 = this.getXBound((int)(player.getX() + rad));
        int y2 = this.getYBound((int)(player.getY() + rad));
        JSONArray array = new JSONArray();
        for(int i = y1;i<=y2;i++){
            JSONArray line = new JSONArray();
            for(int j = x1;j<x2;j++){
                line.add(mapData[j][i]);
            }
            array.add(line);
        }
        object.put("map",array);
        return new GameMap(object.toJSONString());
    }
    //玩家移动时1，需要判断一下方块可穿过
    //这里默认方块都是可穿过的，因为在先前就可以判断
    public synchronized void addEntity(Entity e){
        if(!entityVector.containsKey(e.getUuid())){
            entityVector.put(e.getUuid(),e.getVector());
            mapData[(int)e.getY()][(int)e.getX()] = e.getId();
        }else{
            //先前的vector
            setMapBefore(e);
            mapData[(int)e.getY()][(int)e.getX()] = e.getId();
            entityVector.put(e.getUuid(),e.getVector());
        }
    }

    public void updateEntity(Entity e){
        addEntity(e);
    }

    public void removeEntity(Entity e){
        //先前的vector
        setMapBefore(e);
        entityVector.remove(e.getUuid());
    }

    private void setMapBefore(Entity e){
        Vector vector = entityVector.get(e.getUuid());
        long id;
        if(blocks.get(vector)!=null){
            id = blocks.get(vector).getId();
        }else{
            id = 0L;
        }
        mapData[(int)vector.getY()][(int)vector.getX()] = id;
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

    public long getValue(Vector vector){
        return mapData[(int)vector.getY()][(int)vector.getX()];
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
        try {
            JSONObject object = JSONObject.parseObject(json);
            this.hashMap = ((JSONArray) object.get("hashmap")).toJavaList(String.class);
            this.type = (Integer) object.get("type");
            this.name = (String) object.get("name");
            this.author = (String) object.get("author");
            this.version = (String) object.get("version");
            this.mapData = toData(((JSONArray) object.get("map")).toArray());
            if (mapData.length == 0) throw new MapDataException("the map's length could not be 0");
            this.getVectors(mapData);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("illegal game map");
        }
    }
    public JSONObject toJson(){
        JSONObject object = new JSONObject();
        object.put("hashmap",hashMap);
        object.put("type",type);
        object.put("name",name);
        object.put("author",author);
        object.put("version",version);
        object.put("map",toJsonArray(mapData));
        return object;
    }

    private JSONArray toJsonArray(Long[][] mapData){
        JSONArray array = new JSONArray();
        for(Long[] line : mapData){
            JSONArray jsonArray = new JSONArray();
            jsonArray.addAll(Arrays.asList(line));
            array.add(jsonArray);
        }
        return array;
    }

    private void getVectors(Long[][] mapData){
        for(int i = 0;i<mapData.length;i++){
            Long[] map = mapData[i];
            for(int j = 0;j<map.length;j++){
                long number = map[j];
                Vector vector = new Vector(j,i);
                Block block = new Block(vector,number, canCross(hashMap.get((int)number)));
                blocks.put(vector,block);
                if(block.isCross()){
                    vectors.add(vector);
                }
            }
        }
    }

    public boolean outY(int y){
        return y < 0 || y > mapData.length-1;
    }

    public boolean outX(int x){
        if(mapData.length != 0){
            return x < 0 || x > mapData[0].length-1;
        }
        return false;
    }

    //如果超出边界返回边界值 不超出边界则返回原来的值
    public int getYBound(int y){
        if(y < 0)return 0;
        if(y > mapData.length-1)return mapData.length-1;
        return y;
    }

    public int getXBound(int x){
        if(x < 0)return 0;
        if(x > mapData[0].length-1)return mapData[0].length-1;
        return x;
    }

    private static Long[][] toData(Object[] map){
        List<Long[]> data = new ArrayList<>(map.length);
        for(Object arr : map){
            data.add(getLongArray((JSONArray) arr));
        }
        return data.toArray(new Long[0][0]);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(Long[] longs : mapData){
            for(long l : longs){
                if(l < (long) Integer.MAX_VALUE){
                    int index = (int) l;
                    if(ProtocolVar.NPC_ID.containsWithinBounds(index)){
                        builder.append("NPC");
                    }else if(ProtocolVar.MOB_ID.containsWithinBounds(index)) {
                        builder.append("MOB");
                    } else if(!appendPlayer(l,builder)) {
                        Object o = index > hashMap.size() - 1 ? l : hashMap.get(index);
                        if(o instanceof String){
                            if(CROSS_LABEL.equals(o)){
                                o = ((String)o).replace(CROSS_LABEL," ");
                            }else{
                                o = ((String)o).replace(CROSS_LABEL,"");
                            }
                        }
                        builder.append(o);
                    }

                }else{
                    appendPlayer(l,builder);
                }
                builder.append(" ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    private boolean appendPlayer(long l,StringBuilder builder){
        if(l>=ProtocolVar.PLAYER_MIN_ID){
            builder.append("PER");
            return true;
        }else if(l >= (long) Integer.MAX_VALUE){
            builder.append(l);
        }
        return false;
    }

    private static Long[] getLongArray(JSONArray array){
        return array.toJavaList(Long.class).toArray(new Long[0]);
    }

    private static boolean canCross(String name){
        return name.startsWith(CROSS_LABEL);
    }

    public List<String> getHashMap() {
        return hashMap;
    }



}
