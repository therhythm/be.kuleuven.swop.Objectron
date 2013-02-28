package be.kuleuven.swop.objectron.model;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 27/02/13
 * Time: 20:21
 * To change this template use File | Settings | File Templates.
 */
public class Test_Inventory {
    private Player player1;
    @Before
    public void setUp(){
        player1 = new HumanPlayer("p1", mock(Square.class));
        player1.addToInventory(new LightMine());
    }

    @Test
    public void  test_getItems(){
        //TODO
    }

    @Test
    public void test_retrieveItem(){
        //TODO
    }

}
