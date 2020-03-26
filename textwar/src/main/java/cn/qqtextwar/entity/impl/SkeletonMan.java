package cn.qqtextwar.entity.impl;

import cn.qqtextwar.entity.Freak;
import cn.qqtextwar.math.Vector;

/**
 * 骷髅人
 */
public class SkeletonMan extends Freak {

    public SkeletonMan(Vector vector, int difficulty) {
        super(vector,1000,difficulty);
    }

    @Override
    public double initHealth(int level) {
        return level * 100 + 10;
    }

    @Override
    public double initManaPoints(int level) {
        return level * 100 + 10;
    }

    @Override
    public int randomLevel(int difficulty) {
        return random(10);
    }

    @Override
    public int initAggressivity(int level) {
        return level * 10;
    }

    @Override
    public double getXp(int level) {
        return level * 100;
    }
}
