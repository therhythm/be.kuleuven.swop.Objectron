package scenario;


import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.RacePlayer;
import be.kuleuven.swop.objectron.domain.exception.*;
import be.kuleuven.swop.objectron.domain.gamestate.Game;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.grid.GeneratedGridBuilder;
import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.grid.GridBuilder;
import be.kuleuven.swop.objectron.domain.grid.GridObjectMother;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.domain.util.Position;
import be.kuleuven.swop.objectron.handler.MovePlayerHandler;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
    private Game stateMock;

    @Before
    public void setUp() throws GridTooSmallException, TooManyPlayersException {
        Position p1Pos = new Position(0, 9);
        Position p2Pos = new Position(0, 5);

        List<Position> positions = new ArrayList<>();
        positions.add(p1Pos);
        positions.add(p2Pos);

        Dimension dimension = new Dimension(10, 10);
        GridBuilder builder = new GeneratedGridBuilder(dimension, 2);
        builder.setStartingPositions(positions);
        Grid grid = GridObjectMother.gridWithoutWallsItemsPowerFailures(builder);

        player1 = new RacePlayer("p1", grid.getSquareAtPosition(p1Pos));
        Turn turn = new Turn(player1);
        stateMock = mock(Game.class);
        TurnManager turnManager = mock(TurnManager.class);
        when(turnManager.getCurrentTurn()).thenReturn(turn);

        when(stateMock.getTurnManager()).thenReturn(turnManager);
        when(stateMock.getTurnManager().getCurrentTurn()).thenReturn(turn);
        when(stateMock.getGrid()).thenReturn(grid);

        movePlayerHandler = new MovePlayerHandler(stateMock);
    }

    @Test
    public void test_main_flow() throws InvalidMoveException, NotEnoughActionsException, GameOverException,
            SquareOccupiedException {
        Square prev = player1.getCurrentSquare();

        movePlayerHandler.move(Direction.UP);

        assertNotSame(prev, player1.getCurrentSquare());
        assertTrue(player1.getCurrentSquare().isObstructed());
        assertTrue(prev.isObstructed());
    }

    @Test(expected = InvalidMoveException.class)
    public void test_wrong_positioning() throws InvalidMoveException, NotEnoughActionsException, GameOverException,
            SquareOccupiedException {
        movePlayerHandler.move(Direction.DOWN);
    }


    @Test(expected = NotEnoughActionsException.class)
    public void test_no_more_actions() throws InvalidMoveException, NotEnoughActionsException, GameOverException, SquareOccupiedException {
        stateMock.getTurnManager().getCurrentTurn().addPenalty(Turn.ACTIONS_EACH_TURN);

        movePlayerHandler.move(Direction.UP);
    }
}
