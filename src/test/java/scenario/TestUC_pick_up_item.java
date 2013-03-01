package scenario;

import be.kuleuven.swop.objectron.GameState;
import be.kuleuven.swop.objectron.controller.GameController;
import be.kuleuven.swop.objectron.model.*;
import be.kuleuven.swop.objectron.model.item.Item;
import be.kuleuven.swop.objectron.model.item.LightMine;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import static org.junit.Assert.*;

import static org.mockito.Mockito.*;
/**
 * Created with IntelliJ IDEA.
 * User: Peter
 * Date: 25/02/13
 * Time: 19:40
 * To change this template use File | Settings | File Templates.
 */
public class TestUC_pick_up_item {
    private GameController gameController;
    private Player player;
    private Square currentSquare;

    @Before
    public void setUp(){
        currentSquare = new Square();
        player = new PlayerImpl("p1", currentSquare);

        GameState stateMock = mock(GameState.class);
        when(stateMock.getCurrentPlayer()).thenReturn(player);

        gameController = new GameController(stateMock);
    }

    @Test
    public void  test_basic_flow() throws InventoryFullException, NotEnoughActionsException {
        // fixture
        Item i1 = new LightMine();
        currentSquare.addItem(i1);

        //retrieve items on currentsquare
        List<Item> items =  gameController.getAvailableItems();
        assertTrue(items.size() == 1);

        //pick up the item
        int selectedItemId = 0;
        gameController.pickUpItem(selectedItemId);
        assertTrue(player.getInventoryItems().contains(i1));
    }

    @Test(expected = IllegalStateException.class)
    public void  test_no_items_on_square(){
        assertTrue(currentSquare.getAvailableItems().size()==0);
        gameController.getAvailableItems();
    }

    @Test(expected = InventoryFullException.class)
    public void  test_player_inventory_full() throws InventoryFullException, NotEnoughActionsException {
        currentSquare.addItem(mock(LightMine.class));
        assertTrue(currentSquare.getAvailableItems().size() != 0);

        for(int i = 0;i<6;i++){
            player.addToInventory(mock(LightMine.class));
        }

        gameController.getAvailableItems();
        gameController.pickUpItem(0);
    }

    @Test(expected = NotEnoughActionsException.class)
    public void test_no_more_actions() throws InventoryFullException, NotEnoughActionsException, IllegalStateException{
        for(int i=0; i<4; i++){
            currentSquare.addItem(mock(LightMine.class));
        }

        gameController.pickUpItem(0);
        gameController.pickUpItem(0);
        gameController.pickUpItem(0);
        gameController.pickUpItem(0);
    }

}
