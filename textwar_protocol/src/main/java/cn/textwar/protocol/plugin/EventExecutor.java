package cn.textwar.protocol.plugin;

import cn.qqtextwar.ex.EventException;
import cn.qqtextwar.ex.PluginException;

import java.lang.reflect.Method;
import java.util.*;

public class EventExecutor {


    private Map<Listener, List<MethodInvokeMapper>> listenerMapper = new HashMap<>();

    public void registerEvents(final Listener listener, Plugin plugin){
        if(plugin!=null){
            if(!plugin.isEnabled()){
                throw new PluginException("the plugin is not enabled");
            }
        }else{
            if(listener.getClass().getAnnotation(NativeListener.class)==null){
                throw new PluginException("the listener type must be native type");
            }
        }
        Class<?> type = listener.getClass();
        Method[] methods = type.getDeclaredMethods();
        for(Method method:methods) {
            addMethod(method,listener);
        }
    }
    private void addMethod(Method method,Listener listenerInstance){
        EventManager manager = method.getDeclaredAnnotation(EventManager.class);
        if (manager != null) {
            MethodInvokeMapper mapper = new MethodInvokeMapper(manager.priority(), method);
            List<MethodInvokeMapper> methodMappers = listenerMapper.get(listenerInstance);
            if(methodMappers!=null){
                methodMappers.add(mapper);
                listenerMapper.put(listenerInstance,methodMappers);
            }else{
                methodMappers = new ArrayList<>();
                methodMappers.add(mapper);
                listenerMapper.put(listenerInstance,methodMappers);
            }
        }
    }

    public void registerEvent(final Method method,Plugin plugin){
        if(!plugin.isEnabled()){
            throw new PluginException("the plugin is not enabled");
        }
        try {
            Class<?> type = method.getDeclaringClass();
            addMethod(method, (Listener) (type.newInstance()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void callEvent(final Event event){
        try{
            Set<Map.Entry<Listener,List<MethodInvokeMapper>>> set = listenerMapper.entrySet();
            for(Map.Entry<Listener,List<MethodInvokeMapper>> entry:set){
                Listener listener = entry.getKey();
                List<MethodInvokeMapper> methods = entry.getValue();
                List<MethodInvokeMapper> invoker = new ArrayList<>();
                for(MethodInvokeMapper mapper:methods){
                    Class[] classes = mapper.getMethod().getParameterTypes();
                    if(classes.length == 1){
                        //对父类支持
                        if(isThisEvent(classes[0],event)){
                            invoker.add(mapper);
                        }
                    }else{
                        throw new EventException("the event method must have one parameter");
                    }
                }
                invoker.sort(Comparator.comparing(MethodInvokeMapper::getPriority));
                for(MethodInvokeMapper method:invoker){
                    try {
                        method.getMethod().invoke(listener, event);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void registerNativeEvents(Listener listener){
        registerEvents(listener,null);
    }

    private static boolean isThisEvent(Class clz,Event event){
        return clz.isInstance(event);
    }


}
