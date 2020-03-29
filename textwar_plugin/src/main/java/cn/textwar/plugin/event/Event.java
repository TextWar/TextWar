package cn.textwar.plugin.event;

public class Event {


    private String name;

    public Event(String name) {
        this.name = name;
    }

    public final String getEventName() {
        return name;
    }


}
