package be.kuleuven.swop.objectron.model;

import be.kuleuven.swop.objectron.model.item.Item;

import java.util.List;

/**
 * @author : Nik Torfs
 *         Date: 21/02/13
 *         Time: 23:54
 */
public interface Inventory {
    /**
     * @return
     */
    List<Item> getItems();

    /**
     * Takes (removes) an item from the inventory and returns it.
     *
     * @param identifier: the item identifier
     * @return the item to retrieve
     */
    Item retrieveItem(int identifier);

    /**
     * Add an item to the inventory
     *
     * @param itemToAdd
     */
    void addItem(Item itemToAdd) throws InventoryFullException;

    /**
     * Remove an item from the inventory
     *
     * @param item
     */
    void removeItem(Item item);
}
