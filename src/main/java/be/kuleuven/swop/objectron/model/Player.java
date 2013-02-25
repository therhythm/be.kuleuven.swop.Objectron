package be.kuleuven.swop.objectron.model;

/**
 * @author : Nik Torfs
 *         Date: 22/02/13
 *         Time: 00:06
 */
public interface Player {

    /**
     * Checks if the players inventory is full
     *
     * @return whether the players inventory is full or not
     */
    boolean isInventoryFull();

    /**
     * Retrieve the square on which the player is standing
     *
     * @return the current square
     */
    Square getCurrentSquare();

    /**
     * Add an item to the player's inventory
     *
     * @param itemToAdd: the item to add to the inventory
     */
    void addToInventory(Item itemToAdd);

    /**
     * Retrieve number of available actions
     *
     * @return the number of remaining actions
     */
    int getAvailableActions();

    /**
     * Retrieve currently selected item
     *
     * @return the currently selected item for this player
     */
    Item getCurrentlySelectedItem();
}
