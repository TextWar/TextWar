package cn.qqtextwar.entity;

import cn.qqtextwar.math.Vector;

public class Freak extends Entity {

    public Freak(Vector vector, long id) {
        super(vector, id);
    }

    public enum FreakEnum {
        NINE(9),
        TEN(10),
        ELEVEN(11),
        TWELVE(12),
        THIRTEEN(13),
        FOURTEEN(14),
        FIFTEEN(15);
        public final long mapValue;

        FreakEnum(long value) {
            this.mapValue = value;
        }
    }
}
