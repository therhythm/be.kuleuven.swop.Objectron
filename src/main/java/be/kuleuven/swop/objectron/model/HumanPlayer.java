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

    private String name;
    private Square currentSquare;
    private Inventory inventory;

    private List<PlayerEventListener> listeners = new ArrayList<PlayerEventListener>();

    public HumanPlayer(String name) {
        this.name = name;
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
}
