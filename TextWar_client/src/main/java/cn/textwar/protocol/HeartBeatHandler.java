package cn.textwar.protocol;

import com.alibaba.fastjson.JSONObject;

public class HeartBeatHandler extends ClientHandler {

    @Override
    public JSONObject handle(JSONObject object) {
        System.out.println(object);
        return null;
    }
}
