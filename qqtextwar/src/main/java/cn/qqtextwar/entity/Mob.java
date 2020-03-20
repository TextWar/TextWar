package cn.qqtextwar.entity;

import cn.qqtextwar.math.Vector;

import java.util.HashMap;
import java.util.Map;


public abstract class Mob extends Entity{

    private static Map<String,Class<? extends Registered>> mobs = new HashMap<>();

    private boolean harmful;

    public Mob(Vector vector, long id,boolean harmful,int difficulty) {
        super(vector, id, 0,0);
        this.harmful = harmful;
        setLevel(randomLevel(difficulty));
        setHealthPoints(initHealth(getLevel()));
    }

    public static void registerMob(Class<? extends Registered> mob){
        mobs.put(mob.getName(),mob);
    }

    public boolean isHarmful() {
        return harmful;
    }

    public abstract int randomLevel(int difficulty);

    public abstract double initHealth(int level);

    public abstract double getXp(int level);


}
