package cn.qqtextwar.sql

import cn.qqtextwar.Server
import groovy.sql.DataSet
import groovy.sql.Sql
import org.sqlite.date.DateFormatUtils

import java.lang.reflect.Field
import java.text.SimpleDateFormat

/**
 * 对于数据库连接的封装
 *
 * @author MagicLu550 @ 卢昶存
 */
class SQLiteConnector {

    static final String[] DEFAULT_SQLS = [
            "CREATE TABLE player(id int,name varchar,health int,mana int,joinTime Date,inventoryId int,password varchar,level int,xp int,admin int);"
    ]

    private static final String DATABASE = "server.database"

    private Sql sql

    private Server server

    private boolean first

    SQLiteConnector(Server server){
        this.server = server
        String url = "jdbc:sqlite:"+server.parser.getHeadValue("${DATABASE}.url")
        String driver = server.parser.getHeadValue("${DATABASE}.driver")
        this.first = !new File(server.parser.getHeadValue("${DATABASE}.url")).exists()
        this.sql = Sql.newInstance(url,driver)
        this.server.logger.info("connect the database : [GREEN]"+url+"[DEFAULT]")
    }

    SQLiteConnector createDefault(){
        create(DEFAULT_SQLS)
    }

    def <T> List<T> getByBean(String sql,List<String> params,Class<T> type,String table){
        List<T> list = new ArrayList<>()
        getTable(table).eachRow(sql,params){
            row ->
                T t = type.newInstance()
                Field[] fields = t.class.declaredFields
                for(Field field in fields){
                    field.setAccessible(true)
                    if(field.type == Date.class){
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd")
                        field.set(t,format.parse((String)row."${field.name}"))
                    }else{
                        field.set(t,row."${field.name}")
                    }
                }
                list.add(t)
        }
        return list
    }

    def insertBean(String table,List beans){

        Field[] fields = beans[0].getClass().declaredFields
        beans.each {
            bean->
                int ind = 0
                StringBuilder sql = new StringBuilder("INSERT INTO "+table+"(")
                //) VALUES(
                fields.each {
                    sql.append(it.name)
                    if(ind == fields.length-1){
                        sql.append(") VALUES (")
                    }else{
                        sql.append(",")
                    }
                    ind++
                }
                ind = 0
                fields.each {
                    it.setAccessible(true)

                    if(it.type == Date){
                        java.sql.Date sqlDate1 = new java.sql.Date(((Date)it.get(bean)).getTime())
                        sql.append("'"+sqlDate1.toString()+"'")
                    }else{
                        Object object = it.get(bean)
                        if(object instanceof String){
                            sql.append("'"+object+"'")
                        }else{
                            sql.append(object)
                        }
                    }
                    if(ind == fields.length-1){
                        sql.append(")")
                    }else{
                        sql.append(",")
                    }
                    ind++
                }
                this.getTable(table).execute(sql.toString())
        }

    }

    SQLiteConnector create(String... sql){
        if(first) sql.each {this.sql.execute(it)}
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
