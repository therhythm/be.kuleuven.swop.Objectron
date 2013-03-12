package scenario;


import be.kuleuven.swop.objectron.GameStateImpl;
import be.kuleuven.swop.objectron.handler.EndTurnHandler;
import be.kuleuven.swop.objectron.handler.MovePlayerHandler;
import be.kuleuven.swop.objectron.model.Direction;
import be.kuleuven.swop.objectron.model.Grid;
import be.kuleuven.swop.objectron.model.Player;
import be.kuleuven.swop.objectron.model.Square;
import be.kuleuven.swop.objectron.model.exception.GameOverException;
import be.kuleuven.swop.objectron.model.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.model.exception.NotEnoughActionsException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * @author : Nik Torfs
 *         Date: 22/02/13
 *         Time: 07:14
 */
public class TestUC_Move {

    private MovePlayerHandler movePlayerHandler;
    //private EndTurnHandler endTurnHandler;

    private Player player1;



    @Before
    public void setUp() {
        Grid grid = new Grid(10, 10);
        Square square1 = grid.getSquareAtPosition(9,0);

        player1 = new Player("p1", square1);


        GameStateImpl stateMock = mock(GameStateImpl.class);
        when(stateMock.getCurrentPlayer()).thenReturn(player1);
        when(stateMock.getGrid()).thenReturn(grid);

        movePlayerHandler = new MovePlayerHandler(stateMock);
       // endTurnHandler = new EndTurnHandler(stateMock);
    }

    @Test
    public void test_main_flow() throws InvalidMoveException, NotEnoughActionsException {
        Square prev = player1.getCurrentSquare();

        movePlayerHandler.move(Direction.UP);

        assertNotSame(prev, player1.getCurrentSquare());
        assertTrue(player1.getCurrentSquare().isObstructed());
        assertTrue(prev.isObstructed());
    }

    @Test(expected = InvalidMoveException.class)
    public void test_wrong_positioning() throws InvalidMoveException, NotEnoughActionsException {
        movePlayerHandler.move(Direction.DOWN);
    }

    @Test(expected = NotEnoughActionsException.class)
    public void test_no_more_actions() throws InvalidMoveException, NotEnoughActionsException {
        movePlayerHandler.move(Direction.UP);
        movePlayerHandler.move(Direction.UP);
        movePlayerHandler.move(Direction.UP);
        movePlayerHandler.move(Direction.UP);
    }
}
