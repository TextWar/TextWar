package cn.qqtextwar.sql

import cn.qqtextwar.Server
import groovy.sql.DataSet
import groovy.sql.Sql

/**
 * 对于数据库连接的封装
 *
 * @author MagicLu550 @ 卢昶存
 */
class SQLiteConnector {


    private static final String DATABASE = "server.database"

    private Sql sql

    private Server server

    SQLiteConnector(Server server){
        this.server = server
        String url = "jdbc:sqlite:"+server.parser.getHeadValue("${DATABASE}.url")
        String driver = server.parser.getHeadValue("${DATABASE}.driver")
        this.sql = Sql.newInstance(url,driver)
        this.server.logger.info("connect the database : "+url)
    }

    SQLiteConnector create(){
        File file = new File(server.parser.getHeadValue("${DATABASE}.url"))
        if(!file.exists()){
            file.createNewFile()
        }
        this
    }

    void eachRow(SQL sql,Closure closure){
        this.sql.eachRow(sql.sql,closure)
    }

    boolean execute(SQL sql,Object... params){
        this.sql.execute(sql.sql,params)
    }

    int executeUpdate(SQL sql,Object... params){
        this.sql.executeUpdate(sql.sql,params)
    }

    DataSet getTable(String table){
        this.sql.dataSet(table)
    }

    Sql getSql() {
        return sql
    }
}
