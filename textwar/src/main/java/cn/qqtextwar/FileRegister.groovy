package cn.qqtextwar

import cn.qqtextwar.utils.Utils
import groovy.transform.CompileStatic
import groovy.transform.Memoized

/**
 * FileRegister
 * 用于管理服务端全部的配置文件，目的为了有有序的进行配置的读取。
 * 方便管理和添加配置.
 * 这里的文件名字必须是在resources文件夹下存在的。最终会被复制到服务端的根目录下。
 *
 * @author MagicLu @ 卢昶存
 */
@CompileStatic
class FileRegister {

    /**
     *
     * Server.cfg 服务端的主文件，参见resources/server.cfg
     */
    static final String MAIN_CONFIG = "server.cfg"

    static final String IMAGE = "image"

    static final String MAP = "map"

    static final String PLUGIN = "plugins"

    /**
     * 这里存储注册的全部文件
     */
    private List<String> resources

    /**
     * 从Server对象获得的根目录
     */
    private File baseFile

    /**
     * 这里存储后面注册的映射，为文件名对应根目录下的文件对象
     */
    private Map<String,File> files = [:]

    private List<String> dirs

    FileRegister(Server server){
        this.baseFile = server.baseFile
        resources = [
                MAIN_CONFIG
        ]
        dirs = [
                IMAGE,
                MAP,
                PLUGIN
        ]
    }

    void register(){
        resources.each {
            register(it)
        }
        dirs.each {
            createDir(it)
        }
    }

    void register(String it){
        File file = new File(new File(baseFile,it).toURI().getPath())
        if(!file.exists())
            Utils.readClassStream(file,it)
        files.put(it,file)
    }

    void createDir(String it){
        File file = new File(baseFile,it)
        file.mkdirs()
        files.put(it,file)
    }

    @Memoized
    File getConfig(String cfg){
        files[cfg]
    }




}
