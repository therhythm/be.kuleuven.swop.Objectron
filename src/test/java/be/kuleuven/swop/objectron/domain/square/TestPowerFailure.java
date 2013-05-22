package be.kuleuven.swop.objectron.domain.square;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.effect.powerfailure.PrimaryPowerFailure;
import be.kuleuven.swop.objectron.domain.effect.powerfailure.SecondaryPowerFailure;
import be.kuleuven.swop.objectron.domain.effect.powerfailure.TertiaryPowerFailure;
import be.kuleuven.swop.objectron.domain.exception.*;
import be.kuleuven.swop.objectron.domain.gamestate.Game;
import be.kuleuven.swop.objectron.domain.gamestate.GameObjectMother;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.domain.util.Position;
import be.kuleuven.swop.objectron.handler.MovePlayerHandler;
import be.kuleuven.swop.objectron.viewmodel.SquareViewModel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 13/03/13
 * Time: 21:26
 * To change this template use File | Settings | File Templates.
 */
public class TestPowerFailure {
    private Player player2;
    private Game gamestate;
    private Grid grid;
    private MovePlayerHandler movePlayerHandler;

    @Before
    public void setUp() throws GridTooSmallException, SquareOccupiedException, TooManyPlayersException {
        Position p1Pos = new Position(5, 4);
        Position p2Pos = new Position(5, 3);

        List<Position> positions = new ArrayList<>();
        positions.add(p1Pos);
        positions.add(p2Pos);

        List<String> playerNames = new ArrayList<>();
        playerNames.add("p1");
        playerNames.add("p2");

        Dimension dimension = new Dimension(10, 10);
        gamestate = GameObjectMother.raceGameWithoutWallsItemsPowerFailures(dimension, playerNames, positions);
        grid = gamestate.getGrid();

        TurnManager turnManager = gamestate.getTurnManager();
        turnManager.getCurrentTurn().setMoved();
        turnManager.endTurn();
        player2 = turnManager.getCurrentTurn().getCurrentPlayer();
        movePlayerHandler = new MovePlayerHandler(gamestate);
    }

    @Test
    public void test_stepon_primary() throws GameOverException, NotEnoughActionsException, InvalidMoveException,
            SquareOccupiedException {
        Square otherSquare = grid.getSquareAtPosition(new Position(5, 2));
        new PrimaryPowerFailure(otherSquare, gamestate.getTurnManager());
        movePlayerHandler.move(Direction.UP);
        assertNotEquals(player2, gamestate.getTurnManager().getCurrentTurn().getCurrentPlayer());
    }

    @Test
    public void test_stepon_secondary() throws GameOverException, NotEnoughActionsException, InvalidMoveException,
            SquareOccupiedException {
        Square otherSquare = grid.getSquareAtPosition(new Position(5, 2));
        new SecondaryPowerFailure(otherSquare, Direction.UP, gamestate.getTurnManager());
        movePlayerHandler.move(Direction.UP);
        assertNotEquals(player2, gamestate.getTurnManager().getCurrentTurn().getCurrentPlayer());
    }

    @Test
    public void test_stepon_tertiary() throws GameOverException, NotEnoughActionsException, InvalidMoveException,
            SquareOccupiedException {
        Square otherSquare = grid.getSquareAtPosition(new Position(5, 2));
        new TertiaryPowerFailure(otherSquare, gamestate.getTurnManager());
        movePlayerHandler.move(Direction.UP);
        assertNotEquals(player2, gamestate.getTurnManager().getCurrentTurn().getCurrentPlayer());
    }

    @Test
    public void test_action_loss_unpowered_primary() {
        Square square = grid.getSquareAtPosition(new Position(5, 4));
        new PrimaryPowerFailure(square, gamestate.getTurnManager());
        gamestate.getTurnManager().getCurrentTurn().setMoved();
        gamestate.getTurnManager().endTurn();
        assertEquals(Turn.ACTIONS_EACH_TURN - 1, gamestate.getTurnManager().getCurrentTurn().getActionsRemaining());
    }

    @Test
    public void test_action_loss_unpowered_secondary() {
        Square square = grid.getSquareAtPosition(new Position(5, 4));
        new SecondaryPowerFailure(square, Direction.UP, gamestate.getTurnManager());
        gamestate.getTurnManager().getCurrentTurn().setMoved();
        gamestate.getTurnManager().endTurn();
        assertEquals(Turn.ACTIONS_EACH_TURN - 1, gamestate.getTurnManager().getCurrentTurn().getActionsRemaining());
    }

    @Test
    public void test_action_loss_unpowered_tertiary() {
        Square square = grid.getSquareAtPosition(new Position(5, 4));
        new TertiaryPowerFailure(square, gamestate.getTurnManager());
        gamestate.getTurnManager().getCurrentTurn().setMoved();
        gamestate.getTurnManager().endTurn();
        assertEquals(Turn.ACTIONS_EACH_TURN - 1, gamestate.getTurnManager().getCurrentTurn().getActionsRemaining());
    }

    @Test
    public void test_pf_passing() {
        new PrimaryPowerFailure(grid.getSquareAtPosition(new Position(7, 7)), gamestate.getTurnManager());
        assertEquals(3, countPowerFailures());
        gamestate.getTurnManager().getCurrentTurn().reduceAction();
        assertEquals(2, countPowerFailures());
        gamestate.getTurnManager().getCurrentTurn().reduceAction();
        assertEquals(3, countPowerFailures());
        gamestate.getTurnManager().getCurrentTurn().setMoved();
        gamestate.getTurnManager().endTurn();
        gamestate.getTurnManager().getCurrentTurn().setMoved();
        gamestate.getTurnManager().endTurn();
        gamestate.getTurnManager().getCurrentTurn().setMoved();
        gamestate.getTurnManager().endTurn();
        gamestate.getTurnManager().getCurrentTurn().reduceAction();
        gamestate.getTurnManager().getCurrentTurn().reduceAction();
        assertEquals(0, countPowerFailures());
    }

    @Test
    public void test_rotate() {
        new PrimaryPowerFailure(grid.getSquareAtPosition(new Position(7, 7)), gamestate.getTurnManager());
        boolean secondaryFailure = false;
        Square secondarySquare = null;
        for (Direction d : Direction.values()) {
            for (Class<?> c : grid.getSquareAtPosition(new Position(7, 7)).getNeighbour(d).getViewModel()
                    .getEffectViewModels()) {
                if (c.equals(SecondaryPowerFailure.class)) {
                    secondaryFailure = true;
                    secondarySquare = grid.getSquareAtPosition(new Position(7, 7)).getNeighbour(d);
                }
            }
        }
        assertTrue(secondaryFailure);
        secondaryFailure = false;
        gamestate.getTurnManager().getCurrentTurn().reduceAction();
        gamestate.getTurnManager().getCurrentTurn().reduceAction();
        for (Direction d : Direction.values()) {
            if (secondarySquare.getNeighbour(d).getViewModel().getEffectViewModels().contains(SecondaryPowerFailure
                    .class)) {
                secondaryFailure = true;
            }
        }
        assertTrue(secondaryFailure);
    }

    private int countPowerFailures() {
        int powerFailureCounter = 0;
        for (SquareViewModel sq : grid.getViewModel().getSquareViewModels()) {
            for (Class<?> c : sq.getEffectViewModels()) {
                if (c.equals(PrimaryPowerFailure.class) || c.equals(SecondaryPowerFailure.class) || c.equals
                        (TertiaryPowerFailure.class)) {
                    powerFailureCounter++;
                }
            }
        }
        return powerFailureCounter;

    }


}
