package be.kuleuven.swop.objectron.handler;

import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.InventoryFullException;
import be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException;
import be.kuleuven.swop.objectron.domain.exception.SquareEmptyException;
import be.kuleuven.swop.objectron.domain.gamestate.Game;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.square.Square;

import java.util.List;

/**
 * @author : Nik Torfs
 *         Date: 12/03/13
 *         Time: 01:10
 */
public class PickUpItemHandler extends Handler {

    public PickUpItemHandler(Game game) {
        super(game);
    }

    /**
     * Pick up an item from the current square.
     *
     * @param selectionId The ID of the item to pick up.
     * @throws be.kuleuven.swop.objectron.domain.exception.InventoryFullException
     *          The player's inventory is full.
     *          | game.getCurrentPlayer().getInventoryItems() == 6
     * @throws be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException
     *          The player has no more available actions.
     *          | game.getCurrentPlayer().getAvailableActions() == 0
     * @post The item is removed from the current square.
     * | !game.getCurrentPlayer().getCurrentSquare().
     * |  getAvailableItems().get(selectionId)
     * @post The item is added to the inventory of the current player.
     * | game.getCurrentPlayer().getInventoryItems().get(selectionId)
     */
    public void pickUpItem(int selectionId) throws InventoryFullException, NotEnoughActionsException {
        Turn currentTurn = game.getTurnManager().getCurrentTurn();

        currentTurn.checkEnoughActions();
        Player currentPlayer = currentTurn.getCurrentPlayer();
        currentPlayer.pickupItem(selectionId);
        currentTurn.reduceRemainingActions(1);
        game.endAction();
        game.notifyObservers();
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
        Turn currentTurn = game.getTurnManager().getCurrentTurn();
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
