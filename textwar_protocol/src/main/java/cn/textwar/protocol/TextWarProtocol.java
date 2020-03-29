package cn.textwar.protocol;

import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
import java.util.Base64;

/**
 * TextWar采用类http的方式定义协议
 * TextWar协议总体结构
 * [TextWar]\r\n
 * Time-Stamp:  xxx\r\n
 * Data-Length: xxx\r\n\r\n
 * {data json base64}
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

    public byte[] encode(){
        byte[] json =  Base64.getEncoder().encode(jsonObject.toJSONString().getBytes());
        String builder = new StringBuilder()
                .append(HEAD).append(CRLF)
                .append(TIME_STAMP).append(MIDDLE).append(System.currentTimeMillis()).append(CRLF)
                .append(DATA_LENGTH).append(MIDDLE).append(json.length).append(CRLF).append(CRLF)
                .append(new String(json))
                .toString();
        return builder.getBytes();
    }

    public String getJson(){
        return jsonObject.toJSONString();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        byte[] bytes = encode();
        int n = 0;
        for(byte b : bytes){
            String code = Integer.toString(b,16).toUpperCase();
            builder.append(code.length() == 1?"0"+code:code).append(" ");
            n++;
            if(n%10 == 0){
                builder.append("\n");
            }
        }
        return builder.toString();
    }


    public TextWarProtocol addJSONCode(String key, Object value){
        jsonObject.put(key, value);
        return this;
    }

    public TextWarProtocol addAll(String json){
        JSONObject jsonObject = JSONObject.parseObject(json);
        this.jsonObject.putAll(jsonObject);
        return this;
    }

}