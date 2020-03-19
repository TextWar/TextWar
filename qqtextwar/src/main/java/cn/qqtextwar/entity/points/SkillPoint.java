package cn.qqtextwar.entity.points;

//SkillPoints(name,[new Skill])
public class SkillPoint {

    private int buff;

    private int damage;

    private int level;

    private long time; //冷却

    private String name;

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
}
