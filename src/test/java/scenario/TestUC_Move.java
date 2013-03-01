package scenario;


import be.kuleuven.swop.objectron.GameState;
import be.kuleuven.swop.objectron.controller.GameController;
import be.kuleuven.swop.objectron.model.*;
import be.kuleuven.swop.objectron.model.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.model.exception.NotEnoughActionsException;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * @author : Nik Torfs
 *         Date: 22/02/13
 *         Time: 07:14
 */
public class TestUC_Move {

    private GameController controller;
    private Player player;


    @Before
    public void setUp(){
        Grid grid = new Grid(10, 10);
        Square square = grid.getSquareAtPosition(9,0);
        player = new PlayerImpl("p1", square);

        GameState stateMock = mock(GameState.class);
        when(stateMock.getCurrentPlayer()).thenReturn(player);
        when(stateMock.getGrid()).thenReturn(grid);

        controller = new GameController(stateMock);
    }

    @Test
    public void test_main_flow() throws InvalidMoveException, NotEnoughActionsException {
        Square prev = player.getCurrentSquare();

        controller.move(Direction.UP);

        assertNotSame(prev, player.getCurrentSquare());
        assertTrue(player.getCurrentSquare().isObstructed());
        assertTrue(prev.isObstructed());
        //TODO lightTrail
    }

    @Test(expected = InvalidMoveException.class)
    public void test_wrong_positioning() throws InvalidMoveException, NotEnoughActionsException {
        controller.move(Direction.DOWN);
    }

    @Test(expected = NotEnoughActionsException.class)
    public void test_no_more_actions() throws InvalidMoveException, NotEnoughActionsException {
        controller.move(Direction.UP);
        controller.move(Direction.UP);
        controller.move(Direction.UP);
        controller.move(Direction.UP);
    }
}
