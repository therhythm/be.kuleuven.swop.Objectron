package be.kuleuven.swop.objectron.model;

import be.kuleuven.swop.objectron.model.exception.InventoryFullException;
import be.kuleuven.swop.objectron.model.item.Item;

import java.util.*;

/**
 * @author : Nik Torfs
 *         Date: 21/02/13
 *         Time: 23:51
 */
public class Inventory {
    private static final int LIMIT = 6;

    private List<Item> items = new ArrayList<Item>();

    /**
    * @return
    */
    public List<Item> getAllItems() {
        return Collections.unmodifiableList(items);
    }

    /**
    * Takes (removes) an item from the inventory and returns it.
    *
    * @param identifier: the item identifier
    * @return the item to retrieve
    */
    public Item retrieveItem(int identifier) {
        return items.get(identifier);
    }

    private boolean isLimitReached() {
        return this.items.size() >= LIMIT;
    }

    /**
    * Add an item to the inventory
    *
    * @param itemToAdd
    */
    public void addItem(Item itemToAdd) throws InventoryFullException {
        if(isLimitReached()) {
            throw new InventoryFullException("Inventory full");
        }
        items.add(itemToAdd);
    }

    /**
    * Remove an item from the inventory
    *
    * @param item
    */
    public void removeItem(Item item) {
        items.remove(item);
    }
}
