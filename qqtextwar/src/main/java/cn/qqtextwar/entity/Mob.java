package cn.qqtextwar.entity;

import cn.qqtextwar.math.Vector;

public class Mob extends Entity{

    private boolean harmful;

    public Mob(Vector vector, long id, double healthPoints, double manaPoints,boolean harmful) {
        super(vector, id, healthPoints, manaPoints);
    }

    public enum MobEnum {
        NINE(9,100,100),
        TEN(10,200,200),
        ELEVEN(11,300,300),
        TWELVE(12,400,400),
        THIRTEEN(13,500,500),
        FOURTEEN(14,600,600),
        FIFTEEN(15,700,700);
        public final long mapValue;
        public final int health;
        public final int manaPoints;

        MobEnum(long value,int health,int manaPoints) {
            this.mapValue = value;
            this.health = health;
            this.manaPoints = manaPoints;
        }
    }

    public boolean isHarmful() {
        return harmful;
    }
}
