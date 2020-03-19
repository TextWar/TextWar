package cn.qqtextwar.entity;

import cn.qqtextwar.math.Vector;

public class Mob extends Entity{

    private boolean harmful;

    public Mob(Vector vector, long id, double healthPoints, double manaPoints,boolean harmful) {
        super(vector, id, healthPoints, manaPoints);
    }

    public enum MobEnum {
        NINE(9),
        TEN(10),
        ELEVEN(11),
        TWELVE(12),
        THIRTEEN(13),
        FOURTEEN(14),
        FIFTEEN(15);
        public final long mapValue;

        MobEnum(long value) {
            this.mapValue = value;
        }
    }

    public boolean isHarmful() {
        return harmful;
    }
}
