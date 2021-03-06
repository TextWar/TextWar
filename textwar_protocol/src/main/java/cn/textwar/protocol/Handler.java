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

    public static final String ID = "id";

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

    public static int ALIVE = 101;

    private int id;

    public Handler(List<String> types,int id) {
        this.types = types;
        this.id = id;
    }

    private List<String> types;

    public JSONObject executeOption(Application application,ConnectServer.ClientThread thread, Server server, String type, JSONObject jsonObject, EventExecutor eventExecutor){
        try{
            JSONObject object = execute(application,thread,server,type,jsonObject,eventExecutor);
            if(object == null)throw new UnsupportedOperationException(DEFAULT_ERROR);
            return object;
        }catch (Exception e){
            return createResponse(jsonObject,ERROR,e.getMessage(),new JSONObject());
        }
    }

    public int getId() {
        return id;
    }

    public abstract JSONObject execute(Application application, ConnectServer.ClientThread thread, Server server, String type, JSONObject jsonObject, EventExecutor eventExecutor);

    public List<String> getTypes() {
        return types;
    }

    //req request
    public JSONObject createResponse(JSONObject req,int state,String message,JSONObject data){
        return createResponse(req,getId(),state,message,data);
    }
    public static JSONObject createResponse(JSONObject action,int id,int state,String message,JSONObject data){
        JSONObject object = new JSONObject();
        object.put(ID,id);
        object.put(STATE,state);
        object.put(MESSAGE,message);
        object.put(DATA,data);
        if(action != null){
            object.put("action",(action.get("type") == null?"":(action.get("type")+"."))+action.get("action").toString());
        }
        return object;
    }
}
