package be.kuleuven.swop.objectron.controller;


import be.kuleuven.swop.objectron.GameState;
import be.kuleuven.swop.objectron.model.*;

import java.util.logging.Level;
import java.util.logging.Logger;


import java.util.List;

/**
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

    public void move(Direction direction){
        try {
            state.getGrid().makeMove(direction, state.getCurrentPlayer());
        } catch (InvalidMoveException e) {
            logger.log(Level.INFO, state.getCurrentPlayer().getName() + " has made an invalid move!");
            //TODO do some sort of gamelistener event to show message
        }
    }


    public List<Item> showInventory(){
        List<Item> inventory = state.getCurrentPlayer().getInventoryItems();
        if(!inventory.isEmpty()) {
            return inventory;
        } else {
            throw new InventoryEmptyException("Inventory empty");
        }
    }


    public void selectInventoryItem(int identifier){
        Item selectedItem = state.getCurrentPlayer().getInventoryItem(identifier);
        state.getCurrentPlayer().setCurrentlySelectedItem(selectedItem);
    }


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
