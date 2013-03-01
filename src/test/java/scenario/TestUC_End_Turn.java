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



public class TestUC_End_Turn {

    private GameController controller;
    private GameState state;

    @Before
    public void setUp() throws Exception
    {
        state = new GameState("jos", "piet", 10, 10);
        controller = new GameController(state);
    }

    @Test(expected = GameOverException.class)
    public void test_end_turn_no_moves() throws GameOverException {
        controller.endTurn();
    }

    @Test
    public void test_end_turn_with_move() throws InvalidMoveException, GameOverException, NotEnoughActionsException {
        Player oldPlayer = state.getCurrentPlayer();
        controller.move(Direction.UP);
        controller.endTurn();
        assertFalse(state.getCurrentPlayer().equals(oldPlayer));

    }
}
