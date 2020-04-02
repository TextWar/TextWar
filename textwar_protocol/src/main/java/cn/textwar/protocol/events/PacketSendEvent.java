package cn.textwar.protocol.events;

import cn.textwar.plugins.Event;
import cn.textwar.protocol.TextWarProtocol;

public class PacketSendEvent extends Event {

    private TextWarProtocol protocol;

    public PacketSendEvent(TextWarProtocol protocol) {
        super(PacketSendEvent.class.getSimpleName());
        this.protocol = protocol;
    }

    public TextWarProtocol getProtocol() {
        return protocol;
    }
}
