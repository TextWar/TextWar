package cn.qqtextwar.tests;

import cn.qqtextwar.Server;
import cn.textwar.client.ClientApplication;
import cn.textwar.utils.test.Client;
import cn.textwar.utils.test.TextWarServerRunner;
import cn.textwar.utils.test.TextWarServerTest;

@Client({ClientApplication.class})
public class Test10 extends TextWarServerRunner {

    public static void main(String[] args) {
        doTest(new Test10());
    }

    @TextWarServerTest
    public void test(Server server){
        System.out.println(server);
    }
}
