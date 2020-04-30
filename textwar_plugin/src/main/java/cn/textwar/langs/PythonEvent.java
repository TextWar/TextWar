package cn.textwar.langs;

import cn.textwar.plugins.Event;

public class PythonEvent extends Event {


    private Object eventObject;

    public PythonEvent(Object eventObject) {
        super(PythonEvent.class.getSimpleName());
    }

    public Object invoke(String method,Object... objects) {
        try {
            Class[] classes = new Class[objects.length];
            for(int i = 0;i<objects.length;i++){
                classes[i] = objects[i].getClass();
            }
            return eventObject.getClass().getMethod(method,classes).invoke(eventObject, objects);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Object getPythonEvent(){
        return eventObject;
    }
}
