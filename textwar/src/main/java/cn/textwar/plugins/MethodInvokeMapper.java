package cn.textwar.plugins;

import java.lang.reflect.Method;


/**
 * 存储优先级和方法对象的映射
 *
 * @author magiclu550
 */
public class MethodInvokeMapper {

    private EventPriority priority;

    private Method method;

    public MethodInvokeMapper(EventPriority priority, Method method){
        this.priority = priority;
        this.method = method;
    }

    public EventPriority getPriority() {
        return priority;
    }

    public Method getMethod() {
        return method;
    }
}
