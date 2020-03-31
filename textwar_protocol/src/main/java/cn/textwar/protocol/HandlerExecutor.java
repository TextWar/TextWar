package cn.textwar.protocol;

import cn.qqtextwar.Server;
import cn.textwar.protocol.plugin.EventExecutor;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HandlerExecutor {

    private Map<String, Handler> handlers;

    public HandlerExecutor(){
        handlers = new ConcurrentHashMap<>();
    }

    public void registerHandler(Handler handler){
        handler.getTypes().forEach((type)->handlers.put(type,handler));
    }

    public JSONObject callHandler(String type, JSONObject object, Server server, EventExecutor eventExecutor){
        return this.handlers.get(type).executeOption(
                server,type,object,eventExecutor
        );
    }

}
