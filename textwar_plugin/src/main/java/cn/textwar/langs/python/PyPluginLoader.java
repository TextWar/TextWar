package cn.textwar.langs.python;

import cn.qqtextwar.Server;
import cn.qqtextwar.entity.player.Player;
import cn.qqtextwar.log.ServerLogger;
import cn.textwar.langs.PyEventCaller;
import cn.textwar.plugins.Event;

import java.io.File;
import java.util.Map;

/**
 * 承载python对象的接口
 */
public interface PyPluginLoader {

    @PythonMethod
    void getServer(Server server);

    @PythonMethod
    void getPyEventExec(PyEventCaller caller);

    @PythonMethod
    void refreshPlugins();

    @PythonMethod
    void unloadPlugin(String pluginName);

    @PythonMethod
    void setPluginsDir(String file);

    @PythonMethod
    void loadPlugin(String file);

    @PythonMethod
    void callEvent(String name, int before, Event e);

    @PythonMethod
    Map getCommands();

    @PythonMethod
    Map getActions();

    @PythonMethod
    void commandExecutor(String name, String[] args, Player player);

    default void loadPlugin(String base, String file){
        loadPlugin(new File(base,file).toString());
    }

}
