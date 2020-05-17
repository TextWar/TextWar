package cn.textwar.tests;

import cn.textwar.protocol.TextWarClient;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

public class Test01 {

    @Test
    public void test01(){
        TextWarClient client = new TextWarClient("127.0.0.1",8765).connect().sync();
        JSONObject object  = new JSONObject();
        object.putAll(JSONObject.parseObject("{\n" +
                "    \"type\" : \"player\",\n" +
                "    \"action\" : \"login\",\n" +
                "    \"name\" : \"1234xiaolu111\",\n" +
                "    \"password\" : \"12345643\",\n" +
                "    \"rad\"    : 5 " +
                "}"));
        client.write(object);
        while (true);
    }
}
