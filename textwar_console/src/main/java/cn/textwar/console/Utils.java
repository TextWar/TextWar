package cn.textwar.console;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class Utils {

    public static String getProcessId(){
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        String name = runtime.getName();
        return name.substring(0, name.indexOf("@"));
    }
}
