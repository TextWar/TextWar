package cn.textwar.client.handlers

import cn.qqtextwar.Server
import cn.textwar.protocol.ConnectServer
import cn.textwar.protocol.Handler
import cn.textwar.events.EventExecutor
import com.alibaba.fastjson.JSONObject

/**
 * 玩家登录等操作
 * {
 *     "type" : "player",
 *     "action" : "register",
 *     "name" : "xming",
 *     "password" : 12345
 * }
 */
//TODO 第一次注册，自动生成id，和name绑定，将账号密码等插入数据库
//TODO 获得账号，从数据库获得密码
//TODO 之后比较，来判断密码正确
class PlayerHandler extends Handler{

    PlayerHandler() {
        super(["player"])
    }

    @Override
    JSONObject execute(ConnectServer.ClientThread thread,Server server, String type, JSONObject jsonObject, EventExecutor eventExecutor) {
        if("player" == type){
            return server.getPlayer()
        }
        throw new UnsupportedOperationException(DEFAULT_ERROR)
    }
}
