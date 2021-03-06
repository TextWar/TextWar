package cn.textwar.plugins;


import cn.qqtextwar.utils.oaml.config.YamlConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * 用于读取插件的配置文件plugin.yml，并封装起来
 *
 * @author magiclu550
 */
public class ConfigGetter {

    private static ConfigGetter configGetter;

    static {
        configGetter = new ConfigGetter();
    }

    private ConfigGetter(){
    }

    public static ConfigGetter getConfigGetter(){
        return configGetter;
    }

    public PluginFileVO toDoPluginSet(InputStream in) throws IOException {
        YamlConfig config = new YamlConfig("plugin.yml",false);
        Map propMap = (Map)config.get(".",in);
        String pluginName = propMap.get("name").toString();//pluginName
        String main_class = propMap.get("main").toString();//main_class
        String description = propMap.get("description").toString();//description
        String version = propMap.get("version").toString();//version
        return new PluginFileVO(pluginName,main_class,description,version);
    }
}
