package cn.qqtextwar.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class JavaUtils {

    public static String[] getParentArray(String[] keys){
        return Arrays.copyOf(keys,keys.length-1);
    }

    public static BufferedReader getReader(InputStream in) throws IOException {
        return new BufferedReader(new InputStreamReader(in,System.getProperty("file.coding")));
    }
    /**
     * 得到格式化json数据  退格用\t 换行用\r
     */
    public static String format(String jsonStr) {
        int level = 0;
        StringBuffer jsonForMatStr = new StringBuffer();
        for(int i=0;i<jsonStr.length();i++){
            char c = jsonStr.charAt(i);
            if(level>0&&'\n'==jsonForMatStr.charAt(jsonForMatStr.length()-1)){
                jsonForMatStr.append(getLevelStr(level));
            }
            switch (c) {
                case '{':
                case '[':
                    jsonForMatStr.append(c+"\n");
                    level++;
                    break;
                case ',':
                    char d = jsonStr.charAt(i-1);
                    if(d == '"' || d == ']'){
                        jsonForMatStr.append(c+"\n");
                    } else {
                        jsonForMatStr.append(c);
                    }
                    break;
                case '}':
                case ']':
                    jsonForMatStr.append("\n");
                    level--;
                    jsonForMatStr.append(getLevelStr(level));
                    jsonForMatStr.append(c);
                    break;
                default:
                    jsonForMatStr.append(c);
                    break;
            }
        }
        return jsonForMatStr.toString();
    }

    private static String getLevelStr(int level){
        StringBuilder levelStr = new StringBuilder();
        for(int levelI = 0;levelI<level ; levelI++){
            levelStr.append("\t");
        }
        return levelStr.toString();
    }

    public static void horizontalFlip(Object[][] arr){
        int middle = arr.length % 2 == 0?arr.length/2:arr.length/2 + 1 ;
        for(int i = 0;i<middle;i++){
            Object[] a = arr[arr.length -1 - i];
            arr[arr.length - 1 - i] = arr[i];
            arr[i] = a;
        }
    }

    public static long timing(Timing t){
        long t1 = System.currentTimeMillis();
        t.call();
        long t2 = System.currentTimeMillis();
        return t2 - t1;
    }

    public interface Timing{

        void call();
    }

    //arr[i][j] arr[i][arr.length -1 - j]
    //arr[arr.length -1 -i ][j] arr[arr.length -1 -i ][arr.length -1 - j]
    public static void verticalFlip(Object[][] arr){
        if(arr.length != 0){
            //横中点
            int middleX = arr[0].length % 2 == 0?arr[0].length /2:arr[0].length/2+1;
            //纵中点
            int middleY = arr.length % 2 == 0?arr.length / 2:arr.length /2 +1;
            for(int i = 0;i<middleY;i++){
                for(int j = 0;j<middleX;j++){
                    Object a1 = arr[j][arr.length - i -1];
                    arr[j][arr[j].length - i - 1] = arr[j][i];
                    arr[j][i] = a1;
                    Object a2 = arr[arr.length - j -1 ][arr[j].length - i -1];
                    arr[arr.length - j -1][arr[j].length - i - 1] = arr[arr.length - j -1][i];
                    arr[arr.length - j -1][i] = a2;
                }
            }
        }
    }
}
