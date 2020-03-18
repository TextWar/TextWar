package cn.qqtextwar.entity;

public class Freaks extends Entity {

    public Freaks(Vector vector, long id) {
        super(vector, id);
    }

    public enum FreaksEnum {
        NINE(9),
        TEN(10),
        ELEVEN(11),
        TWELVE(12),
        THIRTEEN(13),
        FOURTEEN(14),
        FIFTEEN(15);
        public final long mapValue;

        FreaksEnum(long value) {
            this.mapValue = value;
        }
    }
}
