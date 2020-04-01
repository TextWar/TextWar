package cn.textwar.langs.python;

import java.io.File;
import java.util.List;

/**
 * 承载python对象的接口
 */
public interface PyPluginLoader {


    List<PythonPlugin> load_plugins(String baseDir);

    void refresh_plugin();

    PythonPlugin load_plugin(String file);

    default PythonPlugin loadPlugin(String base,String file){
        return load_plugin(new File(base,file).toString());
    }

    default List<PythonPlugin> loadPlugins(String baseDir){
        return load_plugins(baseDir);
    }

    default void refreshPlugin(){
        refresh_plugin();
    }
}
