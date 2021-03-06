package scenario;


import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.*;
import be.kuleuven.swop.objectron.domain.gamestate.Game;
import be.kuleuven.swop.objectron.domain.gamestate.GameObjectMother;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.domain.util.Position;
import be.kuleuven.swop.objectron.handler.EndTurnHandler;
import be.kuleuven.swop.objectron.handler.MovePlayerHandler;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;


public class TestUC_End_Turn {

    private EndTurnHandler endTurnHandler;
    private MovePlayerHandler movePlayerHandler;
    private Game state;

    @Before
    public void setUp() throws GridTooSmallException, NumberOfPlayersException {
        Dimension dimension = new Dimension(10, 10);

        List<Position> positions = new ArrayList<>();
        positions.add(new Position(0, 9));
        positions.add(new Position(9, 0));

        List<String> playerNames = new ArrayList<>();
        playerNames.add("jos");
        playerNames.add("piet");

        state = GameObjectMother.raceGameWithoutWalls(dimension, playerNames, positions);

        endTurnHandler = new EndTurnHandler(state);
        movePlayerHandler = new MovePlayerHandler(state);
    }

    @Test(expected = GameOverException.class)
    public void test_end_turn_no_moves() throws GameOverException {
        endTurnHandler.endTurn();
    }

    @Test
    public void test_end_turn_with_move() throws InvalidMoveException, GameOverException, NotEnoughActionsException,
            SquareOccupiedException {
        Player oldPlayer = state.getTurnManager().getCurrentTurn().getCurrentPlayer();
        movePlayerHandler.move(Direction.UP);
        endTurnHandler.endTurn();
        assertFalse(state.getTurnManager().getCurrentTurn().getCurrentPlayer().equals(oldPlayer));

    }
}
