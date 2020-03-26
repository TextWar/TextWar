package cn.qqtextwar.entity.player;

import cn.qqtextwar.GameMap;
import cn.qqtextwar.entity.Entity;

public class Movement {

    private Entity entity;

    private GameMap map;

    public Movement(Entity entity, GameMap map) {
        this.entity = entity;
        this.map = map;
    }

    public Movement update(){
        map.updateEntity(entity);
        return this;
    }
}
