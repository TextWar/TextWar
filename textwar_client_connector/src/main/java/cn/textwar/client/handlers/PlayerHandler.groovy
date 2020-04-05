package cn.textwar.client.handlers

import cn.qqtextwar.Server
import cn.qqtextwar.api.Application
import cn.qqtextwar.entity.player.Player
import cn.qqtextwar.ex.ServerException
import cn.qqtextwar.sql.PlayerDAO
import cn.textwar.plugins.events.PlayerJoinEvent
import cn.textwar.protocol.ConnectServer
import cn.textwar.protocol.Handler
import cn.textwar.plugins.EventExecutor
import com.alibaba.fastjson.JSONObject

/**
 * 玩家登录等操作
 * {
 *     "type" : "player",
 *     "action" : "register",
 *     "name" : "xming",
 *     "password" : 12345,
 *     "rad"    : 10
 * }
 * {
 *     "type" : "player",
 *     "action" : "login",
 *     "name" : "xming",
 *     "password" : 12345,
 *     "rad"    : 5
 * }
 *
 */
class PlayerHandler extends Handler{


    private PlayerDAO dao

    PlayerHandler() {
        super(["player"])
    }

    @Override
    JSONObject execute(Application application,ConnectServer . ClientThread thread, Server server, String type, JSONObject jsonObject, EventExecutor eventExecutor) {
        if(dao == null)this.dao = new PlayerDAO(server.database)
        if("player" == type){
            String name = jsonObject.get("name")
            Player player = server.getPlayerReturnNull(name.hashCode() + 10000)
            if(player != null){
                throw new ServerException("the player has logined")
            }else{
                Player p = server.registerPlayer(application,thread.socket.inetAddress.hostName,name.hashCode() + 10000,server.gameMap)
                String action = jsonObject.get("action")
                try{
                    dao.registerPlayer(action == "register" ,name,jsonObject.get("password").toString(),p)
                }catch(ServerException e){
                    server.removePlayer(p)
                    throw e
                }
                server.eventExecutor.callEvent(new PlayerJoinEvent(p),0)
                p.addInto(server.gameMap)
                server.eventExecutor.callEvent(new PlayerJoinEvent(p),1)
                thread.properties.put("id",p.id) //存储id
                return createResponse(SUCCESS,"you have been ${jsonObject.get("action")}ed",server.gameMap.interceptForPlayer(p,(Integer)jsonObject["rad"]).toJson())
            }

        }
        throw new UnsupportedOperationException(DEFAULT_ERROR)
    }
}
