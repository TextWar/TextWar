package cn.textwar.protocol;

import com.alibaba.fastjson.JSONObject;

public abstract class ClientHandler {

    public abstract JSONObject handle(JSONObject object);



}
