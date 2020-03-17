package cn.qqtextwar;

public class Utils {

    public static String simpleClassName(Class<?> clz){
        String className = clz.getName();
        String[] fields = className.split("\\.");
        StringBuilder builder = new StringBuilder();
        for(int i = 0;i<fields.length-1;i++){
            String after = fields[i].substring(0,1);
            builder.append(after);
            builder.append(".");
        }
        builder.append(fields[fields.length-1]);
        return builder.toString();
    }
}
