package cn.textwar.protocol;

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

    public JSONObject callHandler(String type,JSONObject object){
        return this.handlers.get(type).execute(
                type,object
        );
    }

}
