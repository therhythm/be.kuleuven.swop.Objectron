package be.kuleuven.swop.objectron.model;

import be.kuleuven.swop.objectron.model.listener.PlayerEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Nik Torfs
 *         Date: 22/02/13
 *         Time: 00:06
 */
public class HumanPlayer implements Player {
    private static final int NB_ACTIONS_EACH_TURN = 3;

    private String name;
    private Square currentSquare;
    private Item currentlySelectedItem = null;
    private int availableActions = NB_ACTIONS_EACH_TURN;
    private LightTrail lightTrail;
    private Inventory inventory;
    private List<PlayerEventListener> listeners = new ArrayList<PlayerEventListener>();

    public HumanPlayer(String name, Square currentSquare) {
        this.name = name;
        this.currentSquare = currentSquare;
        lightTrail = new LightTrail();
        inventory = new KeyValueInventory();
    }

    @Override
    public boolean isInventoryFull() {
        return this.inventory.isLimitReached();
    }

    @Override
    public Square getCurrentSquare() {
        return currentSquare;
    }

    @Override
    public void setCurrentSquare(Square currentSquare) {
        this.currentSquare = currentSquare;
    }

    @Override
    public void addToInventory(Item itemToAdd) {
       this.inventory.addItem(itemToAdd);
    }

    @Override
    public void addPlayerEventListener(PlayerEventListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removePlayerEventListener(PlayerEventListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void move(Square newPosition) {
        lightTrail.expand(currentSquare);
        currentSquare = newPosition;
        currentSquare.stepOn();//TODO (possibly arguments)
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getAvailableActions() {
        return availableActions;
    }

    @Override
    public Item getCurrentlySelectedItem() {
        return currentlySelectedItem;
    }

    @Override
    public List<Item> getInventoryItems() {
        return inventory.getItems();
    }

    @Override
    public Item getInventoryItem(int identifier) {
        return inventory.retrieveItem(identifier);
    }

    @Override
    public void setCurrentlySelectedItem(Item currentlySelectedItem) {
        this.currentlySelectedItem = currentlySelectedItem;
    }

    @Override
    public void useCurrentItem() {
        inventory.removeItem(currentlySelectedItem);
        currentlySelectedItem.use(currentSquare);
        reduceAvailableActions();
    }

    private void reduceAvailableActions(){
        availableActions--;
        lightTrail.retract();
    }
}
