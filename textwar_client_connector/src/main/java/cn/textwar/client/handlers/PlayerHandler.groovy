package cn.textwar.client.handlers

import cn.qqtextwar.Server
import cn.textwar.protocol.Handler
import com.alibaba.fastjson.JSONObject

class PlayerHandler extends Handler{

    PlayerHandler(List<String> types) {
        super(types)
    }

    @Override
    JSONObject execute(Server server, String type, JSONObject jsonObject) {
        return null
    }
}
