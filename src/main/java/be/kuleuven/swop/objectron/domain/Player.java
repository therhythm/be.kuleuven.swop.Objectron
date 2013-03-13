package be.kuleuven.swop.objectron.domain;

import be.kuleuven.swop.objectron.domain.exception.InventoryFullException;
import be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException;
import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.item.Effect;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.viewmodel.PlayerViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Nik Torfs
 *         Date: 22/02/13
 *         Time: 00:06
 */
public class Player {
    private static final int NB_ACTIONS_EACH_TURN = 3;

    private String name;
    private Square currentSquare;
    private int availableActions = NB_ACTIONS_EACH_TURN;
    private LightTrail lightTrail = new LightTrail();
    private Inventory inventory = new Inventory();
    private List<Effect> effects = new ArrayList<Effect>();
    private boolean hasMoved;

    public Player(String name, Square currentSquare) {
        this.name = name;
        this.currentSquare = currentSquare;
        currentSquare.setObstructed(true);
    }

    public Square getCurrentSquare() {
        return currentSquare;
    }

    public void addToInventory(Item itemToAdd) throws InventoryFullException, NotEnoughActionsException {
        checkEnoughActions();
        this.inventory.addItem(itemToAdd);
        reduceAvailableActions();
    }

    public void move(Square newPosition) throws NotEnoughActionsException {
        checkEnoughActions();
        reduceAvailableActions();
        lightTrail.expand(currentSquare);
        currentSquare = newPosition;
        currentSquare.stepOn(this);
        hasMoved = true;
    }

    private void checkEnoughActions() throws NotEnoughActionsException {
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
        item.use(currentSquare);
        inventory.removeItem(item);

        reduceAvailableActions();
    }

    public void endTurn() {
        for (Effect effect : effects) {
            effect.activate(this);
        }
        availableActions = NB_ACTIONS_EACH_TURN;
        hasMoved = false;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void addEffect(Effect effect) {
        this.effects.add(effect);
    }

    public void removeEffect(Effect effect) {
        this.effects.remove(effect);
    }

    public void reduceRemainingActions(int amount) {
        if (amount > getAvailableActions())
            throw new IllegalArgumentException("The amount of actions to reduce is more than the remaining actions");

        this.availableActions -= amount;
    }

    private void reduceAvailableActions() {
        reduceRemainingActions(1);
        lightTrail.reduce();
    }

    public PlayerViewModel getPlayerViewModel() {
        return new PlayerViewModel(getName(),
                currentSquare.getHorizontalIndex(),
                currentSquare.getVerticalIndex(),
                getAvailableActions(),
                lightTrail.getLightTrailViewModel());
    }
}