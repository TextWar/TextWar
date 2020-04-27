package cn.qqtextwar;

import cn.qqtextwar.entity.Entity;

//TODO
public interface Hittable {

    String hit(Entity entity, GameMap map);
    
}
