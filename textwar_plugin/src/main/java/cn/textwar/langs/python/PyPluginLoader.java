package cn.textwar.langs.python;

import cn.textwar.plugins.Event;

import java.io.File;

/**
 * 承载python对象的接口
 */
public interface PyPluginLoader {

    @PythonMethod
    void load_plugins(String baseDir);

    @PythonMethod
    void refresh_plugin();

    @PythonMethod
    void unload_plugin(String pluginName);

    @PythonMethod
    void load_plugin(String file);

    @PythonMethod
    void call_event(String name, int before, Event e);

    default void loadPlugin(String base, String file){
        load_plugin(new File(base,file).toString());
    }

    default void loadPlugins(String baseDir){
        load_plugins(baseDir);
    }

    default void refreshPlugin(){
        refresh_plugin();
    }

    default void unloadPlugin(String f){
        load_plugin(f);
    }

    default void callEvent(String name,int before,Event e){
        call_event(name,before,e);
    }
}
