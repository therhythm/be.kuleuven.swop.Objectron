package scenario;

import be.kuleuven.swop.objectron.GameStateImpl;
import be.kuleuven.swop.objectron.handler.UseItemHandler;
import be.kuleuven.swop.objectron.model.Player;
import be.kuleuven.swop.objectron.model.Square;
import be.kuleuven.swop.objectron.model.exception.*;
import be.kuleuven.swop.objectron.model.item.Item;
import be.kuleuven.swop.objectron.model.item.LightMine;
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
        player = new Player("p1", square);

        GameStateImpl stateMock = mock(GameStateImpl.class);
        when(stateMock.getCurrentPlayer()).thenReturn(player);

        useItemHandler = new UseItemHandler(stateMock);

        item = new LightMine();

    }

    @Test(expected = InventoryEmptyException.class)
    public void showInventoryEmptyTest() throws InventoryEmptyException {
        useItemHandler.showInventory();
    }

    @Test
    public void showInventoryTest() throws InventoryFullException, InventoryEmptyException, NotEnoughActionsException {
        player.addToInventory(item);

        List<Item> inventoryItems = useItemHandler.showInventory();

        assertFalse(inventoryItems.isEmpty());
        assertEquals(player.getInventoryItems(), inventoryItems);
    }

    @Test
    public void selectItemTest() throws InventoryFullException, NotEnoughActionsException {
        player.addToInventory(item);

        useItemHandler.selectItemFromInventory(0);

        assertNotNull(player.getCurrentlySelectedItem());
        assertEquals(item, player.getCurrentlySelectedItem());
    }

    @Test
    public void useItemTest() throws InventoryFullException, SquareOccupiedException, NotEnoughActionsException {
        player.addToInventory(item);

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
        player.addToInventory(item);

        useItemHandler.selectItemFromInventory(0);
        useItemHandler.useCurrentItem();
    }

    @Test
    public void cancelItemUsageTest() throws InventoryFullException, NotEnoughActionsException {
        player.addToInventory(item);

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
        player.addToInventory(new LightMine());
        player.addToInventory(new LightMine());
        player.addToInventory(new LightMine());
        useItemHandler.selectItemFromInventory(0);
        useItemHandler.useCurrentItem();
    }
}
