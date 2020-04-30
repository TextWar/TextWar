package cn.qqtextwar.tests;

import cn.qqtextwar.Server;
import cn.textwar.client.ClientApplication;
import cn.textwar.langs.PythonEvent;
import cn.textwar.plugins.EventManager;
import cn.textwar.plugins.Listener;
import cn.textwar.utils.test.Client;
import cn.textwar.utils.test.TextWarAfter;
import cn.textwar.utils.test.TextWarServerRunner;
import cn.textwar.utils.test.TextWarServerTest;

@Client({ClientApplication.class})
public class Test10 extends TextWarServerRunner implements Listener {

    public static void main(String[] args) {
        doTest(Test10.class);
    }

    @TextWarServerTest(doIt = false)
    public void test(Server server){
        server.getLogger().info("[RED]hhhh[DEFAULT]");
    }

    @TextWarServerTest
    public void test0(Server server){
        server.getLogger().info("[BLUE]hello[RED]world[BLUE]![DEFAULT]");

    }
    @TextWarAfter(doIt = false)
    public void close(Server server){
        Server.stop();
    }

    @EventManager
    public void PythonEvent(PythonEvent e){
        System.out.println(e);
    }
}
