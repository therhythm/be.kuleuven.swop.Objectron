package be.kuleuven.swop.objectron.domain.gamestate;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.exception.GameOverException;
import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.grid.GridFactory;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.domain.util.Position;
import be.kuleuven.swop.objectron.handler.MovePlayerHandler;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Nik
 * Date: 4/12/13
 * Time: 1:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestGameState {

    GameState state;

    @Before
    public void setUp() throws GridTooSmallException {
        Position p1Pos = new Position(0, 0);
        Position p2Pos = new Position(1, 0);
        Grid grid = GridFactory.gridWithoutWallsItemsPowerFailures(new Dimension(10, 10), p1Pos, p2Pos);
        state = new GameState("p1", "p2", p1Pos, p2Pos, grid);

    }

    @Test(expected = GameOverException.class)
    public void test_win() throws GameOverException, NotEnoughActionsException, InvalidMoveException {
        MovePlayerHandler handler = new MovePlayerHandler(state);
        TurnManager manager = state.getTurnManager();
        handler.move(Direction.DOWN);
        handler.move(Direction.DOWN);
        handler.move(Direction.DOWN);
        manager.endTurn();
        manager.endTurn();
        handler.move(Direction.DOWN);
        manager.endTurn();
        Square sq = state.getGrid().getSquareAtPosition(new Position(0, 0));
        manager.getCurrentTurn().getCurrentPlayer().move(sq, manager);

        assertTrue(state.checkWin());
    }
}
