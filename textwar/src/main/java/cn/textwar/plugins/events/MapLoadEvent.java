package cn.textwar.plugins.events;

import cn.qqtextwar.GameMap;
import cn.textwar.plugins.Event;

public class MapLoadEvent extends Event {

    private GameMap map;

    public MapLoadEvent(GameMap map) {
        super(MapLoadEvent.class.getSimpleName());
        this.map = map;
    }

    public GameMap getMap() {
        return map;
    }
}
