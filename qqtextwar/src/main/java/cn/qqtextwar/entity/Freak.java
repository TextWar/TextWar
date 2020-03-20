package cn.qqtextwar.entity;

import cn.qqtextwar.math.Vector;

public abstract class Freak extends Mob implements Skillable,Registered {

    private int anggressivity;

    public Freak(Vector vector,int id,int difficulty) {
        super(vector,id,true,difficulty);
        setManaPoints(initManaPoints(getLevel()));
    }

    public abstract double initManaPoints(int level);

    public abstract int initAnggressivity(int level);


}
