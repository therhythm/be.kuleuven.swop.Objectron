package be.kuleuven.swop.objectron.handler;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.*;
import be.kuleuven.swop.objectron.domain.gamestate.Game;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.item.deployer.ItemDeployer;
import be.kuleuven.swop.objectron.domain.item.deployer.PlacingItemDeployer;
import be.kuleuven.swop.objectron.domain.item.deployer.ThrowingItemDeployer;

import java.util.List;

/**
 * @author : Nik Torfs
 *         Date: 12/03/13
 *         Time: 01:10
 */
public class UseItemHandler extends Handler {

    public UseItemHandler(Game game) {
        super(game);
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
        List<Item> inventory = game.getTurnManager().getCurrentTurn().getCurrentPlayer().getInventoryItems();
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
        Item currentlySelectedItem = game.getTurnManager().getCurrentTurn().getCurrentPlayer().getInventoryItem
                (identifier);
        game.getTurnManager().getCurrentTurn().setCurrentItem(currentlySelectedItem);
        return currentlySelectedItem.getName();
    }

    /**
     * Use the currently selected item
     *
     * @throws be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException
     *          The square the player is trying to access, is occupied.
     *          | game.getCurrentPlayer().getCurrentSquare().hasActiveItem()
     * @throws be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException
     *          The player has no more available actions.
     *          | game.getCurrentPlayer().getAvailableActions() == 0
     * @post The item is removed from the player's inventory
     * | !currentPlayer.getInventory().contains(currentPlayer.getCurrentlySelectedItem())
     * @post The player's available actions is reduced by 1
     * | new.currentPlayer.getAvailableActions() = currentPlayer.getAvailableActions()-1
     */
    public void useCurrentItem() throws SquareOccupiedException, NotEnoughActionsException, NoItemSelectedException,
            GameOverException {
        TurnManager turnManager = game.getTurnManager();
        Turn currentTurn = turnManager.getCurrentTurn();
        if (currentTurn.getCurrentItem() == null) {
            throw new NoItemSelectedException("You don't have an item selected.");
        }

        currentTurn.checkEnoughActions();
        currentTurn.reduceAction();
        ItemDeployer deployer = new PlacingItemDeployer(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare());
        currentTurn.getCurrentPlayer().useItem(game.getTurnManager().getCurrentTurn().getCurrentItem(), deployer);
        game.getTurnManager().getCurrentTurn().setCurrentItem(null);

    }

    public void useCurrentIdentityDisc(Direction direction) throws SquareOccupiedException,
            NotEnoughActionsException, NoItemSelectedException, GameOverException {
        TurnManager turnManager = game.getTurnManager();
        Turn currentTurn = turnManager.getCurrentTurn();

        if (currentTurn.getCurrentItem() == null) {
            throw new NoItemSelectedException("You don't have an item selected.");
        }

        Player currentPlayer = turnManager.getCurrentTurn().getCurrentPlayer();
        currentTurn.checkEnoughActions();

        ItemDeployer deployer = new ThrowingItemDeployer(currentPlayer.getCurrentSquare(), direction, turnManager);
        currentPlayer.useItem(game.getTurnManager().getCurrentTurn().getCurrentItem(), deployer);
        game.getTurnManager().getCurrentTurn().setCurrentItem(null);
        currentTurn.reduceAction();
    }

    /**
     * Cancel the usage of a selected item
     *
     * @post The item is no longer selected.
     * | new.game.getCurrentPlayer().getCurrentlySelectedItem() == null
     */
    public void cancelItemUsage() {
        game.getTurnManager().getCurrentTurn().setCurrentItem(null);
    }
}
