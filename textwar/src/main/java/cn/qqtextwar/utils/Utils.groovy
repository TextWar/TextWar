package cn.qqtextwar.utils

import cn.qqtextwar.Server

import java.text.DecimalFormat

class Utils {

    /** 随机数器，不要直接使用，请使用线程安全的random方法*/
    private static Random random

    static {
        random = new Random()
    }

    /** 线程安全的随机表，在创建怪物时使用 */
    static synchronized int random(Random random = this.random,int round){
        random.nextInt(round)
    }

    static synchronized Random setRandomSeed(long seed){
        Random random = new Random()
        random.seed = seed
        return random
    }

    static int random(Random rand,int start,int end){
        rand.nextInt(end-start)+start
    }
    static String getClassFileName(String file){
        return Server.class.getClassLoader().getResource("/").getPath()+file;
    }

    static String[] getArgs(String[] things){
        String[] args = new String[things.length-1]
        System.arraycopy(things,1,args,0,args.length)
        return args
    }

    static PrintWriter getWriter(OutputStream stream){
        PrintWriter writer = new PrintWriter(stream,true)
        return writer
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

    static cn.qqtextwar.math.Vector maxY(cn.qqtextwar.math.Vector vector1,cn.qqtextwar.math.Vector vector2){
        return vector1.y>vector2.y?vector1:vector2
    }
    static cn.qqtextwar.math.Vector minY(cn.qqtextwar.math.Vector vector1,cn.qqtextwar.math.Vector vector2){
        return vector1.y<vector2.y?vector1:vector2
    }

}
