package cn.qqtextwar.entity.player;

import java.util.ArrayList;
import java.util.List;

/**
 * 背包
 */
public class Inventory {

    private List<Item> items;

    public Inventory() {
        this.items = new ArrayList<>();
    }

    public List<Item> getItems() {
        return items;
    }

}
