
package cn.textwar.plugins;


import cn.qqtextwar.Server;
import cn.qqtextwar.command.Command;
import cn.qqtextwar.log.ILogger;
import cn.qqtextwar.utils.oaml.config.Config;
import cn.qqtextwar.utils.oaml.config.ConfigQueryer;
import cn.qqtextwar.utils.oaml.config.ConfigType;

import java.io.File;



public abstract class PluginBase implements Plugin {


    private boolean haveInit = false;

    private Server server;

    private ILogger logger;

    private String pluginName;

    private boolean isEnabled;

    private File dataFolder;

    private File serverFile;

    private String description;

    private PluginClassLoader classLoader;

    private String version;

    public PluginBase(){
    }

    public void init(ILogger logger, Server server, String pluginName, File serverFile, String description, PluginClassLoader classLoader, File dataFolder, String version){
        if(!haveInit){
            this.logger = logger;
            this.server = server;
            this.pluginName = pluginName;
            this.serverFile = serverFile;
            this.dataFolder = dataFolder;
            this.description = description;
            this.classLoader = classLoader;
            this.version = version;
            this.haveInit = true;
        }
    }

    public boolean isHaveInit() {
        return haveInit;
    }

    public Server getServer() {
        return server;
    }

    public ILogger getLogger() {
        return logger;
    }

    public String getPluginName() {
        return pluginName;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled){
        if(!isEnabled){
            this.isEnabled = true;
            onEnable();
        }
    }

    public File getDataFolder() {
        return dataFolder;
    }

    public File getServerFile() {
        return serverFile;
    }

    public String getDescription() {
        return description;
    }

    public PluginClassLoader getClassLoader() {
        return classLoader;
    }

    public String getVersion(){
        return version;
    }

    public void initConfig(){
        try{
            Config config = ConfigQueryer.getInstance(this.dataFolder+"/config.yml",false, ConfigType.YAML);
            config.save();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void info(String message){
        this.getServer().getLogger().multiInfo(this.getClass(),message,"","");
    }

    public void error(String message){
        this.getServer().getLogger().multiError(this.getClass(),message,"","");
    }

    public void debug(String message){
        this.getServer().getLogger().multiDebug(this.getClass(),message,"","");
    }

    public void warn(String message){
        this.getServer().getLogger().multiWarn(this.getClass(),message,"","");
    }

    public void registerEvents(Listener listener){
        this.getServer().getEventExecutor().registerEvents(listener,this);
    }

    public void registerCommand(Command command){
        this.getServer().getExecutor().registerCommand(command);
    }
}
