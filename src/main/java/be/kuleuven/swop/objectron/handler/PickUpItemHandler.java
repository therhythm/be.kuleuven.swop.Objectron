package be.kuleuven.swop.objectron.handler;

import be.kuleuven.swop.objectron.GameState;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.exception.InventoryFullException;
import be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException;
import be.kuleuven.swop.objectron.domain.exception.SquareEmptyException;
import be.kuleuven.swop.objectron.domain.item.Item;

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
        Player currentPlayer = state.getCurrentPlayer();

        try {
            currentPlayer.pickupItem(selectionId);
        } catch (InventoryFullException e) {
            logger.log(Level.INFO, state.getCurrentPlayer().getName() + " has a full inventory!");
            throw e;
        } catch (NotEnoughActionsException e) {
            logger.log(Level.INFO, state.getCurrentPlayer().getName() + " tried to add an item to the inventory when he had no actions remaining.");
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
        if (currentPlayer.getAvailableActions() == 0) {
            throw new NotEnoughActionsException("You don't have the actions to do this");
        } else if (availableItems.size() == 0) {
            throw new SquareEmptyException("no items in current square");
        }

        return availableItems;
    }
}
