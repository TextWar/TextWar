package cn.qqtextwar.utils

import cn.qqtextwar.Server

import java.text.DecimalFormat

class Utils {

    static String getClassFileName(String file){
        return Server.class.getClassLoader().getResource("/").getPath()+file;
    }

    static PrintWriter getWriter(OutputStream stream){
        PrintWriter writer = new PrintWriter(stream,true)
        return writer;
    }

    static String simpleClassName(Class<?> clz){
        String className = clz.getName()
        String[] fields = className.split("\\.")
        StringBuilder builder = new StringBuilder()
        for(int i = 0;i<fields.length-1;i++){
            String after = fields[i].substring(0,1)
            builder.append(after)
            builder.append(".")
        }
        builder.append(fields[fields.length-1])
        return builder.toString()
    }
    static String byInputStream(InputStream input){
        return input.text
    }
    static void readClassStream(File file,String res){
        file.write(this.classLoader.getResourceAsStream(res).text)
    }

    static double format(double d){
        return Double.parseDouble(new DecimalFormat("0.00").format(d))
    }

    static cn.qqtextwar.math.Vector maxX(cn.qqtextwar.math.Vector vector1,cn.qqtextwar.math.Vector vector2){
        return vector1.x>vector2.x?vector1:vector2
    }
    static cn.qqtextwar.math.Vector minX(cn.qqtextwar.math.Vector vector1,cn.qqtextwar.math.Vector vector2){
        return vector1.x<vector2.x?vector1:vector2
    }


}
