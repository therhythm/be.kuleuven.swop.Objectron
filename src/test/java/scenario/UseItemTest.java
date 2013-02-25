package scenario;

import be.kuleuven.swop.objectron.controller.GameController;
import be.kuleuven.swop.objectron.model.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Scenario test for UC Use Item
 * @author : Kasper Vervaecke
 *         Date: 22/02/13
 *         Time: 15:59
 */
public class UseItemTest
{
    private GameController controller;
    private HumanPlayer player;
    private KeyValueInventory inventory;
    private Item item;

    @Before
    public void setUp() throws Exception
    {
        controller = new GameController();
        player = new HumanPlayer();
        inventory = new KeyValueInventory();
        //TODO: finish fixture
    }

    @Test
    public void showInventoryTest()
    {
        List<Item> inventoryItems = controller.showInventory();

        assertFalse(inventoryItems.isEmpty());
        assertEquals(inventory.getItems(),inventoryItems);
    }

    @Test(expected = InventoryEmptyException.class)
    public void showInventoryEmptyTest()
    {
        List<Item> inventoryItems = controller.showInventory();
    }

    @Test
    public void selectItemTest()
    {
        controller.selectItem(0);

        assertNotNull(player.getCurrentlySelectedItem());
        assertEquals(item, player.getCurrentlySelectedItem());
    }

    @Test
    public void useItemTest()
    {
        int initialAvailableActions = player.getAvailableActions();
        int initialNumberOfItemsInInventory = inventory.getItems().size();

        controller.useCurrentItem();

        assertEquals(initialAvailableActions - 1, player.getAvailableActions());
        assertEquals(initialNumberOfItemsInInventory-1, inventory.getItems().size());
        //TODO check item effect on current square
    }

    @Test
    public void cancelItemUsageTest()
    {
        int initialAvailableActions = player.getAvailableActions();
        int initialNumberOfItemsInInventory = inventory.getItems().size();

        controller.cancelItemUsage();

        assertEquals(initialAvailableActions, player.getAvailableActions());
        assertEquals(initialNumberOfItemsInInventory, inventory.getItems().size());
        //TODO check no item effect on current square
    }
}
