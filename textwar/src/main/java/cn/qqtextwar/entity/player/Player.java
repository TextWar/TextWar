package cn.qqtextwar.entity.player;


import cn.qqtextwar.CommandSender;
import cn.qqtextwar.Hitable;
import cn.qqtextwar.Server;
import cn.qqtextwar.entity.Entity;
import cn.qqtextwar.entity.Skillable;
import cn.qqtextwar.math.Vector;

import java.util.HashMap;
import java.util.Map;

/**
 * 玩家类
 * id > 10000
 */
public class Player extends Entity implements Skillable, CommandSender, Hitable {

    private Server server;

    /**
     * 这里用于定义升下一级所需的xp值
     */
    private static final Map<Integer,Integer> LEVEL_LIMIT = new HashMap<>();

    static {
        LEVEL_LIMIT.put(1,100);
        LEVEL_LIMIT.put(2,200);
    }

    private int xp;

    private Inventory inventory;

    private int money;


    public Player(Server server, Vector vector, long id, double healthPoints, double manaPoints, int money) {
        super(vector, id, healthPoints, manaPoints);
        this.xp = 0;
        this.inventory = new Inventory();
        this.money = money;
        this.server = server;
    }


    public int getXp() {
        return xp;
    }

    public void addXpToUprade(int xpToUprade) {
        this.xp += xpToUprade;
        if(this.xp >= LEVEL_LIMIT.get(getLevel()+1)){
            this.xp -= xpToUprade;
            setLevel(getLevel()+1);
            whenLevelUp(getLevel());
        }
    }

    public int getXpToUpgrade(){
        return LEVEL_LIMIT.get(getLevel()+1) - xp;
    }


    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory){
        this.inventory = inventory;
    }

    public int getMoney() {
        return money;
    }

    public void whenLevelUp(int level){
        //TODO 设置升级内容
    }

    @Override
    public int initAggressivity(int level) {
        //TODO 设置攻击力
        return 0;
    }

    public void sendMessage(String message){
        this.server.getApplication().sendMessage(getId(),message);
    }
}
