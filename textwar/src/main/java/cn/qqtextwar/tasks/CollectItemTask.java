package cn.qqtextwar.tasks;

import cn.qqtextwar.entity.player.Item;

import java.util.Map;

public class CollectItemTask extends Task{

    private Map<Class<? extends Item>,Integer> items;

    public CollectItemTask(Map<Class<? extends Item>, Integer> items,Award award) {
        super(award);
        this.items = items;
    }

    public Map<Class<? extends Item>, Integer> getItems() {
        return items;
    }
}
