package cn.qqtextwar.sql

import cn.qqtextwar.Server
import com.alibaba.druid.pool.DruidDataSource

import java.sql.Connection

class SQLiteConnetor {

    private static final String DATABASE = "server.database"

    private Server server

    private Connection connection


    SQLiteConnetor(Server server){
        this.server = server
        DruidDataSource dataSource = new DruidDataSource()
        dataSource.url = server.parser.getHeadValue("${DATABASE}.url")
        dataSource.driverClassName = server.parser.getHeadValue("${DATABASE}.driver")
        this.connection = dataSource.getConnection()
    }

    Connection getConnection() {
        return connection
    }

}
