package cn.qqtextwar.utils

import cn.qqtextwar.Server

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
    static String readClassStream(File file,String res){
        file.write(this.classLoader.getResourceAsStream(res).text)
    }

}
