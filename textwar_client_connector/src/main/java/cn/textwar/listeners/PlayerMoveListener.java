package cn.textwar.listeners;

import cn.textwar.client.ClientServer;
import cn.textwar.plugins.EventManager;
import cn.textwar.plugins.Listener;
import cn.textwar.plugins.NativeListener;
import cn.textwar.plugins.events.PlayerMoveEvent;
import cn.textwar.protocol.TextWarProtocol;
import com.alibaba.fastjson.JSONObject;

@NativeListener
public class PlayerMoveListener implements Listener {

    private ClientServer server;

    public PlayerMoveListener(ClientServer server){
        this.server = server;
    }

    @EventManager(type = AFTER)
    public void onPlayerMove(PlayerMoveEvent e){
        TextWarProtocol protocol = new TextWarProtocol();
        JSONObject object = new JSONObject();
        object.put("id",e.getPlayer().getId());
        object.put("x",e.getPlayer().getX());
        object.put("y",e.getPlayer().getY());
        protocol.addAll(object.toJSONString());
        server.callMessage(protocol);
    }

}
