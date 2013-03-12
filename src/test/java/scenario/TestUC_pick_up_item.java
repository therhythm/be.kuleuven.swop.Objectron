package scenario;

import be.kuleuven.swop.objectron.GameState;
import be.kuleuven.swop.objectron.handler.PickUpItemHandler;
import be.kuleuven.swop.objectron.model.Player;
import be.kuleuven.swop.objectron.model.Square;
import be.kuleuven.swop.objectron.model.exception.InventoryFullException;
import be.kuleuven.swop.objectron.model.exception.NotEnoughActionsException;
import be.kuleuven.swop.objectron.model.exception.SquareEmptyException;
import be.kuleuven.swop.objectron.model.item.Item;
import be.kuleuven.swop.objectron.model.item.LightMine;
import be.kuleuven.swop.objectron.model.item.NullItem;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

    @Before
    public void setUp() {
        currentSquare = new Square(0, 0);
        player = new Player("p1", currentSquare);

        GameState stateMock = mock(GameState.class);
        when(stateMock.getCurrentPlayer()).thenReturn(player);

        pickUpItemHandler = new PickUpItemHandler(stateMock);
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
    public void test_player_inventory_full() throws InventoryFullException, NotEnoughActionsException, SquareEmptyException {
        currentSquare.addItem(new NullItem());
        assertTrue(currentSquare.getAvailableItems().size() != 0);

        for (int i = 0; i < 6; i++) {
            player.addToInventory(new NullItem());
            player.endTurn();//keeps remaining actions up
        }

        pickUpItemHandler.getAvailableItems();
        pickUpItemHandler.pickUpItem(0);
    }

    @Test(expected = NotEnoughActionsException.class)
    public void test_no_more_actions() throws InventoryFullException, NotEnoughActionsException, IllegalStateException {
        for (int i = 0; i < 4; i++) {
            currentSquare.addItem(new NullItem());
        }

        pickUpItemHandler.pickUpItem(0);
        pickUpItemHandler.pickUpItem(0);
        pickUpItemHandler.pickUpItem(0);
        pickUpItemHandler.pickUpItem(0);
    }

}
