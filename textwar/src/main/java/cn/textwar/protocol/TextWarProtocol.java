package cn.textwar.protocol;

import com.alibaba.fastjson.JSONObject;

import java.util.Base64;

/**
 * TextWar协议总体结构
 * [TextWar]\r\n
 * Time-Stamp:  xxx\r\n
 * Data-Length: xxx\r\n\r\n
 * {data json}
 *
 * @author TextWar-Dev
 */
public class TextWarProtocol {

    public static final String HEAD = "[TextWar]";

    public static final String CRLF = "\r\n";

    public static final String TIME_STAMP = "Time-Stamp";

    public static final String DATA_LENGTH = "Data-Length";

    public static final String MIDDLE = ": ";//key: value\r\n 之间必须空格

    private JSONObject jsonObject;

    public TextWarProtocol(){
        this.jsonObject = new JSONObject();
    }

    public byte[] toProtocol(){
        String json = jsonObject.toJSONString();
        return Base64.getEncoder().encode(new StringBuilder()
                .append(HEAD).append(CRLF)
                .append(TIME_STAMP).append(MIDDLE).append(System.currentTimeMillis()).append(CRLF)
                .append(DATA_LENGTH).append(MIDDLE).append(jsonObject.toJSONString().getBytes().length).append(CRLF).append(CRLF)
                .append(json)
                .toString().getBytes());
    }

    public void addJSONCode(String key,Object value){
        jsonObject.put(key, value);
    }

}