package cn.textwar.protocol.events;

import cn.textwar.events.Event;
import cn.textwar.protocol.TextWarProtocol;

public class PacketReceiveEvent extends Event {

    private TextWarProtocol protocol;

    public PacketReceiveEvent(TextWarProtocol protocol) {
        super(PacketReceiveEvent.class.getSimpleName());
        this.protocol = protocol;
    }

    public TextWarProtocol getProtocol() {
        return protocol;
    }
}
