package cn.qqtextwar.entity;

import cn.qqtextwar.math.Vector;

public class Slime extends Mob {

    public Slime(Vector vector, long id, double healthPoints, double manaPoints) {
        super(vector, id, healthPoints, manaPoints, false);
    }
}
