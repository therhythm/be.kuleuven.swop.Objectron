package scenario;


import be.kuleuven.swop.objectron.GameState;
import be.kuleuven.swop.objectron.controller.GameController;
import be.kuleuven.swop.objectron.model.*;
import be.kuleuven.swop.objectron.model.exception.GameOverException;
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
    private Player player1;
    private Player player2;


    @Before
    public void setUp(){
        Grid grid = new Grid(10, 10);
        Square square1 = grid.getSquareAtPosition(9,0);
        Square square2 = grid.getSquareAtPosition(6,0);
        player1 = new PlayerImpl("p1", square1);
        player2 = new PlayerImpl("p2", square2);


        GameState stateMock = mock(GameState.class);
        when(stateMock.getCurrentPlayer()).thenReturn(player1);
        when(stateMock.getGrid()).thenReturn(grid);

        controller = new GameController(stateMock);
    }

    @Test
    public void test_main_flow() throws InvalidMoveException, NotEnoughActionsException {
        Square prev = player1.getCurrentSquare();

        controller.move(Direction.UP);

        assertNotSame(prev, player1.getCurrentSquare());
        assertTrue(player1.getCurrentSquare().isObstructed());
        assertTrue(prev.isObstructed());


    }

    @Test(expected = InvalidMoveException.class)
    public void test_lightTrail() throws InvalidMoveException, NotEnoughActionsException {
        Square prev = player1.getCurrentSquare();
        try {
            System.out.println("position current square: " + player1.getCurrentSquare());
        controller.move(Direction.UP);
            System.out.println("position current square: " + player1.getCurrentSquare());
        controller.move(Direction.UP);
            System.out.println("position current square: " + player1.getCurrentSquare());
        controller.move(Direction.UP_RIGHT);
            System.out.println("position current square: " + player1.getCurrentSquare());

            controller.endTurn();
        } catch (GameOverException e) {
            fail();
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        System.out.println("player 2");
        System.out.println("position current square: " + player2.getCurrentSquare());
        controller.move(Direction.DOWN_RIGHT);

        System.out.println("position current square: " + player2.getCurrentSquare());

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
