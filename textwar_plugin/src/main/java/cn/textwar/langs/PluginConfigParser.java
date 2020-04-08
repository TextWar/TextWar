package cn.textwar.langs;


import cn.qqtextwar.dsl.DSLMethod;
import cn.qqtextwar.dsl.DSLParser;
import groovy.lang.Closure;

import java.io.File;

public class PluginConfigParser extends DSLParser {

    public PluginConfigParser(File file) {
        super(file);
    }

    @DSLMethod
    public void plugin(Closure closure){
        closure.call();
    }
}
