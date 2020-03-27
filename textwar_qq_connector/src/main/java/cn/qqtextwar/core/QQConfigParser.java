package cn.qqtextwar.core;

import cn.qqtextwar.dsl.DSLMethod;
import cn.qqtextwar.dsl.DSLParser;
import groovy.lang.Closure;

import java.io.File;

public class QQConfigParser extends DSLParser {

    public QQConfigParser(File file) {
        super(file);
    }

    @DSLMethod
    public void bot(Closure closure){
        closure.call();
    }

}
