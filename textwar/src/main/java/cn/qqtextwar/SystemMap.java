package cn.qqtextwar;

import cn.qqtextwar.ex.MapDataException;
import cn.qqtextwar.utils.Utils;

import java.util.Arrays;
import java.util.Random;

/**
 * 摘抄自https://github.com/TextWar/textwar-py/blob/master/wild.py
 */
public class SystemMap extends GameMap{

    public SystemMap(int arrayHeight, int arrayWeight){
        this.mapData = new Long[arrayHeight][arrayWeight];
        for(int i = 0;i<mapData.length;i++){
            mapData[i] = new Long[arrayWeight];
            Arrays.fill(mapData[i],1L);
        }
        this.setHashMap(Arrays.asList("*","░"));
        this.setAuthor("system");
        this.setVersion("1.0");
        this.setName("random");
        generate(Utils.random(new Random(),1,99999999));
        this.init(toJson().toJSONString());
    }

    public SystemMap(){
        this(80,50);
    }


    public void generate(int seed){
        Random random = Utils.setRandomSeed(seed);
        for(int i = 1;i<mapData.length+1;i++){
            for(int j = 1;j<mapData[i-1].length+1;j++){
                if(mapData[i-1].length / 2 == j && Utils.random(random,1,10) >= 5){
                    try {
                        walk(random, mapData, i, j, 20);
                    }catch (Exception ignore){

                    }
                }
            }
            i++;
        }
    }

    public int getXIndex(int i){
        if(i < 0) {
            return mapData.length- Math.abs(i);
        }
        return i;
    }
    public int getYIndex(int i){
        if(i < 0) {
            return mapData[0].length- Math.abs(i);
        }
        return i;
    }
    public void walk(Random r,Long[][] maze,int x,int y,int depth){
        if(maze[getXIndex(x)][getYIndex(y)] == 1){
            maze[getXIndex(x)][getYIndex(y)] = 0L;
        }
        if(depth >= 90){
            throw new MapDataException("");
        }
        int rand = Utils.random(r,1,6);
        if(depth >= 70 && rand == 5){
            throw new MapDataException("");
        }
        if((maze[getXIndex(x)][getYIndex(y + 1)] == 1 || maze[getXIndex(x)][getYIndex(y + 1)] == 0) && rand == 1){
            if(maze[getXIndex(x)][getYIndex(y + 1)] == 0){
                walk(r,maze,x,y + 1,depth + 1);
            }
            walk(r,maze,x,y - 1,depth);
        }
        if ((maze[getXIndex(x)][getYIndex(y - 1)] == 1|| maze[getXIndex(x)][getYIndex(y - 1)] == 0)&& rand == 2){
            if(maze[getXIndex(x)][getYIndex(y - 1)] == 0){
                walk(r,maze, x, y - 1, depth);
            }
            walk(r,maze, x, y - 1, depth + 1);
        }
        if((maze[getXIndex(x + 1)][getYIndex(y)] == 1 || maze[getXIndex(x + 1)][getYIndex(y)] == 0) && rand == 3){
            if(maze[getXIndex(x + 1)][getYIndex(y)] == 0){
                walk(r,maze,x + 1,y,depth + 1);
            }
            walk(r,maze,x + 1,y,depth);
        }
        if((maze[getXIndex(x - 1)][getYIndex(y)] == 1 || maze[getXIndex(x - 1)][getYIndex(y)] == 0) && rand == 4){
            if(maze[getXIndex(x - 1)][getYIndex(y)] == 0){
                walk(r,maze, x - 1, y, depth);
            }
            walk(r,maze, x - 1, y, depth + 1);
        }
    }
}
