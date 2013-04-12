package scenario;


import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.GameOverException;
import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.grid.GridFactory;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.domain.util.Position;
import be.kuleuven.swop.objectron.handler.MovePlayerHandler;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * @author : Nik Torfs
 *         Date: 22/02/13
 *         Time: 07:14
 */
public class TestUC_Move {
    private MovePlayerHandler movePlayerHandler;
    private Player player1;
    private GameState stateMock;

    @Before
    public void setUp() throws GridTooSmallException {
        Position p1Pos = new Position(0, 9);
        Position p2Pos = new Position(0, 5);
        Dimension dimension = new Dimension(10, 10);
        Grid grid = GridFactory.gridWithoutWallsPowerFailures(dimension, p1Pos, p2Pos);

        player1 = new Player("p1", grid.getSquareAtPosition(p1Pos));
        Turn turn = new Turn(player1);
        stateMock = mock(GameState.class);
        when(stateMock.getCurrentPlayer()).thenReturn(player1);
        when(stateMock.getCurrentTurn()).thenReturn(turn);
        when(stateMock.getGrid()).thenReturn(grid);

        movePlayerHandler = new MovePlayerHandler(stateMock);
    }

    @Test
    public void test_main_flow() throws InvalidMoveException, NotEnoughActionsException, GameOverException {
        Square prev = player1.getCurrentSquare();

        movePlayerHandler.move(Direction.UP);

        assertNotSame(prev, player1.getCurrentSquare());
        assertTrue(player1.getCurrentSquare().isObstructed());
        assertTrue(prev.isObstructed());
    }

    @Test(expected = InvalidMoveException.class)
    public void test_wrong_positioning() throws InvalidMoveException, NotEnoughActionsException, GameOverException {
        movePlayerHandler.move(Direction.DOWN);
    }


    @Test(expected = NotEnoughActionsException.class)
    public void test_no_more_actions() throws InvalidMoveException, NotEnoughActionsException, GameOverException {
        stateMock.getCurrentTurn().reduceRemainingActions(3);
        movePlayerHandler.move(Direction.UP);
    }
}
