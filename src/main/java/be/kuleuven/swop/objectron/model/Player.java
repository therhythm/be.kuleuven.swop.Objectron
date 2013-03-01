package be.kuleuven.swop.objectron.model;

import be.kuleuven.swop.objectron.model.exception.InventoryFullException;
import be.kuleuven.swop.objectron.model.exception.NotEnoughActionsException;
import be.kuleuven.swop.objectron.model.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.model.item.Item;
import be.kuleuven.swop.objectron.viewmodel.PlayerViewModel;

import java.util.List;

/**
 * @author : Nik Torfs
 *         Date: 22/02/13
 *         Time: 00:06
 */
public interface Player {

    /**
     * Retrieve the square on which the player is standing
     *
     * @return the currentSquare square
     */
    Square getCurrentSquare();

    /**
     * Add an item to the player's inventory
     *
     * @param itemToAdd: the item to add to the inventory
     */
    void addToInventory(Item itemToAdd) throws InventoryFullException, NotEnoughActionsException;

    /**
     * Move the player to the newPosition
     *
     * @param newPosition
     */
    void move(Square newPosition) throws NotEnoughActionsException;

    String getName();

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

    void useCurrentItem() throws SquareOccupiedException, NotEnoughActionsException;

    void endTurn();

    boolean hasMoved();

    void blind();

    PlayerViewModel getPlayerViewModel();
}
