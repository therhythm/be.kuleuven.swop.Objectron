package scenario;

import be.kuleuven.swop.objectron.domain.gamestate.GameStateImpl;
import be.kuleuven.swop.objectron.handler.UseItemHandler;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.exception.*;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.item.LightMine;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Scenario test for UC Use Item
 *
 * @author : Kasper Vervaecke
 *         Date: 22/02/13
 *         Time: 15:59
 */
public class TestUC_Use_Item {
    private UseItemHandler useItemHandler;
    private Player player;
    private Item item;


    @Before
    public void setUp() throws Exception {
        Square square = new Square(0, 0);
        item = new LightMine();
        square.addItem(item);
        player = new Player("p1", square);

        GameStateImpl stateMock = mock(GameStateImpl.class);
        when(stateMock.getCurrentPlayer()).thenReturn(player);

        useItemHandler = new UseItemHandler(stateMock);



    }

    @Test(expected = InventoryEmptyException.class)
    public void showInventoryEmptyTest() throws InventoryEmptyException {
        useItemHandler.showInventory();
    }

    @Test
    public void showInventoryTest() throws InventoryFullException, InventoryEmptyException, NotEnoughActionsException {
        player.pickupItem(0);

        List<Item> inventoryItems = useItemHandler.showInventory();

        assertFalse(inventoryItems.isEmpty());
        assertEquals(player.getInventoryItems(), inventoryItems);
    }

    @Test
    public void selectItemTest() throws InventoryFullException, NotEnoughActionsException {
        player.pickupItem(0);

        String selected = useItemHandler.selectItemFromInventory(0);

        assertEquals(item.getName(), selected);
    }

    @Test
    public void useItemTest() throws InventoryFullException, SquareOccupiedException, NotEnoughActionsException {
        player.pickupItem(0);

        int initialAvailableActions = player.getAvailableActions();
        int initialNumberOfItemsInInventory = player.getInventoryItems().size();

        useItemHandler.selectItemFromInventory(0);
        useItemHandler.useCurrentItem();

        assertEquals(initialAvailableActions - 1, player.getAvailableActions());
        assertEquals(initialNumberOfItemsInInventory - 1, player.getInventoryItems().size());
        assertTrue(player.getCurrentSquare().hasActiveItem());
    }

    @Test(expected = SquareOccupiedException.class)
    public void showSquareOccupiedTest() throws SquareOccupiedException, InventoryFullException, NotEnoughActionsException {
        player.getCurrentSquare().setActiveItem(item);
        player.pickupItem(0);

        useItemHandler.selectItemFromInventory(0);
        useItemHandler.useCurrentItem();
    }

    @Test
    public void cancelItemUsageTest() throws InventoryFullException, NotEnoughActionsException {
        player.pickupItem(0);

        int initialAvailableActions = player.getAvailableActions();
        int initialNumberOfItemsInInventory = player.getInventoryItems().size();

        useItemHandler.selectItemFromInventory(0);
        useItemHandler.cancelItemUsage();

        assertEquals(initialAvailableActions, player.getAvailableActions());
        assertEquals(initialNumberOfItemsInInventory, player.getInventoryItems().size());
        assertFalse(player.getCurrentSquare().hasActiveItem());
    }

    @Test(expected = NotEnoughActionsException.class)
    public void test_no_more_actions() throws NotEnoughActionsException, InvalidMoveException, InventoryFullException, SquareOccupiedException {
        player.pickupItem(0);
        player.getCurrentSquare().addItem(new LightMine());
        player.pickupItem(0);
        player.getCurrentSquare().addItem(new LightMine());
        player.pickupItem(0);
        useItemHandler.selectItemFromInventory(0);
        useItemHandler.useCurrentItem();
    }
}
