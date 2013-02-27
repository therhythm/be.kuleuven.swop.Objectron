package scenario;

import be.kuleuven.swop.objectron.GameState;
import be.kuleuven.swop.objectron.controller.GameController;
import be.kuleuven.swop.objectron.model.Inventory;
import be.kuleuven.swop.objectron.model.Player;
import be.kuleuven.swop.objectron.model.Square;
import be.kuleuven.swop.objectron.model.Item;
import junit.framework.Assert;
import junit.framework.Test;
import org.junit.Before;
import  org.junit.*;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: Peter
 * Date: 25/02/13
 * Time: 19:40
 * To change this template use File | Settings | File Templates.
 */
public class UC_pick_up_item {
    private GameController gameController;
    private Player player;
    private Square currentSquare;
    private Inventory inventory;
    private GameState state;

    @Before
    public void setUp(){
        state = new GameState("player1","player2",10,10);
        gameController = new GameController(state);
        currentSquare = new Square();

    }
    @org.junit.Test
    public void  test_basic_flow(){
        //Hier worden er dummy items aan de currentSquare toegevoegd, zodat die niet leeg is
        List<Item> itemsSquare = new ArrayList<Item>();


        currentSquare.setItems(itemsSquare);
        List<Item> items =  gameController.getAvailableItems();
        int selectedItemId = 0;

        gameController.selectItem(selectedItemId);
       assertTrue(inventory.getItems().contains(items.get(selectedItemId)));



    }
    @org.junit.Test(expected = IllegalArgumentException.class)
    public void  test_alternate_flow_1(){
        assertTrue(currentSquare.getAvailableItems().size()==0);
        List<Item> items =  gameController.getAvailableItems();

    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void  test_alternate_flow_2(){
        //Hier worden er dummy items aan de currentSquare toegevoegd, zodat die niet leeg is
        List<Item> itemsSquare = new ArrayList<Item>();
        assertTrue(currentSquare.getAvailableItems().size()!=0);
        for(int i = 0;i<6;i++){
            player.addToInventory(null);
        }
        assertTrue(player.isInventoryFull());
        List<Item> items =  gameController.getAvailableItems();

    }

}
