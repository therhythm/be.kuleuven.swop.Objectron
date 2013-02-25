package be.kuleuven.swop.objectron.controller;

import be.kuleuven.swop.objectron.model.*;

import java.util.List;

/**
 * @author : Nik Torfs
 *         Date: 22/02/13
 *         Time: 00:11
 */
public class GameController {

    private HumanPlayer currentPlayer;

    public HumanPlayer getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(HumanPlayer currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    //TODO selectDirection
    public void selectDirection(Direction direction){
        throw new RuntimeException("Unimplemented");
    }

    //TODO showInventory
    public List<Item> showInventory(){
        List<Item> inventory = getCurrentPlayer().getInventory().getItems();
        if(!inventory.isEmpty()) {
            return inventory;
        } else {
            throw new InventoryEmptyException("Inventory empty");
        }
    }

    //TODO selectItem
    public void selectItem(int identifier){
        Item selectedItem = currentPlayer.getInventory().retrieveItem(identifier);
        currentPlayer.setCurrentlySelectedItem(selectedItem);
    }

    //TODO useCurrentItem
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
