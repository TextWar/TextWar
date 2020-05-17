package cn.textwar.client.handlers

import cn.qqtextwar.Server
import cn.qqtextwar.api.Application
import cn.textwar.plugins.EventExecutor
import cn.textwar.protocol.ConnectServer
import cn.textwar.protocol.Handler
import com.alibaba.fastjson.JSONObject
import groovy.transform.CompileStatic

import java.util.concurrent.ConcurrentHashMap

/**
 * {
 *     "type" : "image",
 *     "action" : "upload",
 *     "image" : base64,
 *     "id" : player_id
 * }
 * {
 *     "type" : "image",
 *     "action" : "get",
 *     "id" : player_id
 * }
 */
@CompileStatic
class ImageHandler extends Handler {

    private Map<Long,String> images = new ConcurrentHashMap<>()

    ImageHandler() {
        super(["image"],4)
    }

    @Override
    JSONObject execute(Application application, ConnectServer.ClientThread thread, Server server, String type, JSONObject jsonObject, EventExecutor eventExecutor) {
        if("upload" == jsonObject["action"]){
            images[Long.parseLong(jsonObject["id"].toString())] = (String)jsonObject["image"]
            return createResponse(jsonObject,SUCCESS,"upload the image",new JSONObject())
        }
        if("get" == jsonObject["action"]){
            JSONObject rep = new JSONObject()
            Object id = jsonObject["id"]
            if(id == null)return null
            rep["image"] = images[Long.parseLong(id.toString())]
            return createResponse(jsonObject,SUCCESS,"get the image",rep)
        }
        return null
    }
}
