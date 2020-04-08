package cn.qqtextwar;

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
        generate(Utils.random(new Random(),1,99999999));
        this.init(toJson().toJSONString());
    }

    public SystemMap(){
        this(80,50);
    }

    public static void main(String[] args) {
        System.out.println(new SystemMap().toJson());
    }


    public void generate(int seed){
        Random random = Utils.setRandomSeed(seed);
        int i = 0;
        for(Long[] longs : mapData){
            for(int j = 0;j<longs.length;j++){
                if(longs.length / 2 == j && Utils.random(random,1,10) >= 5){
                    walk(random,mapData,i,j,20);
                }
            }
            i++;
        }
    }

    public void walk(Random r,Long[][] maze,int x,int y,int depth){
        if(maze[x][y] == 1){
            maze[x][y] = 0L;
        }
        if(depth >= 90){
            return;
        }
        int rand = Utils.random(r,1,6);
        if(depth >= 70 && rand == 5){
            return;
        }
        if((maze[x][y + 1] == 1 || maze[x][y + 1] == 0) && rand == 1){
            if(maze[x][y + 1] == 0){
                walk(r,maze,x,y + 1,depth + 1);
            }
            walk(r,maze,x,y - 1,depth);
        }
        if((maze[x + 1][y] == 1 || maze[x + 1][y] == 0) && rand == 3){
            if(maze[x + 1][y] == 0){
                walk(r,maze,x + 1,y,depth + 1);
            }
            walk(r,maze,x + 1,y,depth);
        }
        if((maze[x - 1][y] == 1 || maze[x - 1][y] == 0) && rand == 4){
            if(maze[x - 1][y] == 0){
                walk(r,maze, x - 1, y, depth);
            }
            walk(r,maze, x - 1, y, depth + 1);
        }
    }
}
