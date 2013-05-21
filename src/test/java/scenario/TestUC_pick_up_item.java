package scenario;

import be.kuleuven.swop.objectron.domain.Inventory;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.exception.InventoryFullException;
import be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException;
import be.kuleuven.swop.objectron.domain.exception.SquareEmptyException;
import be.kuleuven.swop.objectron.domain.gamestate.Game;
import be.kuleuven.swop.objectron.domain.gamestate.GameObjectMother;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.item.LightMine;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.domain.util.Position;
import be.kuleuven.swop.objectron.handler.PickUpItemHandler;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Peter
 * Date: 25/02/13
 * Time: 19:40
 * To change this template use File | Settings | File Templates.
 */
public class TestUC_pick_up_item {
    private PickUpItemHandler pickUpItemHandler;
    private Player player;
    private Square currentSquare;
    private Game gameState;

    @Before
    public void setUp() throws GridTooSmallException {
        Dimension dimension = new Dimension(10, 10);

        List<Position> positions = new ArrayList<>();
        positions.add(new Position(0, 9));
        positions.add(new Position(9, 0));

        List<String> playerNames = new ArrayList<>();
        playerNames.add("p1");
        playerNames.add("p2");

        gameState = GameObjectMother.raceGameWithoutItems(dimension, playerNames, positions);

        pickUpItemHandler = new PickUpItemHandler(gameState);
        player = gameState.getTurnManager().getCurrentTurn().getCurrentPlayer();
        currentSquare = player.getCurrentSquare();

        pickUpItemHandler = new PickUpItemHandler(gameState);
    }

    @Test
    public void test_basic_flow() throws InventoryFullException, NotEnoughActionsException, SquareEmptyException {
        // fixture
        Item i1 = new LightMine();
        currentSquare.addItem(i1);

        //retrieve items on currentsquare
        List<Item> items = pickUpItemHandler.getAvailableItems();
        assertTrue(items.size() == 1);

        //pick up the item
        int selectedItemId = 0;
        pickUpItemHandler.pickUpItem(selectedItemId);
        assertTrue(player.getInventoryItems().contains(i1));
    }

    @Test(expected = SquareEmptyException.class)
    public void test_no_items_on_square() throws SquareEmptyException, NotEnoughActionsException {
        assertTrue(currentSquare.getAvailableItems().size() == 0);
        pickUpItemHandler.getAvailableItems();
    }

    @Test(expected = InventoryFullException.class)
    public void test_player_inventory_full() throws InventoryFullException, NotEnoughActionsException,
            SquareEmptyException {
        currentSquare.addItem(new LightMine());
        assertTrue(currentSquare.getAvailableItems().size() != 0);

        for (int i = 0; i < Inventory.INVENTORY_LIMIT; i++) {
            player.pickupItem(0);
            currentSquare.addItem(new LightMine());
            gameState.getTurnManager().getCurrentTurn().setMoved();
            gameState.getTurnManager().endTurn();
            gameState.getTurnManager().getCurrentTurn().setMoved();
            gameState.getTurnManager().endTurn();
        }

        pickUpItemHandler.getAvailableItems();
        pickUpItemHandler.pickUpItem(0);
    }

    @Test(expected = NotEnoughActionsException.class)
    public void test_no_more_actions() throws InventoryFullException, NotEnoughActionsException, IllegalStateException {
        for (int i = 0; i <= Turn.ACTIONS_EACH_TURN; i++) {
            currentSquare.addItem(new LightMine());
            pickUpItemHandler.pickUpItem(0);
        }
    }

    @Test(expected = NotEnoughActionsException.class)
    public void test_getAvailableItems_notEnoughActions() throws InventoryFullException, NotEnoughActionsException,
            SquareEmptyException {

        for (int i = 0; i < Turn.ACTIONS_EACH_TURN; i++) {
            currentSquare.addItem(new LightMine());
        }

        for (int i = 0; i < Turn.ACTIONS_EACH_TURN; i++) {
            pickUpItemHandler.pickUpItem(0);
        }

        pickUpItemHandler.getAvailableItems();
    }

}
