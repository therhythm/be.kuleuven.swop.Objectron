package be.kuleuven.swop.objectron.controller;

import be.kuleuven.swop.objectron.GameState;
import be.kuleuven.swop.objectron.model.Direction;
import be.kuleuven.swop.objectron.model.Item;
import be.kuleuven.swop.objectron.model.Player;
import be.kuleuven.swop.objectron.model.Square;

import java.util.List;

/**
 * @author : Nik Torfs
 *         Date: 22/02/13
 *         Time: 00:11
 */
public class GameController {
    private GameState state;

    public GameController(GameState state) {
        this.state = state;
    }


    //TODO selectDirection
    public void move(Direction direction){
        throw new RuntimeException("Unimplemented");
    }

    //TODO showInventory
    public void showInventory(){
        throw new RuntimeException("Unimplemented");
    }

    //TODO selectItem
    public void selectItem(/*identifier*/){
        throw new RuntimeException("Unimplemented");
    }

    //TODO useCurrentItem
    public boolean useCurrentItem(){
        throw new RuntimeException("Unimplemented");
    }

    //TODO cancelItemUsage
    public void cancelItemUsage(){
        throw new RuntimeException("Unimplemented");
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
        throw new RuntimeException("Unimplemented");
    }
}
