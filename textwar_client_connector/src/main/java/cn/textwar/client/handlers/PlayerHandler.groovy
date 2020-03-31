package cn.textwar.client.handlers

import cn.qqtextwar.Server
import cn.textwar.protocol.Handler
import cn.textwar.protocol.plugin.EventExecutor
import com.alibaba.fastjson.JSONObject

/**
 * 玩家登录等操作
 * {
 *     "type" : "player",
 *     "action" : "register"
 *     "
 * }
 */
class PlayerHandler extends Handler{

    PlayerHandler() {
        super(["player"])
    }

    @Override
    JSONObject execute(Server server, String type, JSONObject jsonObject,EventExecutor eventExecutor) {
        return null
    }
}
