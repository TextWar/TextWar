package cn.textwar.client.handlers

import cn.qqtextwar.Server
import cn.qqtextwar.api.Application
import cn.textwar.plugins.EventExecutor
import cn.textwar.protocol.ConnectServer
import cn.textwar.protocol.Handler
import com.alibaba.fastjson.JSONObject

/**
 * {
 *     "type" : "action",
 *     "player" : "laolu",
 *     "name" : "w"
 * }
 * {
 *     "type" : "command",
 *     "player" : "laolu",
 *     "name" : "/hello"
 *     "args" : ["1","2"]
 * }
 */
//TODO 未来会加权限部分
class CommandHandler extends Handler {

    CommandHandler() {
        super(["action","command"])
    }

    @Override
    JSONObject execute(Application application, ConnectServer.ClientThread thread, Server server, String type, JSONObject jsonObject, EventExecutor eventExecutor) {
        if("action" == type){

        }else if("command" == type){

        }
        return null
    }
}
