package cn.textwar.client.events;


public class BroadcastEvent extends MessageEvent {


    public BroadcastEvent(String message) {
        super(BroadcastEvent.class.getSimpleName(),message,null);
    }

}
