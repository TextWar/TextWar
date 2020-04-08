package cn.qqtextwar.dsl;

import groovy.lang.Closure;

import java.io.File;

/**
 * 这个负责解析server.cfg
 * DSLMethod拦截标签元素
 * @author MagicLu550 @ 卢昶存
 */
public class ServerConfigParser extends DSLParser {

    public ServerConfigParser(File file) {
        super(file);
    }

    /**
     * server标签，为根标签
     */
    public void server(Closure closure){
        closure.call();
    }

    /**
     *负责rpc的属性
     */
    public void rpc(Closure closure){
        closure.call();
    }

    /**
     * 负责配置数据库
     */
    public void database(Closure closure){
        closure.call();
    }

    public void game(Closure closure){
        closure.call();
    }

    public void python(Closure closure){
        closure.call();
    }

    public void player(Closure closure){
        closure.call();
    }

    public void born(Closure closure){
        closure.call();
    }

    public void map(Closure closure){
        closure.call();
    }
}
