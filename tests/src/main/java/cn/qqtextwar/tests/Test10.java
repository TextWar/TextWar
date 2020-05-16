package cn.qqtextwar.tests;

import cn.qqtextwar.Server;
import cn.textwar.client.ClientApplication;
import cn.textwar.langs.PythonEvent;
import cn.textwar.plugins.EventManager;
import cn.textwar.plugins.Listener;
import cn.textwar.plugins.NativeListener;
import cn.textwar.plugins.events.CommandExecuteEvent;
import cn.textwar.utils.test.*;


@Client({ClientApplication.class})
@NativeListener
@Close
public class Test10 extends TextWarServerRunner implements Listener {


    @TextWarServerTest(doIt = false)
    public void test(Server server){
        server.getLogger().info("[RED]hhhh[DEFAULT]");
    }

    @TextWarServerTest
    public void test0(Server server){
        server.getLogger().info("[BLUE]hello[RED]world[BLUE]![DEFAULT]");

    }
    @EventManager
    public void pythonEvent(PythonEvent e){
        System.out.println(e);
    }

    @EventManager
    public void commandEvent(CommandExecuteEvent e){
        e.setCancelled(true);
        System.out.println(111);
    }
}
