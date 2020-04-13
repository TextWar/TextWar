package cn.qqtextwar.tests;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.net.Socket;

public class Test08 {

    public static void main(String[] args) {
        System.out.println(JSONObject.parseObject("{'a':'b'}\r\n"));
    }
}
