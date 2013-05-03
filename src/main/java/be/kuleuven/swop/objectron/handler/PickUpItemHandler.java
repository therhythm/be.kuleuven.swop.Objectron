package be.kuleuven.swop.objectron.handler;

import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.InventoryFullException;
import be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException;
import be.kuleuven.swop.objectron.domain.exception.SquareEmptyException;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.square.Square;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author : Nik Torfs
 *         Date: 12/03/13
 *         Time: 01:10
 */
public class PickUpItemHandler extends Handler {
    private static Logger logger = Logger.getLogger(PickUpItemHandler.class.getCanonicalName());

    public PickUpItemHandler(GameState state) {
        super(state);
    }

    /**
     * Pick up an item from the current square.
     *
     * @param selectionId The ID of the item to pick up.
     * @throws be.kuleuven.swop.objectron.domain.exception.InventoryFullException
     *          The player's inventory is full.
     *          | state.getCurrentPlayer().getInventoryItems() == 6
     * @throws be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException
     *          The player has no more available actions.
     *          | state.getCurrentPlayer().getAvailableActions() == 0
     * @post The item is removed from the current square.
     * | !state.getCurrentPlayer().getCurrentSquare().
     * |  getAvailableItems().get(selectionId)
     * @post The item is added to the inventory of the current player.
     * | state.getCurrentPlayer().getInventoryItems().get(selectionId)
     */
    public void pickUpItem(int selectionId) throws InventoryFullException, NotEnoughActionsException {
        Turn currentTurn = state.getTurnManager().getCurrentTurn();
        try {
            currentTurn.checkEnoughActions();
            Player currentPlayer = currentTurn.getCurrentPlayer();
            currentPlayer.pickupItem(selectionId);
            currentTurn.reduceRemainingActions(1);
            state.endAction();
            state.notifyObservers();
        } catch (InventoryFullException e) {
            logger.log(Level.INFO, currentTurn.getCurrentPlayer().getName() + " has a full inventory!");
            throw e;
        } catch (NotEnoughActionsException e) {
            logger.log(Level.INFO, currentTurn.getCurrentPlayer().getName() + " tried to add an item to the inventory when he had no actions remaining.");
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
        Turn currentTurn = state.getTurnManager().getCurrentTurn();
        currentTurn.checkEnoughActions();
        Player currentPlayer = currentTurn.getCurrentPlayer();
        Square currentSquare = currentPlayer.getCurrentSquare();

        List<Item> availableItems = currentSquare.getAvailableItems();
        if (availableItems.size() == 0) {
            throw new SquareEmptyException("no items in current square");
        }

        return availableItems;
    }
}
