package be.kuleuven.swop.objectron.controller;

import be.kuleuven.swop.objectron.GameState;
import be.kuleuven.swop.objectron.gui.GameView;
import be.kuleuven.swop.objectron.listener.GameEventListener;
import be.kuleuven.swop.objectron.model.exception.*;
import be.kuleuven.swop.objectron.model.item.Item;
import be.kuleuven.swop.objectron.model.item.NullItem;
import be.kuleuven.swop.objectron.viewmodel.PlayerViewModel;
import be.kuleuven.swop.objectron.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class of game controller implementations.
 *
 * @author : Nik Torfs
 *         Date: 22/02/13
 *         Time: 00:11
 */
public class GameController {
    private static final Logger logger = Logger.getLogger(GameController.class.getCanonicalName());
    private GameState state;
    private List<GameEventListener> listeners = new ArrayList<GameEventListener>();

    /**
     * Initialize the game controller with a give game state.
     *
     * @param state The game state to initialize the controller with.
     * @post The new game state equals the given state.
     * | new.state == state
     */
    public GameController(GameState state) {
        this.state = state;
    }

    /**
     * Move the player in a given direction.
     *
     * @param direction The direction the player wants to move in.
     * @throws be.kuleuven.swop.objectron.model.exception.InvalidMoveException This is an invalid move.
     *                              | !state.getGrid().validPosition(
     *                              |  player.getCurrentSquare().getNeighbour(direction))
     * @post The player is moved in the chosen direction.
     * | new.state.getCurrentPlayer().getCurrentSquare()
     * |  != state.getCurrentPlayer().getCurrentSquare()
     */
    public void move(Direction direction) throws InvalidMoveException, NotEnoughActionsException {
        try {
            state.getGrid().makeMove(direction, state.getCurrentPlayer());
            doPlayerUpdate();
        } catch (InvalidMoveException e) {
            logger.log(Level.INFO, state.getCurrentPlayer().getName() + " tried to do an invalid move!");
            throw e;
        } catch (NotEnoughActionsException e) {
            logger.log(Level.INFO, state.getCurrentPlayer().getName() + " tried to do a move when he had no actions remaining.");
            throw e;
        }
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

    private void doPlayerUpdate() {
        PlayerViewModel viewModel = state.getCurrentPlayer().getPlayerViewModel();
        for(GameEventListener listener : listeners){
            listener.playerUpdated(viewModel);
        }
    }

    /**
     * Use the currently selected item
     *
     * @return A boolean to indicate whether the item was successfully used
     * @post The item is removed from the player's inventory
     * | !currentPlayer.getInventory().contains(currentPlayer.getCurrentlySelectedItem())
     * @post The player's available actions is reduced by 1
     * | new.currentPlayer.getAvailableActions() = currentPlayer.getAvailableActions()-1
     */
    public void useCurrentItem() throws SquareOccupiedException, NotEnoughActionsException {
        try {
            state.getCurrentPlayer().useCurrentItem();
            doPlayerUpdate();
        } catch (SquareOccupiedException e) {
            logger.log(Level.INFO, state.getCurrentPlayer().getName() + " tried to place an item on an occupied square!");
            throw e;
        } catch (NotEnoughActionsException e) {
            logger.log(Level.INFO, state.getCurrentPlayer().getName() + " tried to do use an item when he had no actions remaining.");
            throw e;
        }
    }

    /**
     * Cancel the usage of a selected item
     *
     * @post The item is no longer selected.
     * | new.state.getCurrentPlayer().getCurrentlySelectedItem() == null
     */
    public void cancelItemUsage() {
        state.getCurrentPlayer().setCurrentlySelectedItem(new NullItem());
        doPlayerUpdate();
    }


    /**
     * Retrieve a list of available items on the current square.
     *
     * @return A list with available items.
     * @throws IllegalStateException The inventory of the current player is full.
     *                               | currentPlayer.isInventoryFull()
     * @throws IllegalStateException The current square has no items
     *                               | currentSquare.availableItems().size() == 0
     */
    public List<Item> getAvailableItems() {
        Player currentPlayer = this.state.getCurrentPlayer();
        Square currentSquare = currentPlayer.getCurrentSquare();

        List<Item> availableItems = currentSquare.getAvailableItems();
        if (availableItems.size() == 0) {
            throw new IllegalStateException("no items in current square");
        }

        return availableItems;
    }

    /**
     * Pick up an item from the current square.
     *
     * @param selectionId The ID of the item to pick up.
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
            doPlayerUpdate();
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
     * End the current player's turn.
     *
     * @throws be.kuleuven.swop.objectron.model.exception.GameOverException The player hasn't moved during this turn and loses the game.
     *                           | !state.getCurrentPlayer().hasMoved()
     * @post The current player is switched to a new player.
     * | new.state.getCurrentPlayer() != state.getCurrentPlayer()
     */
    public void endTurn() throws GameOverException {
        if (!state.getCurrentPlayer().hasMoved()) {
            throw new GameOverException("You haven't moved the previous turn and therefore you have lost the game");
        } else {
            state.getCurrentPlayer().endTurn();
            state.nextPlayer();
        }
    }

    public void addGameEventListener(GameView gameEventListener) {
        this.listeners.add(gameEventListener);
    }
}
