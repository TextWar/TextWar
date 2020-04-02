package cn.qqtextwar.sql;

public enum SQL {

    PLAYER_SELECT("SELECT * FROM #{player_table} WHERE NAME = ?"),
    PLAYER_REGISTER("INSERT INTO #{player_table} VALUES(?,?,?,?,?,?,?)")
    ;

    private String sql;

    SQL(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }
}
