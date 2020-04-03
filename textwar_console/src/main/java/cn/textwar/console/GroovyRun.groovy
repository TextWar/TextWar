package cn.textwar.console

import cn.qqtextwar.Server

class GroovyRun {

    static void runManager(Server server,String process){
        server.logger.info("running the server manager")
        "JConsole ${process}".execute()
    }
}
