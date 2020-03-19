package cn.qqtextwar.entity;

import cn.qqtextwar.math.Vector;

import java.util.UUID;

public class Entity {

    private long id;

    private UUID uuid;

    private Vector vector;

    private double healthPoints;

    private double manaPoints;

    private int level;

    public Entity(Vector vector, long id) {
        uuid = UUID.randomUUID();
        this.vector = vector;
        this.id = id;
    }

    public Entity(Vector vector, long id, double healthPoints, double manaPoints) {
        this.uuid = UUID.randomUUID();
        this.vector = vector;
        this.id = id;
        this.healthPoints = healthPoints;
        this.manaPoints = manaPoints;
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

    public double getHealthPoints() {
        return healthPoints;
    }

    public double getManaPoints() {
        return manaPoints;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    protected final void setHealthPoints(double healthPoints) {
        this.healthPoints = healthPoints;
    }

    protected final void setManaPoints(double manaPoints) {
        this.manaPoints = manaPoints;
    }

    protected final synchronized void addHealthPoints(double value) {
        this.healthPoints += value;
    }

    protected final synchronized void delHealthPoints(double value) {
        this.healthPoints -= value;
    }

    protected final synchronized void addManaPoints(double value) {
        this.manaPoints += value;
    }

    protected final synchronized void delManaPoints(double value) {
        this.manaPoints -= value;
    }


}
