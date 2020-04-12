package cn.qqtextwar.sql;

import java.util.Date;

/**
 * 存储玩家的数据
 */
public class PlayerDatas {

    private int admin;

    private int xp;

    private long id;

    private String name;

    private double health;

    private double mana;

    private Date joinTime;

    private long inventoryId;

    private String password;

    private int level;

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void setMana(double mana) {
        this.mana = mana;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    public void setInventoryId(long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public int getAdmin() {
        return admin;
    }

    public String getName() {
        return name;
    }

    public double getHealth() {
        return health;
    }

    public double getMana() {
        return mana;
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public long getInventoryId() {
        return inventoryId;
    }

    public String getPassword() {
        return password;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getXp() {
        return xp;
    }

    @Override
    public String toString() {
        return "PlayerDatas{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", health=" + health +
                ", mana=" + mana +
                ", joinTime=" + joinTime +
                ", inventoryId=" + inventoryId +
                ", password='" + password + '\'' +
                ", level=" + level +
                '}';
    }
}
