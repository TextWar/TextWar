package cn.qqtextwar.entity;

import cn.qqtextwar.entity.points.SkillPoint;
import cn.qqtextwar.entity.points.SkillPoints;
import cn.qqtextwar.math.Vector;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Player extends Entity{

    private static final Map<Integer,Integer> LEVEL_LIMIT = new HashMap<>();

    static {
        LEVEL_LIMIT.put(1,100);
        LEVEL_LIMIT.put(2,200);
    }

    private Map<SkillPoints,Integer> levels;

    private Map<SkillPoints, LocalDateTime> useDates;

    private int xp;

    private Inventory inventory;

    private int money;


    public Player(Vector vector, long id, double healthPoints, double manaPoints, int money) {
        super(vector, id, healthPoints, manaPoints);
        this.levels = new HashMap<>();
        this.useDates = new HashMap<>();
        this.xp = 0;
        this.inventory = new Inventory();
        this.money = money;
    }

    private void upLevel(SkillPoints points){
        levels.put(points,levels.get(points)+1);
    }

    public synchronized SkillPoint doSkill(SkillPoints points){
        LocalDateTime time = useDates.get(points);
        LocalDateTime now = LocalDateTime.now();
        if(time == null){
            useDates.put(points,now);
            return getSkillByLevel(points);
        }else{
            Duration duration = Duration.between(time,now);
            if(duration.toMinutes()*60 >= getSkillByLevel(points).getTime()){
                useDates.put(points,now);
                return getSkillByLevel(points);
            }else{
                return null;
            }
        }
    }

    public void addSkill(SkillPoints points){
        this.levels.put(points,0);
    }

    public int getXp() {
        return xp;
    }

    public void addXpToUprade(int xpToUprade) {
        this.xp += xpToUprade;
        if(this.xp >= LEVEL_LIMIT.get(getLevel()+1)){
            this.xp -= xpToUprade;
            setLevel(getLevel()+1);
        }
    }

    public int getXpToUpgrade(){
        return LEVEL_LIMIT.get(getLevel()+1) - xp;
    }

    public SkillPoint getSkillByLevel(SkillPoints points){
        return points.getSkillByLevel(levels.get(points));
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

}
