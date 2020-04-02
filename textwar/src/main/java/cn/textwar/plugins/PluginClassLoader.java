
package cn.textwar.plugins;

import cn.qqtextwar.FileRegister;
import cn.qqtextwar.Server;
import cn.qqtextwar.ex.PluginException;
import cn.qqtextwar.log.ILogger;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * 用于加载插件的主类和配置信息
 *
 * @author magiclu550
 */

public class PluginClassLoader {

    public static final String PACKAGE = "cn.textwar";

    private Server server;
    private Map<String,String> pluginInfos = new HashMap<>();//文件名 介绍

    private Map<String,String> pluginNames = new HashMap<>();//文件名 插件名

    private Map<String,String> nameFiles = new HashMap<>();//插件名 文件名

    private Map<String, Plugin> namePlugins = new HashMap<>();


    private File[] files;

    private List<Plugin> plugins = new ArrayList<>();

    public PluginClassLoader(Server server){
        this.server = server;
    }


    private List<File> jarFiles = new ArrayList<>();

    public List<Plugin> loadPlugins(File pluginDir){
        files = pluginDir.listFiles();//all jar files
        loadFiles(files);
        for(File jar:jarFiles){
            plugins.add(loadPlugin(jar));
        }
        return plugins;
    }

    public void loadPlugin(String file){
        loadPlugin(new File(file));
    }

    //可以加载指定名字的插件
    private Plugin loadPlugin(File jar,String[] pName){
        if(!jar.getName().endsWith(".jar"))return null;
        if(pluginNames.containsKey(jar.getName())){
            return namePlugins.get(pluginNames.get(jar.getName()));
        }
        try{
            URL url = jar.toURI().toURL();

            URLClassLoader classLoader = new URLClassLoader(new URL[]{url},this.getClass().getClassLoader());

            JarFile file = new JarFile(jar);

            ZipEntry entry = file.getEntry("plugin.yml");

            if(entry!=null) {

                InputStream in = file.getInputStream(entry);

                PluginFileVO vo = ConfigGetter.getConfigGetter().toDoPluginSet(in);

                for (Plugin plu : plugins) {
                    if (plu.getPluginName().equals(vo.getPluginName())) {
                        plugins.remove(plu);
                    }
                }
                Object plugin;
                if(!vo.getMain_class().startsWith(PACKAGE)) {
                    pluginInfos.put(jar.getName(),vo.getDescription());
                    plugin = classLoader.loadClass(vo.getMain_class()).newInstance();
                }else{
                    throw new PluginException("the main class could not start with 'cn.jsmod2'");
                }

                return loadPluginInfo(plugin,vo,file,classLoader);


            }else{
                Enumeration<JarEntry> entries = file.entries();
                while (entries.hasMoreElements()){
                    JarEntry entry1 = entries.nextElement();
                    String name = entry1.getName();
                    if(name.endsWith(".class")){
                        String mainName = name.substring(0,name.lastIndexOf(".")).replace("/",".");
                        if(mainName.startsWith(PACKAGE)) {
                            server.getLogger().error("the main class could not start with 'cn.jsmod2'");
                            continue;
                        }
                        Class<?> pluginClass = classLoader.loadClass(mainName);

                        Main main = pluginClass.getAnnotation(Main.class);
                        if(main!=null){
                            if(pName!=null&&!Arrays.asList(pName).contains(main.name())){
                                return null;
                            }
                            if(nameFiles.containsKey(main.name())){
                                return namePlugins.get(main.name());
                            }
                            pluginInfos.put(jar.getName(),main.description());
                            nameFiles.put(main.name(),jar.getName());
                            pluginNames.put(jar.getName(),main.name());
                            PluginFileVO vo = new PluginFileVO(main.name(),pluginClass.getName(),main.description(),main.version());
                            Object obj = pluginClass.newInstance();
                            return loadPluginInfo(obj,vo,file,classLoader);
                        }
                    }
                }
            }

        }catch (Exception e){
            server.getLogger().error(e.toString());
        }
        return null;
    }


    public Plugin loadPlugin(File jar) {

        return loadPlugin(jar,null);
    }

    public Map<String, String> getPluginInfos() {
        return pluginInfos;
    }

    public void unloadPlugin(String name){
        Plugin removed = null;
        for(int i = 0;i<plugins.size();i++){
            if(plugins.get(i).getPluginName().equals(name)){
                removed = plugins.get(i);
                break;
            }
        }

        if(removed !=null) {
            removed.onDisable();
            plugins.remove(removed);
            pluginInfos.remove(nameFiles.get(name));
            pluginNames.remove(nameFiles.get(name));
            nameFiles.remove(name);
        }else
            throw new PluginException("no such plugin");
    }

    public Plugin loadPluginInfo(Object plugin,PluginFileVO vo,JarFile jarFile,URLClassLoader loader) throws Exception{


        if (plugin instanceof PluginBase) {

            ILogger logger = server.getLogger();

            Plugin pluginObject = ((PluginBase) plugin);

            namePlugins.put(vo.getPluginName(),pluginObject);//放进去

            //检测如果有loadBefore

            LoadBefore before = pluginObject.getClass().getAnnotation(LoadBefore.class);

            if(before != null){
                int dep = 0;
                //那么先判断loadBefore的内容
                String[] args = before.pluginName();
                for(File file:files){
                    //如果发现了名字,则先加载改名字的插件
                    Plugin p = loadPlugin(file,args);
                    if(p !=null){
                        dep++;
                    }
                }
                if(dep < args.length){
                    server.getLogger().error("you could not have the dependencies:","","");
                    for(String d:args){
                       server.getLogger().error(d);
                    }
                }
            }

            File dataFolder = new File(server.getRegister().getConfig(FileRegister.getPLUGIN()) + "/" + vo.getPluginName());
            if(!dataFolder.exists()){
                dataFolder.mkdirs();
            }
            pluginObject.init(logger, server, vo.getPluginName(), server.getBaseFile(), vo.getDescription(), this, dataFolder, vo.getVersion());

            pluginObject.onLoad();

            pluginObject.setEnabled(true);

            server.getLogger().multiInfo(this.getClass(),"the plugin named:" + vo.getPluginName() + " is loading.. version: " + vo.getVersion(),"","");

            EnableRegister register = pluginObject.getClass().getAnnotation(EnableRegister.class);

            if(register!=null){


                List<Class<? extends Listener>> exclusionsListener = Arrays.asList(register.exclusionsListener());

                Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    String name = entry.getName();
                    if(name.endsWith(".class")){
                        Object obj;
                        Class<?> clz = loader.loadClass(name.substring(0,name.lastIndexOf(".")).replace("/","."));
                        if(register.listener()) {
                            if (Arrays.asList(mostSuperClass(clz).getInterfaces()).contains(Listener.class) && !exclusionsListener.contains(clz)) {
                                obj = clz.newInstance();
                                logger.multiInfo(this.getClass(),"loading listener","","");
                                server.getEventExecutor().registerEvents((Listener) obj, pluginObject);
                            }
                        }
                    }
                }
            }
            return pluginObject;
        } else {

            throw new PluginException("the main class must be the PluginBase's subclass");

        }
    }

    private Class<?> mostSuperClass(Class<?> clzz){
        while (true){
            Class<?> sub = clzz;
            clzz = clzz.getSuperclass();
            if(clzz.equals(Object.class))
                return sub;
        }
    }


    private void loadFiles(File[] files){
        if(files!=null){
            for(File file:files){
                if(file.getName().endsWith(".jar")){
                    jarFiles.add(file);
                }else{
                    loadFiles(file.listFiles());
                }
            }
        }
    }

    public List<Plugin> getPlugins(){
        return plugins;
    }

}
