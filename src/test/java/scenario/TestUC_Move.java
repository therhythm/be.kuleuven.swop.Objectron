package scenario;


import be.kuleuven.swop.objectron.domain.*;

import be.kuleuven.swop.objectron.domain.exception.GameOverException;
import be.kuleuven.swop.objectron.domain.gamestate.GameStateImpl;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.handler.MovePlayerHandler;
import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException;
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
    private  Player player2;



    @Before
    public void setUp() throws GridTooSmallException {
        int horizontalPositionPlayer1  =0;
        int verticalPositionPlayer1 = 9;
        int horizontalPositionPlayer2  =0;
        int verticalPositionPlayer2 = 5;

        GridFactoryImpl gridFactory = new GridFactoryImpl(10,10);
       gridFactory.buildGrid(horizontalPositionPlayer1,verticalPositionPlayer1,horizontalPositionPlayer2,verticalPositionPlayer2);
        Grid grid = gridFactory.getGameGrid();


        Square square1 = grid.getSquareAtPosition(verticalPositionPlayer1,horizontalPositionPlayer1);
        Square square2 = grid.getSquareAtPosition(verticalPositionPlayer2,horizontalPositionPlayer2);

        player1 = new Player("p1", square1);
        player2 = new Player("p1", square2);


        GameStateImpl stateMock = mock(GameStateImpl.class);
        when(stateMock.getCurrentPlayer()).thenReturn(player1);
        when(stateMock.getGrid()).thenReturn(grid);

        movePlayerHandler = new MovePlayerHandler(stateMock);
       // endTurnHandler = new EndTurnHandler(stateMock);
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
        player1.reduceRemainingActions(3);
        movePlayerHandler.move(Direction.UP);
    }
}
