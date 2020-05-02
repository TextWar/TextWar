package cn.qqtextwar.entity.points;

//SkillPoints(name,[new Skill])

import cn.qqtextwar.GameMap;
import cn.qqtextwar.entity.Entity;

/**
 * 技能点，
 */
public abstract class SkillPoint {

    private int buff;

    private int damage;

    private int level;

    private long time; //冷却

    private String name;

    private SkillPoints points;

    public SkillPoint(int buff, int damage, int level, String name,int time) {
        this.buff = buff;
        this.damage = damage;
        this.level = level;
        this.name = name;
        this.time = time;
    }

    public int getBuff() {
        return buff;
    }

    public void setBuff(int buff) {
        this.buff = buff;
    }

    public int getDamage() {
        return damage;
    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public long getTime() {
        return time;
    }

    public abstract String doSkill(Entity entity, SkillPoint point, GameMap map);

    void setSkillPoints(SkillPoints points){
        this.points = points;
    }

    public SkillPoints getPoints() {
        return points;
    }
}
