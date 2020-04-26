package cn.qqtextwar.entity.player;

import cn.qqtextwar.ex.ItemTypeException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * 物品
 */
public abstract class Item {

    private static Map<UUID,Item> itemPoor;

    private static Map<Integer,Class<? extends Item>> itemTypes;

    static {
        itemPoor = new HashMap<>();
        itemTypes = new HashMap<>();
        Item.registerPoor(0,SWord.class);
    }

    private UUID uuid;

    private int id;

    public Item(UUID uuid, int id) {
        this.uuid = uuid;
        this.id = id;
    }

    public static void registerPoor(int id,Class<? extends Item> itemType){
        itemTypes.put(id,itemType);
    }

    public static Item createItem(int id){
        try {
            Item item = itemTypes.get(id).newInstance();
            itemPoor.put(item.uuid,item);
            return item;
        }catch (Exception e){
            e.printStackTrace();
            throw new ItemTypeException("No such item type id : " +id);
        }
    }

    public static Item getItem(UUID uuid){
        Item item = itemPoor.get(uuid);
        if(item == null)throw new ItemTypeException("No such item uuid: "+uuid);
        return item;
    }

    public int getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id &&
                Objects.equals(uuid, item.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, id);
    }
}
