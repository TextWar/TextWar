package cn.qqtextwar.entity;

import cn.qqtextwar.math.Vector;

/**
 * 这里就是用于定义有害的生物，可以使用技能
 *
 * @author MagicLu550 @ 卢昶存
 */
public abstract class Freak extends Mob implements Skillable,Registered {

    public Freak(Vector vector,int id,int difficulty) {
        super(vector,id,true,difficulty);
        setManaPoints(initManaPoints(getLevel()));
        this.aggressivity = initAggressivity(getLevel());
    }

    public abstract double initManaPoints(int level);



    public int getAnggressivity() {
        return aggressivity;
    }
}
