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
 * A class of PickUpItemHandlers involving a Game.
 * @author : Nik Torfs
 *         Date: 12/03/13
 *         Time: 01:10
 */
public class PickUpItemHandler extends Handler {

    /**
     * Initialize this new PickUpItemHandler with a given Game.
     * @param game
     *        The game for this new PickUpItemHandler.
     * @post  This PickUpItemHandler is initialized with the given Game.
     *        | new.this.game == game
     */
    public PickUpItemHandler(Game game) {
        super(game);
    }

    /**
     * Pick up an item from the current square.
     *
     * @param selectionId
     *        The ID of the item to pick up.
     * @throws be.kuleuven.swop.objectron.domain.exception.InventoryFullException
     *          The player's inventory is full.
     *          | currentTurn.getCurrentPlayer().getInventoryItems() == 6
     * @throws be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException
     *          The player has no more available actions.
     *          | currentTurn.getActionsRemaining() == 0
     * @post The item is removed from the current square.
     * | !new.currentTurn.getCurrentPlayer().getCurrentSquare().
     * |  getAvailableItems().get(selectionId)
     * @post The item is added to the inventory of the current player.
     * | new.currentTurn.getCurrentPlayer().getInventoryItems().get(selectionId)
     */
    public void pickUpItem(int selectionId) throws InventoryFullException, NotEnoughActionsException {
        Turn currentTurn = game.getTurnManager().getCurrentTurn();

        currentTurn.checkEnoughActions();
        Player currentPlayer = currentTurn.getCurrentPlayer();
        currentPlayer.pickupItem(selectionId);
        currentTurn.reduceAction();
    }

    /**
     * Retrieve a list of available items on the current square.
     *
     * @return A list with available items.
     * @throws NotEnoughActionsException
     *         The player has no more available actions.
     *         | currentTurn.getActionsRemaining() == 0
     * @throws SquareEmptyException
     *         The current square has no items
     *         | currentSquare.availableItems().size() == 0
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
