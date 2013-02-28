package be.kuleuven.swop.objectron.model;

import be.kuleuven.swop.objectron.model.listener.PlayerEventListener;

import java.util.List;

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
     * @return the currentSquare square
     */
    Square getCurrentSquare();

    /**
     * Set the current square
     *
     * @param currentSquare: the current square
     */
    void setCurrentSquare(Square currentSquare);

    /**
     * Add an item to the player's inventory
     *
     * @param itemToAdd: the item to add to the inventory
     */
    void addToInventory(Item itemToAdd);

    /**
     * Move the player to the newPosition
     *
     * @param newPosition
     */
    void move(Square newPosition);

    String getName();

    void addPlayerEventListener(PlayerEventListener listener);

    void removePlayerEventListener(PlayerEventListener listener);

     /* Retrieve number of available actions
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

    List<Item> getInventoryItems();

    Item getInventoryItem(int identifier);

    void setCurrentlySelectedItem(Item selectedItem);

    void useCurrentItem();
}
