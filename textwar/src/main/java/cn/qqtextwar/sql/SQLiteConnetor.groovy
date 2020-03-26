package cn.qqtextwar.sql

import cn.qqtextwar.Server
import groovy.sql.DataSet
import groovy.sql.Sql

/**
 * 对于数据库连接的封装
 *
 * @author MagicLu550 @ 卢昶存
 */
class SQLiteConnetor {


    private static final String DATABASE = "server.database"

    private Sql sql

    SQLiteConnetor(Server server){
        String url = server.parser.getHeadValue("${DATABASE}.url")
        String driver = server.parser.getHeadValue("${DATABASE}.driver")
        this.sql = Sql.newInstance(url,driver)
    }

    void eachRow(SQL sql,Closure closure){
        this.sql.eachRow(sql.sql,closure)
    }

    String execute(SQL sql,Object... params){
        this.sql.execute(sql.sql,params)
    }

    int executeUpdate(SQL sql,Object... params){
        this.sql.executeUpdate(sql.sql,params)
    }

    DataSet getTable(String table){
        this.sql.dataSet(table)
    }



}
