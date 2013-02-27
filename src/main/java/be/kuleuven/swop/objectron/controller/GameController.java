package be.kuleuven.swop.objectron.controller;

import be.kuleuven.swop.objectron.model.Direction;
import be.kuleuven.swop.objectron.model.Grid;
import be.kuleuven.swop.objectron.model.InvalidMoveException;
import be.kuleuven.swop.objectron.model.Player;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author : Nik Torfs
 *         Date: 22/02/13
 *         Time: 00:11
 */
public class GameController {
    private static final Logger logger = Logger.getLogger(GameController.class.getCanonicalName());

    private Player currentPlayer;
    private Grid gameGrid;


    public void move(Direction direction){
        try {
            gameGrid.makeMove(direction, currentPlayer);
        } catch (InvalidMoveException e) {
            logger.log(Level.INFO, currentPlayer.getName() + " has made an invalid move!");
            //TODO do some sort of gamelistener event to show message
        }
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
