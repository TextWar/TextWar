package cn.qqtextwar.sql

/**
 * 负责进行玩家的操作
 */
class PlayerDAO {

    public static final String PLAYER_TABLE = "player"

    public static final String PLAYER_TABLE_VAR = "#{player_table}"
    private SQLiteConnector database


    PlayerDAO(SQLiteConnector database) {
        this.database = database
    }

    //如果已经存在玩家，则比较password和id
    //如果不存在玩家，则插入数据，返回true
    boolean registerPlayer(String name){
        database.getTable(PLAYER_TABLE).eachRow(SQL.PLAYER_SELECT.name().replace(PLAYER_TABLE_VAR,PLAYER_TABLE),[name]){

        }
    }
}
