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
 * @author MagicLu
 */
@CompileStatic
class FileRegister {

    /**
     * Server.cfg 服务端的主文件，参见resources/server.cfg
     */
    static final String MAIN_CONFIG = "server.cfg"

    private List<String> resources

    private File baseFile

    private Map<String,File> files = [:]

    FileRegister(Server server){
        this.baseFile = server.baseFile
        resources = [
                MAIN_CONFIG
        ]
    }

    void register(){
        resources.each {
            File file = new File(baseFile,it)
            if(!file.exists())
                Utils.readClassStream(file,it)
            files.put(it,file)
        }
    }

    @Memoized
    File getConfig(String cfg){
        files.get(cfg)
    }




}
