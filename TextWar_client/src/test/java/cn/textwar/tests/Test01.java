package cn.textwar.tests;

import cn.textwar.protocol.TextWarClient;
import org.junit.Test;

public class Test01 {

    @Test
    public void test01(){
        new TextWarClient("127.0.0.1",8765).connect().sync();
        while (true);
    }
}
