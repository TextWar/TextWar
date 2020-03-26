package cn.qqtextwar.sql;

public enum SQL {

    ;

    private String sql;

    SQL(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }
}
