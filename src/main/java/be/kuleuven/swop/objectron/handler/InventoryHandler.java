package be.kuleuven.swop.objectron.handler;

import be.kuleuven.swop.objectron.GameState;
import be.kuleuven.swop.objectron.gui.GameView;
import be.kuleuven.swop.objectron.listener.GameEventListener;
import be.kuleuven.swop.objectron.model.Player;
import be.kuleuven.swop.objectron.model.Square;
import be.kuleuven.swop.objectron.model.exception.InventoryEmptyException;
import be.kuleuven.swop.objectron.model.exception.InventoryFullException;
import be.kuleuven.swop.objectron.model.exception.NotEnoughActionsException;
import be.kuleuven.swop.objectron.model.item.Item;
import be.kuleuven.swop.objectron.viewmodel.PlayerViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author : Nik Torfs
 *         Date: 11/03/13
 *         Time: 03:48
 */
public class InventoryHandler {
    private static final Logger logger = Logger.getLogger(InventoryHandler.class.getCanonicalName());
    private GameState state;
    private List<GameEventListener> listeners = new ArrayList<GameEventListener>();

    public InventoryHandler(GameState state){
        this.state = state;
    }

    /**
     * Retrieve the inventory of the current player.
     *
     * @return A list of the items in the inventory of the current player.
     * @throws be.kuleuven.swop.objectron.model.exception.InventoryEmptyException The current player's inventory is empty
     *                                 | getCurrentPlayer().getInventory().isEmpty()
     */
    public List<Item> showInventory() throws InventoryEmptyException {
        List<Item> inventory = state.getCurrentPlayer().getInventoryItems();
        if (!inventory.isEmpty()) {
            return inventory;
        } else {
            throw new InventoryEmptyException("Inventory empty");
        }
    }

    /**
     * Retrieve an item from the player's inventory and set it as the currently selected item
     *
     * @param identifier The identifier of the item.
     * @post The item is selected
     * | currentPlayer.getCurrentlySelectedItem()
     * |  == currentPlayer.getInventory().retrieveItem(identifier)
     */
    public void selectItemFromInventory(int identifier) {
        Item selectedItem = state.getCurrentPlayer().getInventoryItem(identifier);
        state.getCurrentPlayer().setCurrentlySelectedItem(selectedItem);
        doPlayerUpdate();
    }

    /**
     * Pick up an item from the current square.
     *
     * @param selectionId The ID of the item to pick up.
     * @throws be.kuleuven.swop.objectron.model.exception.InventoryFullException The player's inventory is full.
     *         | state.getCurrentPlayer().getInventoryItems() == 6
     * @throws be.kuleuven.swop.objectron.model.exception.NotEnoughActionsException The player has no more available actions.
     *         | state.getCurrentPlayer().getAvailableActions() == 0
     * @post The item is removed from the current square.
     * | !state.getCurrentPlayer().getCurrentSquare().
     * |  getAvailableItems().get(selectionId)
     * @post The item is added to the inventory of the current player.
     * | state.getCurrentPlayer().getInventoryItems().get(selectionId)
     */
    public void pickUpItem(int selectionId) throws InventoryFullException, NotEnoughActionsException {
        Player currentPlayer = state.getCurrentPlayer();
        Square currentSquare = currentPlayer.getCurrentSquare();

        Item selectedItem = currentSquare.pickUpItem(selectionId);

        try {
            currentPlayer.addToInventory(selectedItem);
            //doPlayerUpdate();
        } catch (InventoryFullException e) {
            currentSquare.addItem(selectedItem);
            logger.log(Level.INFO, state.getCurrentPlayer().getName() + " has a full inventory!");
            throw e;
        } catch (NotEnoughActionsException e) {
            logger.log(Level.INFO, state.getCurrentPlayer().getName() + " tried to add an item to the inventory when he had no actions remaining.");
            throw e;
        }
    }

    /**
     * Add a game event listener to the list of listeners.
     *
     * @param gameEventListener The game event listener to add.
     * @post The listener is added to the list.
     *       | new.listeners.contains(gameEventListener)
     */
    public void addGameEventListener(GameView gameEventListener) {
        this.listeners.add(gameEventListener);
    }

    /**
     * Send player updates to the game event listeners.
     */
    private void doPlayerUpdate() {
        PlayerViewModel viewModel = state.getCurrentPlayer().getPlayerViewModel();
        for(GameEventListener listener : listeners){
            listener.playerUpdated(viewModel);
        }
    }
}
