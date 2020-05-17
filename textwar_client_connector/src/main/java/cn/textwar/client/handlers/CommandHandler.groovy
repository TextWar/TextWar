package cn.textwar.client.handlers

import cn.qqtextwar.Server
import cn.qqtextwar.api.Application
import cn.qqtextwar.sql.PlayerDAO
import cn.textwar.client.ClientApplication
import cn.textwar.plugins.EventExecutor
import cn.textwar.protocol.ConnectServer
import cn.textwar.protocol.Handler
import com.alibaba.fastjson.JSONObject
import groovy.transform.CompileStatic

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
@CompileStatic
class CommandHandler extends Handler {

    private PlayerDAO playerDAO

    CommandHandler() {
        super(["action","command"],3)
    }

    @Override
    JSONObject execute(Application application, ConnectServer.ClientThread thread, Server server, String type, JSONObject jsonObject, EventExecutor eventExecutor) {
        if(playerDAO == null)this.playerDAO = (application as ClientApplication).daoFactory.playerDAO
        if("action" == type){
            int id = playerDAO.getId(jsonObject["playerName"] as String)
            return createResponse(jsonObject,SUCCESS,server.executor.doCommandOrAction(jsonObject["name"] as String,id,new String[0]),new JSONObject())
        }else if("command" == type){
            int id = playerDAO.getId(jsonObject["playerName"] as String)
            return createResponse(jsonObject,SUCCESS,server.executor.doCommandOrAction(jsonObject["name"] as String,id,(jsonObject["args"] as List).toArray() as String[]),new JSONObject())
        }
        return null
    }
}
