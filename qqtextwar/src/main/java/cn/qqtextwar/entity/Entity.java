package cn.qqtextwar.entity;

import java.util.UUID;

public class Entity {

    private long id;

    private UUID uuid;

    private int x;

    private int y;

    public static final int[] freaks = {
      9,10,11,12,13,14,15
    };

    public Entity(int x,int y,long id){
        uuid = UUID.randomUUID();
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
