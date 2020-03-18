package cn.qqtextwar.dsl;

import groovy.lang.Closure;

import java.io.File;

public class ServerConfigParser extends DSLParser {

    public ServerConfigParser(File file) {
        super(file);
    }

    @DSLMethod
    public void server(Closure closure){
        closure.call();
    }

    @DSLMethod
    public void rpc(Closure closure){
        closure.call();
    }

    @DSLMethod
    public void database(Closure closure){
        closure.call();
    }
}
