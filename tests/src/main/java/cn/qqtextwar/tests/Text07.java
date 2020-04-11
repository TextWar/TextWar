package cn.qqtextwar.tests;

import java.util.HashMap;

public class Text07 {

    public static void main(String[] args) {
        System.out.println(new HashMap<String,HashMap>(){
            {
                put("hello",new HashMap<String,String>(){
                    {
                             put("name","11");
                    }
                });
            }
        });
    }
}
