package be.kuleuven.swop.objectron.controller;

import be.kuleuven.swop.objectron.GameState;
import be.kuleuven.swop.objectron.model.*;

import java.util.logging.Level;
import java.util.logging.Logger;


import java.util.List;

/**
 * A class of game controller implementations.
 * @author : Nik Torfs
 *         Date: 22/02/13
 *         Time: 00:11
 */
public class GameController {
    private static final Logger logger = Logger.getLogger(GameController.class.getCanonicalName());
    private GameState state;

    public GameController(GameState state) {
        this.state = state;
    }

    public void move(Direction direction) throws InvalidMoveException {
        try {
            state.getGrid().makeMove(direction, state.getCurrentPlayer());
        } catch (InvalidMoveException e) {
            logger.log(Level.INFO, state.getCurrentPlayer().getName() + " tried to do an invalid move!");
            throw e;
        }
    }



    /**
     * Retrieve the inventory of the current player.
     * @throws InventoryEmptyException
     *         The current player's inventory is empty
     *         | getCurrentPlayer().getInventory().isEmpty()
     * @return A list of the items in the inventory of the current player.
     */
    public List<Item> showInventory(){
        List<Item> inventory = state.getCurrentPlayer().getInventoryItems();
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
    public void selectInventoryItem(int identifier){
        Item selectedItem = state.getCurrentPlayer().getInventoryItem(identifier);
        state.getCurrentPlayer().setCurrentlySelectedItem(selectedItem);
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
        state.getCurrentPlayer().useCurrentItem();
        //TODO check successful use of item
        return true;
    }

    //TODO cancelItemUsage
    public void cancelItemUsage(){
        //No further actions required (iteration 1)
    }


    /**
     * @throws IllegalStateException when the inventory of the currentPlayer is full
     *          currentPlayer.isInventoryFull()
     *
     *         IllegalStateException when the current square has no items
     *          currentSquare.availableItems().size()==0
     *
     * @return list with available items
     */
    public  List<Item>  getAvailableItems(){
        Player currentPlayer = this.state.getCurrentPlayer();
        if (currentPlayer.isInventoryFull())
            throw new IllegalStateException("inventory full");

        Square currentSquare = currentPlayer.getCurrentSquare();

        List<Item> availableItems = currentSquare.getAvailableItems();
        if(availableItems.size()==0)
            throw new IllegalStateException("no items in current square");

        return availableItems;
    }

    /**
     *
     * @param selectionId
     * @post the item will be removed from the current square and will be added to the inventory of the currentPlayer
     */
    public void selectItem(int selectionId){
        Player currentPlayer = state.getCurrentPlayer();
        Square currentSquare = currentPlayer.getCurrentSquare();

        Item selectedItem = currentSquare.pickUpItem(selectionId);
        currentPlayer.addToInventory(selectedItem);
    }

    //TODO endTurn
    public void endTurn(){
        if(!state.getCurrentPlayer().hasMoved())
            throw new GameOverException("You haven't moved the previous turn and therefore you have lost the game");
        else
            state.switchContext();
    }
}
