package scenario;

import be.kuleuven.swop.objectron.GameState;
import be.kuleuven.swop.objectron.controller.GameController;
import be.kuleuven.swop.objectron.model.*;
import org.junit.Before;

import java.util.ArrayList;
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
        player = new HumanPlayer("p1", currentSquare);

        GameState stateMock = mock(GameState.class);
        when(stateMock.getCurrentPlayer()).thenReturn(player);

        gameController = new GameController(stateMock);
    }

    @org.junit.Test
    public void  test_basic_flow(){
        // fixture
        Item i1 = new LightMine();
        currentSquare.addItem(i1);

        //retrieve items on currentsquare
        List<Item> items =  gameController.getAvailableItems();
        assertTrue(items.size() == 1);

        //pick up the item
        int selectedItemId = 0;
        gameController.selectItem(selectedItemId);
        assertTrue(player.getInventoryItems().contains(i1));
    }

    @org.junit.Test(expected = IllegalStateException.class)
    public void  test_no_items_on_square(){
        assertTrue(currentSquare.getAvailableItems().size()==0);
        gameController.getAvailableItems();
    }

    @org.junit.Test(expected = IllegalStateException.class)
    public void  test_player_inventory_full(){
        currentSquare.addItem(mock(LightMine.class));
        assertTrue(currentSquare.getAvailableItems().size() != 0);

        for(int i = 0;i<6;i++){
            player.addToInventory(mock(LightMine.class));
        }

        assertTrue(player.isInventoryFull());
        gameController.getAvailableItems();
    }

}
