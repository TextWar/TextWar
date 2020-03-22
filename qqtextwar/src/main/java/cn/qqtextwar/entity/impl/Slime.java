package cn.qqtextwar.entity.impl;

import cn.qqtextwar.entity.CommonMob;
import cn.qqtextwar.math.Vector;

/**
 * 史莱姆
 */
public class Slime extends CommonMob {

    public Slime(Vector vector,int diff) {
        super(vector, 1001,diff);
    }

    @Override
    public int randomLevel(int difficulty) {
        return random(10);
    }

    @Override
    public double initHealth(int level) {
        return level * 10 +20;
    }

    @Override
    public double getXp(int level) {
        return level*10;
    }
}
