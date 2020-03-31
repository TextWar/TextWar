package cn.textwar.langs;

import cn.textwar.protocol.plugin.Event;
import cn.textwar.protocol.plugin.EventManager;
import cn.textwar.protocol.plugin.Listener;
import cn.textwar.protocol.plugin.NativeListener;
import cn.textwar.protocol.TextWarProtocol;

@NativeListener
public class PluginListener implements Listener {

    private PluginServer server;

    public PluginListener(PluginServer server) {
        this.server = server;
    }

    @EventManager
    public void onEventHandler(Event e){
        server.getStreamList().forEach((x)->{
            try {
                x.write(new TextWarProtocol().addJSONCode("event_name", e.getEventName()).encode());
            }catch (Exception e1){
                e1.printStackTrace();
            }
        });
    }
}
