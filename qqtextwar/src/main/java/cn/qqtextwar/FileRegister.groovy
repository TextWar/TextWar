package cn.qqtextwar

import groovy.transform.CompileStatic

@CompileStatic
class FileRegister {

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

    File getConfig(String cfg){
        files.get(cfg)
    }




}
