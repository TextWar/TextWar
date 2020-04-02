package cn.textwar.protocol;

import cn.qqtextwar.Server;
import cn.textwar.plugins.EventExecutor;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HandlerExecutor {

    public static int NOT_FOUND = 404;

    public static String NOT_FOUND_MESSAGE = "#{type} handler is not found";

    private Map<String, Handler> handlers;

    public HandlerExecutor(){
        handlers = new ConcurrentHashMap<>();
    }

    public void registerHandler(Handler handler){
        handler.getTypes().forEach((type)->handlers.put(type,handler));
    }

    public JSONObject callHandler(ConnectServer.ClientThread thread, String type, JSONObject object, Server server, EventExecutor eventExecutor){
        Handler handler = this.handlers.get(type);
        return handler == null?Handler.createResponse(NOT_FOUND,NOT_FOUND_MESSAGE.replace("#{type}",type),new JSONObject()):handler.executeOption(
                thread,server,type,object,eventExecutor
        );
    }

}
