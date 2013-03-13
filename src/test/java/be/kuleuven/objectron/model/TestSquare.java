package be.kuleuven.objectron.model;

import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.Square;
import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.item.LightMine;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 13/03/13
 * Time: 21:26
 * To change this template use File | Settings | File Templates.
 */
public class TestSquare {
    private Square square;
    private Player player;

    @Before
    public void setUp()throws GridTooSmallException {
          square = new Square(5,5);
        square.addItem(new LightMine());
        player = new Player("test",new Square(5,4));
    }

    @Test
    public void test_step_on_active_item(){
        assertTrue(square.hasActiveItem());
        square.stepOn(player);
        assertFalse(square.hasActiveItem());

    }
}
