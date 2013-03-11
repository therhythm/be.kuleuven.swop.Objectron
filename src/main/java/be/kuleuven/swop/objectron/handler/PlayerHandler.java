package be.kuleuven.swop.objectron.handler;

import be.kuleuven.swop.objectron.GameState;
import be.kuleuven.swop.objectron.gui.GameView;
import be.kuleuven.swop.objectron.listener.GameEventListener;
import be.kuleuven.swop.objectron.model.Direction;
import be.kuleuven.swop.objectron.model.Player;
import be.kuleuven.swop.objectron.model.Square;
import be.kuleuven.swop.objectron.model.exception.*;
import be.kuleuven.swop.objectron.model.item.Item;
import be.kuleuven.swop.objectron.model.item.NullItem;
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
public class PlayerHandler {
    private static final Logger logger = Logger.getLogger(PlayerHandler.class.getCanonicalName());
    private GameState state;
    private List<GameEventListener> listeners = new ArrayList<GameEventListener>();

    public PlayerHandler(GameState state){
        this.state = state;
    }

    /**
     * Move the player in a given direction.
     *
     * @param direction The direction the player wants to move in.
     * @throws be.kuleuven.swop.objectron.model.exception.InvalidMoveException This is an invalid move.
     *                              | !state.getGrid().validPosition(
     *                              |  player.getCurrentSquare().getNeighbour(direction))
     * @throws NotEnoughActionsException The player has not enough actions remaining.
     *                              | state.getCurrentPlayer().getAvailableActions() == 0
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
     * Retrieve a list of available items on the current square.
     *
     * @return A list with available items.
     * @throws IllegalStateException The inventory of the current player is full.
     *                               | currentPlayer.isInventoryFull()
     * @throws IllegalStateException The current square has no items
     *                               | currentSquare.availableItems().size() == 0
     */
    public List<Item> getAvailableItems() throws SquareEmptyException, NotEnoughActionsException {
        Player currentPlayer = this.state.getCurrentPlayer();
        Square currentSquare = currentPlayer.getCurrentSquare();

        List<Item> availableItems = currentSquare.getAvailableItems();
        if(currentPlayer.getAvailableActions() == 0){
            throw new NotEnoughActionsException("You don't have the actions to do this");
        }
        else if (availableItems.size() == 0) {
            throw new SquareEmptyException("no items in current square");
        }

        return availableItems;
    }

    /**
     * Use the currently selected item
     *
     * @return A boolean to indicate whether the item was successfully used
     * @throws SquareOccupiedException The square the player is trying to access, is occupied.
     *         | state.getCurrentPlayer().getCurrentSquare().hasActiveItem()
     * @throws NotEnoughActionsException The player has no more available actions.
     *         | state.getCurrentPlayer().getAvailableActions() == 0
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
            doPlayerUpdate();
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
