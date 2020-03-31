package cn.textwar.protocol.plugin;

public class Event {


    private String name;

    public Event(String name) {
        this.name = name;
    }

    public final String getEventName() {
        return name;
    }


}
