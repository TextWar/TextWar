package cn.textwar.console;

import cn.qqtextwar.dsl.DSLMethod;
import cn.qqtextwar.dsl.DSLParser;
import groovy.lang.Closure;

import java.io.File;

public class ConsoleConfigParser extends DSLParser {

    public ConsoleConfigParser(File file) {
        super(file);
    }

    @DSLMethod
    public void console(Closure closure){
        closure.call();
    }
}
