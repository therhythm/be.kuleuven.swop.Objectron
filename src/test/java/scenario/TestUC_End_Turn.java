package scenario;


import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.handler.EndTurnHandler;
import be.kuleuven.swop.objectron.handler.MovePlayerHandler;
import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.GameOverException;
import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;


public class TestUC_End_Turn {

    private EndTurnHandler endTurnHandler;
    private MovePlayerHandler movePlayerHandler;
    private GameState state;

    @Before
    public void setUp() throws GridTooSmallException {
        state = new GameState("jos", "piet", 10, 10);
        endTurnHandler = new EndTurnHandler(state);
        movePlayerHandler = new MovePlayerHandler(state);
    }

    @Test(expected = GameOverException.class)
    public void test_end_turn_no_moves() throws GameOverException {
        endTurnHandler.endTurn();
    }

    @Test
    public void test_end_turn_with_move() throws InvalidMoveException, GameOverException, NotEnoughActionsException {
        Player oldPlayer = state.getCurrentPlayer();
        movePlayerHandler.move(Direction.UP);
        endTurnHandler.endTurn();
        assertFalse(state.getCurrentPlayer().equals(oldPlayer));

    }
}
