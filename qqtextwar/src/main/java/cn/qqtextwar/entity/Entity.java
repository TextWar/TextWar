package cn.qqtextwar.entity;

import java.util.UUID;

public class Entity {

    private long id;

    private UUID uuid;

    private Vector vector;

    public Entity(Vector vector, long id) {
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

    public static enum FreaksEnum {
        NINE(9),
        TEN(10),
        ELEVEN(11),
        TWELVE(12),
        THIRTEEN(13),
        FOURTEEN(14),
        FIFTEEN(15);
        public final long mapValue;
        FreaksEnum(long value) {
            this.mapValue = value;
        }
    }
}
