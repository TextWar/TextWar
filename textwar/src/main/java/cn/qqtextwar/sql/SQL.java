package cn.qqtextwar.sql;

public enum SQL {

    GET_ID_BY_NAME("SELECT id FROM #{player_table} where name = ?"),
    GET_MAX_ID("SELECT id FROM #{player_table} order by id desc;"),
    PLAYER_SELECT("SELECT * FROM #{player_table} WHERE NAME = ?"),
    PLAYER_LEVEL("INSERT INTO #{player_table} (level) VALUES(?)"),
    PLAYER_XP("INSERT INTO #{player_table} (xp) VALUES(?)")
    ;

    private String sql;

    SQL(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }
}
