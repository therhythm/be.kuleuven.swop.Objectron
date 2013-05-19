package be.kuleuven.swop.objectron.domain;

import be.kuleuven.swop.objectron.domain.exception.InventoryFullException;
import be.kuleuven.swop.objectron.domain.exception.TooManyItemsOfSameTypeException;
import be.kuleuven.swop.objectron.domain.item.Flag;
import be.kuleuven.swop.objectron.domain.square.Square;
import org.junit.Test;

import static org.mockito.Mockito.mock;

/**
 * Created with IntelliJ IDEA.
 * User: Nik
 * Date: 5/14/13
 * Time: 6:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestInventory {

    @Test (expected = TooManyItemsOfSameTypeException.class)
    public void test_item_max_in_bag() throws InventoryFullException, TooManyItemsOfSameTypeException {
        Inventory inventory = new Inventory();
        inventory.addItem(new Flag(mock(Player.class), mock(Square.class)));
        inventory.addItem(new Flag(mock(Player.class), mock(Square.class)));
    }
}
