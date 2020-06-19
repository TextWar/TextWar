package cn.textwar.client.handlers

import cn.qqtextwar.Server
import cn.qqtextwar.api.Application
import cn.textwar.plugins.EventExecutor
import cn.textwar.protocol.ConnectServer
import cn.textwar.protocol.Handler
import com.alibaba.fastjson.JSONObject

/**
 * 玩家id,小地图范围
 * {
 *     "type" : "update",
 *     "action" : "player_move",
 *     "player_id" : xxxx,
 *     "rad" : 5
 * }
 * 返回这个地图范围内的实体信息(怪物类型，坐标）
 *
 * 未来添加玩家方法doUpdate,服务端当监测到对应玩家的范围内有实体变动，就会调用其doUpdate更新其范围内的怪物给客户端
 */
class UpdateMapHandler extends Handler{


    UpdateMapHandler(List<String> types, int id) {
        super(types, id)
    }

    @Override
    JSONObject execute(Application application, ConnectServer.ClientThread thread, Server server, String type, JSONObject jsonObject, EventExecutor eventExecutor) {

        return null
    }
}
