package cn.textwar.langs.python;

import cn.textwar.plugins.Event;

import java.io.File;

/**
 * 承载python对象的接口
 */
public interface PyPluginLoader {

    @PythonMethod
    void refreshPlugin();

    @PythonMethod
    void unloadPlugin(String pluginName);

    @PythonMethod
    void loadPlugin(String file);

    @PythonMethod
    void callEvent(String name, int before, Event e);

    default void loadPlugin(String base, String file){
        loadPlugin(new File(base,file).toString());
    }

}
