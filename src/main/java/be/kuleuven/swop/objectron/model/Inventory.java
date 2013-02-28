package be.kuleuven.swop.objectron.model;

import java.util.List;

/**
 * @author : Nik Torfs
 *         Date: 21/02/13
 *         Time: 23:54
 */
public interface Inventory {
    /**
     *
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
     * Check if the inventory limit is reached.
     *
     * @return whether the limit is reached or not
     */
    boolean isLimitReached();

    /**
     * Add an item to the inventory
     *
     * @param itemToAdd
     */
    void addItem(Item itemToAdd);
}
