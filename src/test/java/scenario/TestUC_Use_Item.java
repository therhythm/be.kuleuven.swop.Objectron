package scenario;

import be.kuleuven.swop.objectron.GameState;
import be.kuleuven.swop.objectron.controller.GameController;
import be.kuleuven.swop.objectron.model.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Scenario test for UC Use Item
 * @author : Kasper Vervaecke
 *         Date: 22/02/13
 *         Time: 15:59
 */
public class TestUC_Use_Item
{
    private GameController controller;
    private PlayerImpl player;
    private Item item;

    @Before
    public void setUp() throws Exception
    {
        Square square = new Square();
        player = new PlayerImpl("p1", square);

        GameState stateMock = mock(GameState.class);
        when(stateMock.getCurrentPlayer()).thenReturn(player);

        controller = new GameController(stateMock);

        item = new LightMine();

    }

    @Test(expected = InventoryEmptyException.class)
    public void showInventoryEmptyTest() throws InventoryEmptyException {
        controller.showInventory();
    }

    @Test
    public void showInventoryTest() throws InventoryFullException, InventoryEmptyException, NotEnoughActionsException {
        player.addToInventory(item);

        List<Item> inventoryItems = controller.showInventory();

        assertFalse(inventoryItems.isEmpty());
        assertEquals(player.getInventoryItems(), inventoryItems);
    }

    @Test
    public void selectItemTest() throws InventoryFullException, NotEnoughActionsException {
        player.addToInventory(item);

        controller.selectItemFromInventory(0);

        assertNotNull(player.getCurrentlySelectedItem());
        assertEquals(item, player.getCurrentlySelectedItem());
    }

    @Test
    public void useItemTest() throws InventoryFullException, SquareOccupiedException, NotEnoughActionsException {
        player.addToInventory(item);

        int initialAvailableActions = player.getAvailableActions();
        int initialNumberOfItemsInInventory = player.getInventoryItems().size();

        controller.selectItemFromInventory(0);
        controller.useCurrentItem();

        assertEquals(initialAvailableActions - 1, player.getAvailableActions());
        assertEquals(initialNumberOfItemsInInventory - 1, player.getInventoryItems().size());
        assertTrue(player.getCurrentSquare().hasActiveItem());
        assertEquals(item, player.getCurrentSquare().getActiveItem());
    }

    @Test(expected = SquareOccupiedException.class)
    public void showSquareOccupiedTest() throws SquareOccupiedException, InventoryFullException, NotEnoughActionsException {
        player.getCurrentSquare().setActiveItem(item);
        player.addToInventory(item);

        controller.selectItemFromInventory(0);
        controller.useCurrentItem();
    }

    @Test
    public void cancelItemUsageTest() throws InventoryFullException, NotEnoughActionsException {
        player.addToInventory(item);

        int initialAvailableActions = player.getAvailableActions();
        int initialNumberOfItemsInInventory = player.getInventoryItems().size();

        controller.selectItemFromInventory(0);
        controller.cancelItemUsage();

        assertEquals(initialAvailableActions, player.getAvailableActions());
        assertEquals(initialNumberOfItemsInInventory, player.getInventoryItems().size());
        assertFalse(player.getCurrentSquare().hasActiveItem());
    }
}
