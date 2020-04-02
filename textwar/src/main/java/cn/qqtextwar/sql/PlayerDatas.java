package cn.qqtextwar.sql;

import java.util.Date;

/**
 * 存储玩家的数据
 */
public class PlayerDatas {

    private long id;

    private String name;

    private int health;

    private int mana;

    private Date joinTime;

    private int inventoryId;

    private String password;

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
