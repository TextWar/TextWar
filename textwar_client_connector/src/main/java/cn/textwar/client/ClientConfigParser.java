package cn.textwar.client;

import cn.qqtextwar.dsl.DSLMethod;
import cn.qqtextwar.dsl.DSLParser;
import groovy.lang.Closure;

import java.io.File;

public class ClientConfigParser extends DSLParser {

    public ClientConfigParser(File file) {
        super(file);
    }

    @DSLMethod
    public void client(Closure closure){
        closure.call();
    }
}
