package be.kuleuven.swop.objectron.controller;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.swop.objectron.model.*;

import java.util.List;

/**
 * A class of game controller implementations.
 * @author : Nik Torfs
 *         Date: 22/02/13
 *         Time: 00:11
 */
public class GameController {

    private HumanPlayer currentPlayer;

    /**
     * Return the current player.
     */
    @Basic
    public HumanPlayer getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Set the current player.
     */
    @Basic
    public void setCurrentPlayer(HumanPlayer currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    //TODO selectDirection
    public void selectDirection(Direction direction){
        throw new RuntimeException("Unimplemented");
    }

    /**
     * Retrieve the inventory of the current player.
     * @throws InventoryEmptyException
     *         The current player's inventory is empty
     *         | getCurrentPlayer().getInventory().isEmpty()
     * @return A list of the items in the inventory of the current player.
     */
    public List<Item> showInventory(){
        List<Item> inventory = getCurrentPlayer().getInventory().getItems();
        if(!inventory.isEmpty()) {
            return inventory;
        } else {
            throw new InventoryEmptyException("Inventory empty");
        }
    }

    /**
     * Retrieve an item from the player's inventory and set it as the currently selected item
     * @param identifier
     *        The identifier of the item.
     * @post  The item is selected
     *        | currentPlayer.getCurrentlySelectedItem()
     *        |  == currentPlayer.getInventory().retrieveItem(identifier)
     */
    public void selectItem(int identifier){
        Item selectedItem = currentPlayer.getInventory().retrieveItem(identifier);
        currentPlayer.setCurrentlySelectedItem(selectedItem);
    }

    /**
     * Use the currently selected item
     * @post   The item is removed from the player's inventory
     *         | !currentPlayer.getInventory().contains(currentPlayer.getCurrentlySelectedItem())
     * @post   The player's available actions is reduced by 1
     *         | new.currentPlayer.getAvailableActions() = currentPlayer.getAvailableActions()-1
     * @return A boolean to indicate whether the item was successfully used
     */
    public boolean useCurrentItem(){
        Item currentItem = currentPlayer.getCurrentlySelectedItem();
        currentPlayer.getInventory().removeItem(currentItem.getIdentifier());
        currentItem.use(currentPlayer.getCurrentSquare());
        currentPlayer.setAvailableActions(currentPlayer.getAvailableActions()-1);
        //TODO check successful use of item
        return true;
    }

    //TODO cancelItemUsage
    public void cancelItemUsage(){
        //No further actions required (iteration 1)
    }

    //TODO getAvailableItems
    public void getAvailableItems(){
        throw new RuntimeException("Unimplemented");
    }

    //TODO pickUpItem
    public void pickUpItem(/*identifier*/){
        throw new RuntimeException("Unimplemented");
    }

    //TODO endTurn
    public void endTurn(){
        throw new RuntimeException("Unimplemented");
    }
}
