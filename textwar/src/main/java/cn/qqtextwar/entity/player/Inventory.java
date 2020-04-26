package cn.qqtextwar.entity.player;

import java.util.ArrayList;
import java.util.List;

/**
 * 背包
 */
//TODO 数据库的初始化
public class Inventory {

    private List<Item> items;

    private List<Item> bag;

    private Item handNow;

    private int handIndex;

    public Inventory() {
        this.items = new ArrayList<>();
        this.bag = new ArrayList<>();
    }

    public void addNewItem(Item item){
        if(items.size() <= 10){
            addInventory(item);
        }else{
            addBag(item);
        }
    }

    public Item discard(Item item){
        items.remove(item);
        return item;
    }

    public void addInventory(Item item){
        items.add(item);
    }

    public void hand(int index){
        handNow = items.get(index);
        handIndex = index;
    }

    public Item getHandNow() {
        return handNow;
    }

    public int getHandIndex() {
        return handIndex;
    }

    public List<Item> getInventory() {
        return items;
    }

    public void removeInventory(Item item){
        items.remove(item);
    }

    public void moveInventoryToBag(Item item){
        items.remove(item);
        bag.add(item);
    }

    public void moveBagToInventory(Item item){
        bag.remove(item);
        items.add(item);
    }

    public void addBag(Item item){
        bag.add(item);
    }

    public void removeBag(Item item){
        items.remove(item);
    }

    public List<Item> getBag() {
        return bag;
    }
}
