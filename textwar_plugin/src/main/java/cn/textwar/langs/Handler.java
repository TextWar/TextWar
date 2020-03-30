package cn.textwar.langs;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

//注意线程安全
//在使用Handler时
public abstract class Handler {

    private List<String> types;

    public abstract JSONObject execute(String type, JSONObject jsonObject);

    public List<String> getTypes() {
        return types;
    }
}
