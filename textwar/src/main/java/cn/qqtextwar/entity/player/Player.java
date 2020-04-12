package cn.qqtextwar.entity.player;


import cn.qqtextwar.CommandSender;
import cn.qqtextwar.Hitable;
import cn.qqtextwar.ProtocolVar;

import cn.qqtextwar.Server;
import cn.qqtextwar.api.Application;
import cn.qqtextwar.entity.Entity;
import cn.qqtextwar.entity.Skillable;
import cn.qqtextwar.ex.IllegalIdException;
import cn.qqtextwar.math.Vector;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 玩家类
 * id > 10000
 */
public class Player extends Entity implements Skillable, CommandSender, Hitable {

    private int admin;
    private String name;

    private LocalDateTime operationTime;

    private Application application;


    private String ip;

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

    private Server server;

    public Player(Server server,Application application,String ip, Vector vector, long id, double healthPoints, double manaPoints, int money) {
        super(vector, id, healthPoints, manaPoints);
        if(id < ProtocolVar.PLAYER_MIN_ID){
            throw new IllegalIdException("the player's id is bigger than 10000");
        }
        this.xp = 0;
        this.inventory = new Inventory();
        this.money = money;
        this.ip = ip;
        this.application = application;
        this.server = server;
    }

    public Server getServer() {
        return server;
    }

    public Application getApplication() {
        return application;
    }

    public boolean done(Server server){
        if(operationTime == null) {
            operationTime = LocalDateTime.now();
            return true;
        }else{
            LocalDateTime time = LocalDateTime.now();
            if(Duration.between(operationTime,time).toMillis() / 1000 >= (int)server.getParser().getValue("server.messageTime",5)[0]){
                this.operationTime = LocalDateTime.now();
                return true;
            }else{
                return false;
            }
        }
    }

    public void setAdmin(boolean admin) {
        this.admin = (admin?1:0);
        server.getDaoFactory().getPlayerDAO().setAdmin(name,this.admin);
    }

    public boolean isAdmin(){
        return this.admin == 1;
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

    public void setXp(int xp) {
        this.xp = xp;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    public void sendMessage(String message){
        application.sendMessage(getId(),message);
    }

    public void chat(String message){
        application.playerChat(this.getId(),message);
    }
}
