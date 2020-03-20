package cn.qqtextwar.entity;

import cn.qqtextwar.entity.points.SkillPoint;
import cn.qqtextwar.entity.points.SkillPoints;
import cn.qqtextwar.math.Vector;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public abstract class Entity {

    private long id;

    private UUID uuid;

    private double healthPoints;

    private double manaPoints;

    private int level;

    protected Random random;
    
    protected Vector vector;

    private Map<SkillPoints,Integer> levels;

    private Map<SkillPoints, LocalDateTime> useDates;


    public Entity(Vector vector, long id) {
        uuid = UUID.randomUUID();
        this.vector = vector;
        this.id = id;
        this.levels = new HashMap<>();
        this.useDates = new HashMap<>();
    }

    public Entity(Vector vector, long id, double healthPoints, double manaPoints) {
        this.uuid = UUID.randomUUID();
        this.vector = vector;
        this.id = id;
        this.healthPoints = healthPoints;
        this.manaPoints = manaPoints;
        this.random = new Random();
    }

    public synchronized int random(int round){
        return random.nextInt(round);
    }

    public synchronized SkillPoint doSkill(SkillPoints points){
        LocalDateTime time = useDates.get(points);
        LocalDateTime now = LocalDateTime.now();
        if(time == null){
            useDates.put(points,now);
            SkillPoint point = getSkillByLevel(points);
            setManaPoints(getManaPoints() - point.getBuff());
            return point;
        }else{
            Duration duration = Duration.between(time,now);
            if(duration.toMinutes()*60 >= getSkillByLevel(points).getTime()){
                useDates.put(points,now);
                SkillPoint point = getSkillByLevel(points);
                setManaPoints(getManaPoints() - point.getBuff());
                return point;
            }else{
                return null;
            }
        }
    }

    private void upLevel(SkillPoints points){
        levels.put(points,levels.get(points)+1);
    }



    public void addSkill(SkillPoints points){
        this.levels.put(points,0);
    }

    private SkillPoint getSkillByLevel(SkillPoints points){
        return points.getSkillByLevel(levels.get(points));
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
