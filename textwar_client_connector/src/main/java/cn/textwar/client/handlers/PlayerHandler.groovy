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
 *     "password" : 12345
 * }
 * {
 *     "type" : "player",
 *     "action" : "login",
 *     "name" : "xming",
 *     "password" : 12345
 * }
 *
 */
//TODO 第一次注册，自动生成id，和name绑定，将账号密码等插入数据库
//TODO 获得账号，从数据库获得密码
//TODO 之后比较，来判断密码正确
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
                //TODO rad未来为能见度
                thread.properties.put("id",p.id) //存储id
                return createResponse(SUCCESS,"you have been ${jsonObject.get("action")}",server.gameMap.interceptForPlayer(p,5).toJson())
            }

        }
        throw new UnsupportedOperationException(DEFAULT_ERROR)
    }
}
