package cn.qqtextwar.entity.tasks;

import cn.qqtextwar.entity.Item;

import java.util.Map;

public class CollectItemTask extends Task{

    private Map<Item.ItemType,Integer> items;

    public CollectItemTask(Map<Item.ItemType, Integer> items,Award award) {
        super(award);
        this.items = items;
    }

    public Map<Item.ItemType, Integer> getItems() {
        return items;
    }
}
