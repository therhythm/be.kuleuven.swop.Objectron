package swop_project;

import be.kuleuven.swop.objectron.controller.GameController;
import be.kuleuven.swop.objectron.model.Inventory;
import be.kuleuven.swop.objectron.model.Item;
import be.kuleuven.swop.objectron.model.Player;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

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
    private Player player;
    private Inventory inventory;
    private Item item;

    @Before
    public void setUp() throws Exception
    {

    }

    @Test
    public void showInventoryTest()
    {
        Item[] inventoryItems = controller.showInventory();

        Assert.assertTrue(inventoryItems.length > 0);
    }

    @Test(expected = InventoryEmptyException.class)
    public void showInventoryEmptyTest()
    {
        Item[] inventoryItems = controller.showInventory();
    }

    @Test
    public void selectItemTest()
    {
        Boolean itemSelected = controller.selectItem(0);

        Assert.assertTrue(itemSelected);
    }

    @Test
    public void useItemTest()
    {
        int initialAvailableActions = player.getAvailableActions();
        int initialNumberOfItemsInInventory = inventory.getNumberOfItems();

        controller.useCurrentItem();

        Assert.assertEquals(initialAvailableActions - 1, player.getAvailableActions());
        Assert.assertEquals(initialNumberOfItemsInInventory-1, inventory.getNumberOfItems());
        //TODO check item effect on current square
    }

    @Test
    public void cancelItemUsageTest()
    {
        int initialAvailableActions = player.getAvailableActions();
        int initialNumberOfItemsInInventory = inventory.getNumberOfItems();

        controller.cancelItemUsage();

        Assert.assertEquals(initialAvailableActions, player.getAvailableActions());
        Assert.assertEquals(initialNumberOfItemsInInventory, inventory.getNumberOfItems());
        //TODO check no item effect on current square
    }
}
