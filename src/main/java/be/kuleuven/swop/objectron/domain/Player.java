package be.kuleuven.swop.objectron.domain;

import be.kuleuven.swop.objectron.domain.exception.InventoryFullException;
import be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException;
import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.item.UseItemRequest;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.viewmodel.PlayerViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Nik Torfs
 *         Date: 22/02/13
 *         Time: 00:06
 */
public class Player {

    private String name;
    private Square currentSquare;
    private Square initialSquare;
    private int availableActions = Settings.PLAYER_ACTIONS_EACH_TURN;
    private int remainingActionsSlowed = 0;
    private LightTrail lightTrail = new LightTrail();
    private Inventory inventory = new Inventory();
    private boolean hasMoved;

    public Player(String name, Square currentSquare) {
        this.name = name;
        this.currentSquare = currentSquare;
        this.initialSquare = currentSquare;
        currentSquare.setObstructed(true);
    }

    public Square getCurrentSquare() {
        return currentSquare;
    }

    public Square getInitialSquare() {
        return initialSquare;
    }


    public void pickupItem(int identifier) throws InventoryFullException, NotEnoughActionsException {
        checkEnoughActions();
        Item item = currentSquare.pickUpItem(identifier);

        try{
            this.inventory.addItem(item);
        }catch(InventoryFullException ex){
            currentSquare.addItem(item);
            throw ex;
        }

        reduceAvailableActions();
    }

    public void move(Square newPosition) throws NotEnoughActionsException {
        checkEnoughActions();
        reduceAvailableActions();
        lightTrail.expand(currentSquare);
        currentSquare.playerLeaves();
        currentSquare = newPosition;
        currentSquare.stepOn(this);
        hasMoved = true;
    }

    public void checkEnoughActions() throws NotEnoughActionsException {
        if (availableActions == 0) {
            throw new NotEnoughActionsException("You can't do any actions anymore, end the turn!");
        }
    }

    public String getName() {
        return this.name;
    }

    public int getAvailableActions() {
        return availableActions;
    }

    public List<Item> getInventoryItems() {
        return inventory.getAllItems();
    }

    public Item getInventoryItem(int identifier) {
        return inventory.retrieveItem(identifier);
    }

    public void useItem(Item item) throws SquareOccupiedException, NotEnoughActionsException {
        checkEnoughActions();
        item.useItem(new UseItemRequest(currentSquare));

        inventory.removeItem(item);

        reduceAvailableActions();
    }

    public void useItem(Item item, Direction direction) throws NotEnoughActionsException, SquareOccupiedException {
        checkEnoughActions();
        item.useItem(new UseItemRequest(currentSquare,direction));

        inventory.removeItem(item);

        reduceAvailableActions();
    }

    public void newTurn() {
        int temp = Settings.PLAYER_ACTIONS_EACH_TURN - remainingActionsSlowed;
        if(temp >= 0){
            availableActions = Settings.PLAYER_ACTIONS_EACH_TURN - remainingActionsSlowed;
            remainingActionsSlowed = 0;
            hasMoved = false;
        }else{
            remainingActionsSlowed = Math.abs(temp);
            availableActions = 0;
            hasMoved = true;
        }
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void reduceRemainingActions(int amount) {
        if (amount > getAvailableActions()){
            remainingActionsSlowed = amount - availableActions;
            availableActions = 0;
        }else{
            availableActions = availableActions - amount;
        }
    }

    private void reduceAvailableActions() {
        reduceRemainingActions(1);
        lightTrail.reduce();
    }

    public PlayerViewModel getPlayerViewModel() {
        return new PlayerViewModel(getName(),
                currentSquare.getPosition(),
                initialSquare.getPosition(),
                getAvailableActions(),
                lightTrail.getLightTrailViewModel());
    }


}
