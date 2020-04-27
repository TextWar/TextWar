package cn.qqtextwar.entity.player;

import java.util.ArrayList;
import java.util.List;

/**
 * 背包
 */
//TODO 数据库的初始化
public class ItemStorageRoom {

    public static final int INVENTORY_SIZE = 10;

    private int bagSize = 100;

    private List<Item> items;

    private List<Item> bag;

    private Item handNow;

    private int handIndex;

    private Player owner;

    public ItemStorageRoom(Player owner) {
        this.items = new ArrayList<>(INVENTORY_SIZE);
        this.bag = new ArrayList<>();
        this.owner = owner;
    }

    public boolean addNewItem(Item item){
        if(!inventoryFull()){
            addInventory(item);
        }else{
            if(bagFull())return false;
            addBag(item);
        }
        return true;
    }

    public void moveInventoryToBag(Item item){
        if(!bagFull()){
            removeInventory(item);
            bag.set(bag.indexOf(null),item);
        }
    }

    public boolean inventoryFull(){
        return inventoryUsed() >= INVENTORY_SIZE;
    }

    public boolean bagFull(){
        return bagUsed() >= bagSize;
    }


    public void moveBagToInventory(Item item){
        removeBag(item);
        items.set(items.indexOf(null),item);
    }

    public Item discard(Item item){
        removeInventory(item);
        return item;
    }

    public int inventoryUsed(){
        return sizeOf(items);
    }

    public int bagUsed(){
        return sizeOf(bag);
    }

    public int bagSize(){
        return bagSize;
    }

    public void setBagSize(int bagSize) {
        this.bagSize = bagSize;
    }


    public Item hand(int index){
        handNow = items.get(index);
        handIndex = index;
        return handNow;
    }

    public int indexOfInventory(Item item){
        return items.indexOf(item);
    }

    public int indexOfBag(Item item){
        return bag.indexOf(item);
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


    private void addBag(Item item){
        bag.set(items.indexOf(null),item);
    }

    private void removeBag(Item item){
        bag.set(bag.indexOf(item),null);
    }

    private void addInventory(Item item){
        items.set(items.indexOf(null),item);
    }

    private void removeInventory(Item item){
        items.set(items.indexOf(item),null);
    }

    private int sizeOf(List<Item> items){
        int size = 0;
        for(int i = 0;i<items.size();i++){
            if(items.get(i) != null)size++;
        }
        return size;
    }

    public Player getOwner() {
        return owner;
    }
}
