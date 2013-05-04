package be.kuleuven.swop.objectron.handler;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.InventoryEmptyException;
import be.kuleuven.swop.objectron.domain.exception.NoItemSelectedException;
import be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException;
import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.item.deployer.ItemDeployer;
import be.kuleuven.swop.objectron.domain.item.deployer.PlacingItemDeployer;
import be.kuleuven.swop.objectron.domain.item.deployer.ThrowingItemDeployer;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author : Nik Torfs
 *         Date: 12/03/13
 *         Time: 01:10
 */
public class UseItemHandler extends Handler {

    public UseItemHandler(GameState state) {
        super(state);
    }

    /**
     * Retrieve the inventory of the current player.
     *
     * @return A list of the items in the inventory of the current player.
     * @throws be.kuleuven.swop.objectron.domain.exception.InventoryEmptyException
     *          The current player's inventory is empty
     *          | getCurrentPlayer().getInventory().isEmpty()
     */
    public List<Item> showInventory() throws InventoryEmptyException {
        List<Item> inventory = state.getTurnManager().getCurrentTurn().getCurrentPlayer().getInventoryItems();
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
    public String selectItemFromInventory(int identifier) {
        Item currentlySelectedItem = state.getTurnManager().getCurrentTurn().getCurrentPlayer().getInventoryItem(identifier);
        state.getTurnManager().getCurrentTurn().setCurrentItem(currentlySelectedItem);
        return currentlySelectedItem.getName();
    }

    /**
     * Use the currently selected item
     *
     * @throws be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException
     *          The square the player is trying to access, is occupied.
     *          | state.getCurrentPlayer().getCurrentSquare().hasActiveItem()
     * @throws be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException
     *          The player has no more available actions.
     *          | state.getCurrentPlayer().getAvailableActions() == 0
     * @post The item is removed from the player's inventory
     * | !currentPlayer.getInventory().contains(currentPlayer.getCurrentlySelectedItem())
     * @post The player's available actions is reduced by 1
     * | new.currentPlayer.getAvailableActions() = currentPlayer.getAvailableActions()-1
     */
    public void useCurrentItem() throws SquareOccupiedException, NotEnoughActionsException, NoItemSelectedException {
        TurnManager turnManager = state.getTurnManager();
        Turn currentTurn = turnManager.getCurrentTurn();
        if (currentTurn.getCurrentItem() == null) {
            throw new NoItemSelectedException("You don't have an item selected.");
        }

        currentTurn.checkEnoughActions();
        currentTurn.reduceRemainingActions(1);
        state.endAction();
        ItemDeployer deployer = new PlacingItemDeployer(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare());
        currentTurn.getCurrentPlayer().useItem(state.getTurnManager().getCurrentTurn().getCurrentItem(), deployer);
        state.getTurnManager().getCurrentTurn().setCurrentItem(null);

    }

    public void useCurrentIdentityDisc(Direction direction) throws SquareOccupiedException, NotEnoughActionsException, NoItemSelectedException {
        TurnManager turnManager = state.getTurnManager();
        Turn currentTurn = turnManager.getCurrentTurn();

        if (currentTurn.getCurrentItem() == null) {
            throw new NoItemSelectedException("You don't have an item selected.");
        }

        Player currentPlayer = turnManager.getCurrentTurn().getCurrentPlayer();
        currentTurn.checkEnoughActions();

        ItemDeployer deployer = new ThrowingItemDeployer(currentPlayer.getCurrentSquare(), direction, turnManager);
        currentPlayer.useItem(state.getTurnManager().getCurrentTurn().getCurrentItem(), deployer);
        state.getTurnManager().getCurrentTurn().setCurrentItem(null);
        currentTurn.reduceRemainingActions(1);
    }

    /**
     * Cancel the usage of a selected item
     *
     * @post The item is no longer selected.
     * | new.state.getCurrentPlayer().getCurrentlySelectedItem() == null
     */
    public void cancelItemUsage() {
        state.getTurnManager().getCurrentTurn().setCurrentItem(null);
    }
}
