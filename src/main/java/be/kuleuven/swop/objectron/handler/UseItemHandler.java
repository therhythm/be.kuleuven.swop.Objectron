package be.kuleuven.swop.objectron.handler;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.*;
import be.kuleuven.swop.objectron.domain.gamestate.Game;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.item.deployer.ItemDeployCommand;
import be.kuleuven.swop.objectron.domain.item.deployer.PlacingItemDeployCommand;
import be.kuleuven.swop.objectron.domain.item.deployer.ThrowingItemDeployCommand;

import java.util.List;

/**
 * A class of UseItemHandlers involving a Game.
 * @author : Nik Torfs
 *         Date: 12/03/13
 *         Time: 01:10
 */
public class UseItemHandler extends Handler {

    /**
     * Initialize this new UseItemHandler with a given Game.
     * @param game
     *        The game for this new UseItemHandler.
     * @post  This UseItemHandler is initialized with the given Game.
     *        | new.this.game == game
     */
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
     * @param identifier
     *        The identifier of the item.
     * @post The item is selected
     *       | new.currentTurn.getCurrentItem() == currentPlayer.getInventoryItem(identifier)
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
     *          | currentTurn.getCurrentPlayer().getCurrentSquare().hasActiveItem()
     * @throws be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException
     *          The player has no more available actions.
     *          | currentTurn.getAvailableActions() == 0
     * @throws be.kuleuven.swop.objectron.domain.exception.NoItemSelectedException
     *          The player has no items selected.
     *          | currentTurn.getCurrentItem() == null
     * @post The item is removed from the player's inventory
     * | !new.currentPlayer.getInventory().contains(currentPlayer.getCurrentlySelectedItem())
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
        ItemDeployCommand deployer = new PlacingItemDeployCommand(turnManager.getCurrentTurn().getCurrentPlayer()
                .getCurrentSquare());
        currentTurn.getCurrentPlayer().useItem(game.getTurnManager().getCurrentTurn().getCurrentItem(), deployer);
        game.getTurnManager().getCurrentTurn().setCurrentItem(null);

    }

    /**
     * Use the currently selected identity disc.
     *
     * @param direction
     *        The direction to throw the disc in.
     * @throws SquareOccupiedException
     *         The square the player is trying to access, is occupied.
     *         | currentTurn.getCurrentPlayer().getCurrentSquare().hasActiveItem()
     * @throws NotEnoughActionsException
     *         The player has no more available actions.
     *         | currentTurn.getAvailableActions() == 0
     * @throws NoItemSelectedException
     *         The player has no items selected.
     *         | currentTurn.getCurrentItem() == null
     * @throws GameOverException
     *         The game is finished.
     *         | game.getTurnManager().checkWin() == true
     */
    public void useCurrentIdentityDisc(Direction direction) throws SquareOccupiedException,
            NotEnoughActionsException, NoItemSelectedException, GameOverException {
        TurnManager turnManager = game.getTurnManager();
        Turn currentTurn = turnManager.getCurrentTurn();

        if (currentTurn.getCurrentItem() == null) {
            throw new NoItemSelectedException("You don't have an item selected.");
        }

        Player currentPlayer = turnManager.getCurrentTurn().getCurrentPlayer();
        currentTurn.checkEnoughActions();

        ItemDeployCommand deployer = new ThrowingItemDeployCommand(currentPlayer.getCurrentSquare(), direction, turnManager);
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
