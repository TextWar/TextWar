package cn.textwar.client.handlers

import cn.qqtextwar.Server
import cn.qqtextwar.api.Application
import cn.textwar.protocol.ConnectServer
import cn.textwar.protocol.Handler
import cn.textwar.plugins.EventExecutor
import com.alibaba.fastjson.JSONObject
import groovy.transform.CompileStatic

/**
 * 关于地图json的处理
 * 获得地图
 * {
 *     "type" : "map",
 *     "action" : "update",
 *     "player" : 12331222, //这里会根据玩家找到其附近的方块
 *     "rad" : 5
 * }
 */
@CompileStatic
class MapHandler extends Handler {

    MapHandler() {
        super(["map"],1)
    }

    @Override
    JSONObject execute(Application application,ConnectServer . ClientThread thread, Server server, String type, JSONObject jsonObject, EventExecutor eventExecutor) {
        long id = Long.parseLong(jsonObject["player"].toString())
        if("update" == jsonObject["action"]){
            return createResponse(SUCCESS,DEFAULT_SUCCESS_MESSAGE,server.getGameMap().interceptForPlayer(server.getPlayer(id),(Integer)jsonObject["rad"]).toJson())
        }
        return null
    }
}
