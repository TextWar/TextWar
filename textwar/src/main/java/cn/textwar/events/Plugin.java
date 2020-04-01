
package cn.textwar.events;


import cn.qqtextwar.Server;
import cn.qqtextwar.log.ILogger;

import java.io.File;


/**
 * 定义了一个插件对象相关的接口
 * @author magiclu550
 */
public interface Plugin {

    default void onLoad(){

    }

    default void onEnable(){

    }

    default void onDisable(){

    }

    void init(ILogger logger, Server server, String pluginName, File serverFile, String description, PluginClassLoader classLoader, File dataFolder, String version);

    boolean isHaveInit();

    Server getServer();

    ILogger getLogger();

    String getPluginName();

    boolean isEnabled();

    void setEnabled(boolean enabled);

    File getDataFolder();

    File getServerFile();

    String getDescription();

    PluginClassLoader getClassLoader();

    String getVersion();
}
