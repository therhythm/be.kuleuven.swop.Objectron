package be.kuleuven.swop.objectron.model;

import be.kuleuven.swop.objectron.GameState;
import be.kuleuven.swop.objectron.controller.GameController;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 27/02/13
 * Time: 20:21
 * To change this template use File | Settings | File Templates.
 */
public class test_Inventory {
    private Player player1;
    @Before
    public void setUp(){
        player1 = new HumanPlayer("player 1");
        player1.addToInventory(new LightGrenade());
    }

    @Test
    public void  test_getItems(){
        System.out.println("test 1");
    }

    @Test
    public void test_retrieveItem(){
        System.out.println("test 2");
    }

}
