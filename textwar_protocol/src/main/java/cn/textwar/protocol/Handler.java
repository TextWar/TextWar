package cn.textwar.protocol;

import cn.qqtextwar.Server;
import cn.qqtextwar.api.Application;
import cn.textwar.plugins.EventExecutor;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

//注意线程安全
//在使用Handler时
public abstract class Handler {

    public static final String DEFAULT_ERROR = "not support yet";

    public static final String STATE = "state";

    public static final String DATA = "data";

    public static final String MESSAGE = "message";

    public static final String DEFAULT_SUCCESS_MESSAGE = "success";

    // 成功
    public static final int SUCCESS = 200;

    // 发生异常
    public static final int ERROR = 500;

    // 找不到Handler
    public static int NOT_FOUND = 404;

    public static int CLOSE = 100;


    public Handler(List<String> types) {
        this.types = types;
    }

    private List<String> types;

    public JSONObject executeOption(Application application,ConnectServer.ClientThread thread, Server server, String type, JSONObject jsonObject, EventExecutor eventExecutor){
        try{
            return execute(application,thread,server,type,jsonObject,eventExecutor);
        }catch (Exception e){
            e.printStackTrace();
            return createResponse(ERROR,e.getMessage(),new JSONObject());
        }
    }

    public abstract JSONObject execute(Application application,ConnectServer.ClientThread thread, Server server, String type, JSONObject jsonObject, EventExecutor eventExecutor);

    public List<String> getTypes() {
        return types;
    }

    public static JSONObject createResponse(int state,String message,JSONObject data){
        JSONObject object = new JSONObject();
        object.put(STATE,state);
        object.put(MESSAGE,message);
        object.put(DATA,data);
        return object;
    }
}
