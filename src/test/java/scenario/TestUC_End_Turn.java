package scenario;


import be.kuleuven.swop.objectron.GameState;
import be.kuleuven.swop.objectron.controller.GameController;
import be.kuleuven.swop.objectron.gui.GameView;
import be.kuleuven.swop.objectron.model.*;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class TestUC_End_Turn {

    private GameController controller;
    private Player player;
    private GameState state;

    @Before
    public void setUp() throws Exception
    {
        GameState state = new GameState("jos", "piet", 10, 10);
        GameController controller = new GameController(state);

    }

    @Test(expected = GameOverException.class)
    private void test_end_turn_no_moves(){
        controller.endTurn();
    }

    @Test
    private void test_end_turn_with_move(){
        Player oldPlayer = state.getCurrentPlayer();
        controller.move(Direction.UP);
        controller.endTurn();
        assertFalse(state.getCurrentPlayer().equals(oldPlayer));

    }
}
