package cn.qqtextwar.entity.points;

//SkillPoints(name,[new Skill])
public class SkillPoint {

    private int buff;

    private int damage;

    private int level;

    private String name;

    public SkillPoint(int buff, int damage, int level, String name) {
        this.buff = buff;
        this.damage = damage;
        this.level = level;
        this.name = name;
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

    public void setLevel(int level) {
        this.level = level;
    }

    public void upLevel(){
        setLevel(getLevel()+1);
    }

    public String getName() {
        return name;
    }


}
