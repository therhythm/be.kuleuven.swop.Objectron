package be.kuleuven.swop.objectron.handler;

import be.kuleuven.swop.objectron.GameState;
import be.kuleuven.swop.objectron.model.exception.InventoryEmptyException;
import be.kuleuven.swop.objectron.model.exception.NotEnoughActionsException;
import be.kuleuven.swop.objectron.model.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.model.item.Item;
import be.kuleuven.swop.objectron.model.item.NullItem;
import be.kuleuven.swop.objectron.viewmodel.PlayerViewModel;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author : Nik Torfs
 *         Date: 12/03/13
 *         Time: 01:10
 */
public class UseItemHandler extends Handler {
    private static Logger logger = Logger.getLogger(UseItemHandler.class.getCanonicalName());

    public UseItemHandler(GameState state) {
        super(state);
    }

    /**
     * Retrieve the inventory of the current player.
     *
     * @return A list of the items in the inventory of the current player.
     * @throws be.kuleuven.swop.objectron.model.exception.InventoryEmptyException
     *          The current player's inventory is empty
     *          | getCurrentPlayer().getInventory().isEmpty()
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
    public PlayerViewModel selectItemFromInventory(int identifier) {
        Item selectedItem = state.getCurrentPlayer().getInventoryItem(identifier);
        state.getCurrentPlayer().setCurrentlySelectedItem(selectedItem);
        return state.getCurrentPlayer().getPlayerViewModel();
    }

    /**
     * Use the currently selected item
     *
     * @return A boolean to indicate whether the item was successfully used
     * @throws be.kuleuven.swop.objectron.model.exception.SquareOccupiedException
     *          The square the player is trying to access, is occupied.
     *          | state.getCurrentPlayer().getCurrentSquare().hasActiveItem()
     * @throws be.kuleuven.swop.objectron.model.exception.NotEnoughActionsException
     *          The player has no more available actions.
     *          | state.getCurrentPlayer().getAvailableActions() == 0
     * @post The item is removed from the player's inventory
     * | !currentPlayer.getInventory().contains(currentPlayer.getCurrentlySelectedItem())
     * @post The player's available actions is reduced by 1
     * | new.currentPlayer.getAvailableActions() = currentPlayer.getAvailableActions()-1
     */
    public PlayerViewModel useCurrentItem() throws SquareOccupiedException, NotEnoughActionsException {
        try {
            state.getCurrentPlayer().useCurrentItem();
            return state.getCurrentPlayer().getPlayerViewModel();
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
    public PlayerViewModel cancelItemUsage() {
        state.getCurrentPlayer().setCurrentlySelectedItem(new NullItem());
        return state.getCurrentPlayer().getPlayerViewModel();
    }
}
