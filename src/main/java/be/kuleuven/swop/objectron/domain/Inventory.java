package be.kuleuven.swop.objectron.domain;

import be.kuleuven.swop.objectron.domain.exception.TooManyItemsOfSameTypeException;
import be.kuleuven.swop.objectron.domain.exception.InventoryFullException;
import be.kuleuven.swop.objectron.domain.item.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author : Nik Torfs
 *         Date: 21/02/13
 *         Time: 23:51
 */
public class Inventory {
    public static final int INVENTORY_LIMIT = 6;

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
        return this.items.size() >= INVENTORY_LIMIT;
    }

    /**
     * Add an item to the inventory
     *
     * @param itemToAdd
     */
    public void addItem(Item itemToAdd) throws InventoryFullException, TooManyItemsOfSameTypeException {
        if (isLimitReached()) {
            throw new InventoryFullException("Inventory full");
        }

        int itemCount = 0;
        for(Item item : items){
            if(itemToAdd.getClass().isInstance(item)){
                itemCount++;
            }
        }

        if(itemCount + 1 > itemToAdd.getMaxInBag()) {
            throw new TooManyItemsOfSameTypeException();
        }
        itemToAdd.pickedUp();
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
