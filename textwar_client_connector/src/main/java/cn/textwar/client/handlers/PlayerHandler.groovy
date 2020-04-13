package cn.textwar.client.handlers

import cn.qqtextwar.Server
import cn.qqtextwar.api.Application
import cn.qqtextwar.entity.player.Player
import cn.qqtextwar.ex.ServerException
import cn.qqtextwar.sql.PlayerDAO
import cn.textwar.client.ClientApplication
import cn.textwar.plugins.events.PlayerJoinEvent
import cn.textwar.protocol.ConnectServer
import cn.textwar.protocol.Handler
import cn.textwar.plugins.EventExecutor
import com.alibaba.fastjson.JSONObject
import groovy.transform.CompileStatic

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
@CompileStatic
class PlayerHandler extends Handler{

    private PlayerDAO dao

    PlayerHandler() {
        super(["player"],2)
    }

    @Override
    JSONObject execute(Application application,ConnectServer . ClientThread thread, Server server, String type, JSONObject jsonObject, EventExecutor eventExecutor) {
        if(dao == null)this.dao = (application as ClientApplication).daoFactory.playerDAO
        if("player" == type){
            String name = jsonObject.get("name")
            int id = dao.getId(name)
            if(id == -1){
                id = dao.getMaxId()+1
            }
            Player player = server.getPlayerReturnNull(id)
            if(player != null){
                throw new ServerException("the player has logined")
            }else{
                Player p = server.registerPlayer(application,thread.socket.inetAddress.hostName,id,server.gameMap)
                String action = jsonObject.get("action")
                thread.properties.put("id",p.id) //存储id
                try{
                    dao.registerPlayer(action == "register" ,name,jsonObject.get("password").toString(),p)
                }catch(ServerException e){
                    server.removePlayer(p)
                    throw e
                }
                server.eventExecutor.callEvent(new PlayerJoinEvent(p),0)
                p.addInto(server.gameMap)
                server.eventExecutor.callEvent(new PlayerJoinEvent(p),1)
                return createResponse(SUCCESS,"you have been ${jsonObject.get("action")}ed",server.gameMap.interceptForPlayer(p,(Integer)jsonObject["rad"]).toJson())
            }

        }
        return null
    }
}
