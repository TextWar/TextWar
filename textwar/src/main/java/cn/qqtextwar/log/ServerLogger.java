/*
Jsmod2 is a java-based scpsl cn.jsmod2.server initiated by jsmod2.cn.
It needs to rely on smod2 and proxy. jsmod2 is an open source
free plugin that is released under the GNU license. Please read
the GNU open source license before using the software. To understand
the appropriateness, if infringement, will be handled in accordance
with the law, @Copyright Jsmod2 China,more can see <a href="http://jsmod2.cn">that<a>
 */
package cn.qqtextwar.log;


import cn.qqtextwar.utils.Utils;
import org.apache.log4j.Logger;

import static org.fusesource.jansi.Ansi.Color.*;

public class ServerLogger implements ILogger{

    private static ServerLogger log;

    private boolean debug;

    private Logger logger = Logger.getLogger(ServerLogger.class);

    static {
        log = new ServerLogger();
    }
    @Override
    public void log(LogType logType, String message) {
        Integer level = logType.getLevel();

        switch (level){
            //default
            case 0:
                System.out.println(message);
                break;
            //debug
            case 1:
                debug(message);
                break;
                //info
            case 2:
                info(message);
                break;
                //warn
            case 3:
                warn(message);
                break;
                //error
            case 4:
                error(message);
                break;
            case 5:
                crit(message);
                break;
        }
    }

    @Override
    public void error(String message) {
        error(message,"");
    }
    @Override
    public void debug(String message) {
        debug(message,"");
    }
    @Override
    public void info(String message) {
        info(message,"");
    }
    @Override
    public void warn(String message) {
        warn(message,"");
    }

    @Override
    public void multiDebug(Class<?> clz, String message, String prefix, String suffix) {
        debug(message,prefix+getMultiMessage(clz)+"\t",suffix.equals("")?"\n":suffix);
    }

    @Override
    public void multiError(Class<?> clz, String message, String prefix, String suffix) {
        error(message,prefix+getMultiMessage(clz)+"\t",suffix.equals("")?"\n":suffix);
    }

    @Override
    public void multiInfo(Class<?> clz, String message, String prefix, String suffix) {
        info(message,prefix+getMultiMessage(clz)+"\t",suffix.equals("")?"\n":suffix);
    }

    @Override
    public void multiWarn(Class<?> clz, String message, String prefix, String suffix) {
        warn(message,prefix+getMultiMessage(clz)+"\t",suffix.equals("")?"\n":suffix);
    }
    @Override
    public void debug(String message, String prefix) {
        debug(message,prefix,"\n");
    }

    @Override
    public void error(String message, String prefix) {
        error(message,prefix,"\n");
    }

    @Override
    public void info(String message, String prefix) {
        info(message, prefix,"\n");
    }

    @Override
    public void warn(String message, String prefix) {
        warn(message, prefix,"\n");
    }

    @Override
    public void debug(String message, String prefix, String suffix) {
        String msg = LogFormat.format(message,"DEBUG",GREEN,prefix)+suffix;
        logger.debug(msg);
    }

    @Override
    public void error(String message, String prefix, String suffix) {
        String msg = LogFormat.format(LogFormat.textFormat(message,RED),"ERROR",RED,prefix,true)+suffix;
        logger.error(msg);
    }

    @Override
    public void info(String message, String prefix, String suffix) {
        if(!debug) {
            String msg = LogFormat.format(message, "INFO", YELLOW, prefix) + suffix;
            logger.info(msg);
        }else{
            debug(message,prefix,suffix);
        }
    }

    @Override
    public void warn(String message, String prefix, String suffix) {
        String msg = LogFormat.format(message,"WARN",RED,prefix,true)+suffix;
        logger.warn(msg);
    }

    @Override
    public void crit(String s) {
        crit(s,"");
    }

    @Override
    public void crit(String s, String s1) {
        crit(s,s1,"\n");
    }

    @Override
    public void crit(String s, String s1, String s2) {
        String msg = LogFormat.format(s,"CRIT",YELLOW,s1,true)+s2;
        logger.warn(msg);
    }

    @Override
    public void multiCrit(Class<?> clz, String message, String prefix, String suffix) {
        debug(message,prefix+getMultiMessage(clz)+"\t",suffix.equals("")?"\n":suffix);
    }

    public static ServerLogger getLogger() {
        return log;
    }


    private String getMultiMessage(Class clz){
        return LogFormat.textFormat("[",BLUE)+LogFormat.textFormat(getLine()+"",BLUE)+"]"+LogFormat.textFormat("["+ Utils.simpleClassName(clz) +"]", CYAN);
    }

    public int getLine(){
        Throwable t = new Throwable();
        return t.getStackTrace()[t.getStackTrace().length-1].getLineNumber();
    }

    public String getSimpleMessage(String str){
        return str.replaceAll("\\033\\[[0-9]+m","");
    }

    public void setAsDebug(boolean debug){
        this.debug = debug;
    }


}
