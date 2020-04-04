package cn.textwar.console;

import cn.textwar.plugins.EventManager;
import cn.textwar.plugins.Listener;
import cn.textwar.plugins.NativeListener;
import cn.textwar.plugins.events.MapLoadEvent;
import cn.textwar.plugins.events.PlayerExitEvent;
import cn.textwar.plugins.events.PlayerJoinEvent;
import cn.textwar.protocol.events.PacketReceiveEvent;
import cn.textwar.protocol.events.PacketSendEvent;

@NativeListener
public class ServerListener implements Listener {

    private ServerConsole console;

    public ServerListener(ServerConsole console){
        this.console = console;
    }

    @EventManager(type = 1)
    public void onMap(MapLoadEvent e){
        console.getLogger().info("load map == "+e.getMap().toString());
    }

    @EventManager(type = 1)
    public void onSend(PacketSendEvent e){
        console.getLogger().info("Send Packet - "+e.getProtocol().getJson());
        console.getLogger().info("Send Packet Data - "+e.getProtocol());
    }

    @EventManager(type = 1)
    public void onReceive(PacketReceiveEvent e){
        console.getLogger().info("Receive Packet - "+e.getProtocol().getJson());
        console.getLogger().info("Receive Packet Data - "+e.getProtocol());
    }

    @EventManager
    public void onPlayerJoin(PlayerJoinEvent e){
        console.getLogger().info(e.getPlayer().getName()+" : "+e.getPlayer().getIp()+"/ join in the game ");
    }

    @EventManager
    public void onPlayerExit(PlayerExitEvent e){
        console.getLogger().info(e.getPlayer().getName()+" : "+e.getPlayer().getIp()+"/ exit the game");
    }
}
