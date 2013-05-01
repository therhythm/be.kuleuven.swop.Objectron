package be.kuleuven.swop.objectron.domain;

import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.exception.InventoryFullException;
import be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException;
import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.item.deployer.ItemDeployer;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.viewmodel.PlayerViewModel;

import java.util.List;

/**
 * @author : Nik Torfs
 *         Date: 22/02/13
 *         Time: 00:06
 */
public class Player{
    private String name;
    private Square currentSquare;
    private Square initialSquare;
    private LightTrail lightTrail = new LightTrail();
    private Inventory inventory = new Inventory();
    private int remainingPenalties;
    private boolean isTeleporting;

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

        try {
            this.inventory.addItem(item);
        } catch (InventoryFullException ex) {
            currentSquare.addItem(item);
            throw ex;
        }
        actionPerformed();
    }

    public void move(Square newPosition) {

        actionPerformed();
        lightTrail.expand(currentSquare);
        currentSquare = newPosition;
        isTeleporting = false;
    }

    public void teleport(Square destination) {
        isTeleporting = true;
        lightTrail.expand(currentSquare);
        currentSquare = destination;
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

    public void useItem(Item item, ItemDeployer deployer) throws SquareOccupiedException, NotEnoughActionsException {

        deployer.deploy(item);

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

    public void addRemainingPenalties(int remainingPenalties) {
        this.remainingPenalties += remainingPenalties;
    }

    public void reduceRemainingPenalties(int playerActionsEachTurn) {
        this.remainingPenalties -= playerActionsEachTurn;
        if (this.remainingPenalties < 0) {
            this.remainingPenalties = 0;
        }
    }

    public String toString() {
        String result = "";
        result += "name: " + this.getName() + "\n";
        result += "position: " + this.getCurrentSquare() + "\n";

        return result;
    }

    public boolean isTeleporting() {
        return isTeleporting;
    }
}
