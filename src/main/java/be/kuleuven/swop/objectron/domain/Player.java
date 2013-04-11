package be.kuleuven.swop.objectron.domain;

import be.kuleuven.swop.objectron.domain.exception.InventoryFullException;
import be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException;
import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
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
    private LightTrail lightTrail = new LightTrail();
    private Inventory inventory = new Inventory();
    private int remainingPenalties;

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

    public void pickupItem(int identifier) throws InventoryFullException {
        Item item = currentSquare.pickUpItem(identifier);

        try{
            this.inventory.addItem(item);
        }catch(InventoryFullException ex){
            currentSquare.addItem(item);
            throw ex;
        }

        actionPerformed();
    }

    public void move(Square newPosition) {
        actionPerformed();
        lightTrail.expand(currentSquare);
        currentSquare = newPosition;
    }

    public String getName() {
        return this.name;
    }

    public List<Item> getInventoryItems() {
        return inventory.getAllItems();
    }

    public Item getInventoryItem(int identifier) {
        return inventory.retrieveItem(identifier);
    }

    public void useItem(Item item,UseItemRequest useItemRequest) throws SquareOccupiedException, NotEnoughActionsException {
       
        item.useItem(useItemRequest);

        inventory.removeItem(item);

        actionPerformed();
    }

    private void actionPerformed() {
        lightTrail.reduce();
    }

    public PlayerViewModel getPlayerViewModel() {
        return new PlayerViewModel(getName(),
                currentSquare.getPosition(),
                initialSquare.getPosition(),
                lightTrail.getLightTrailViewModel());
    }

    public int getRemainingPenalties() {
        return remainingPenalties;
    }

    public void setRemainingPenalties(int remainingPenalties) {
        this.remainingPenalties = remainingPenalties;
    }
}
