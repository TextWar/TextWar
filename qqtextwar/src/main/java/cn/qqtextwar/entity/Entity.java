package cn.qqtextwar.entity;

import java.util.UUID;

public class Entity {

    private long id;

    private UUID uuid;

    private Vector vector;

    public static final int[] freaks = {
      9,10,11,12,13,14,15
    };

    public Entity(Vector vector,long id){
        uuid = UUID.randomUUID();
        this.vector = vector;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getX() {
        return vector.getX();
    }

    public int getY() {
        return vector.getY();
    }

    public Vector getVector() {
        return vector;
    }
}
