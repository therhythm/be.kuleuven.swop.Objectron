package be.kuleuven.objectron.model;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Player;

import be.kuleuven.swop.objectron.domain.Settings;
import be.kuleuven.swop.objectron.domain.exception.*;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.grid.GridFactory;
import be.kuleuven.swop.objectron.domain.item.LightMine;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.domain.util.Position;
import be.kuleuven.swop.objectron.handler.MovePlayerHandler;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 13/03/13
 * Time: 21:26
 * To change this template use File | Settings | File Templates.
 */
public class TestSquare {
    private Square currentSquare;
    private Player player;
    private GameState state;
    private MovePlayerHandler movePlayerHandler;

    @Before
    public void setUp()throws GridTooSmallException, SquareOccupiedException {
        Dimension dimension = new Dimension(10, 10);
        Grid grid = GridFactory.gridWithoutWalls(dimension, new Position(0, 9), new Position(9, 0));
        state = new GameState("p1", "p2", dimension, grid);
        player = state.getCurrentPlayer();
        currentSquare = player.getCurrentSquare();
        movePlayerHandler = new MovePlayerHandler(state);
    }

    @Test
    public void testStartUnpowered(){
        state.endTurn();
        currentSquare.receivePowerFailure();
        state.endTurn();
        assertEquals(Settings.PLAYER_ACTIONS_EACH_TURN - 1, state.getCurrentTurn().getActionsRemaining());
    }

    @Test
    public void testStepOnUnpoweredSquare() throws GameOverException, InvalidMoveException, NotEnoughActionsException{
        currentSquare.getNeighbour(Direction.UP).receivePowerFailure();
        assertEquals(player, state.getCurrentPlayer());
        movePlayerHandler.move(Direction.UP);
        assertNotEquals(player, state.getCurrentPlayer());
    }

    @Test
    public void testStepOnActiveUnpowered() throws SquareOccupiedException, NotEnoughActionsException, SquareOccupiedException, InvalidMoveException, GameOverException{
        currentSquare.getNeighbour(Direction.UP).setActiveItem(new LightMine());
        currentSquare.getNeighbour(Direction.UP).receivePowerFailure();
        int remainingActionsAfterMove = state.getCurrentTurn().getActionsRemaining() - 1;
        movePlayerHandler.move(Direction.UP);
        state.endTurn();
        assertEquals(4 - remainingActionsAfterMove, player.getRemainingPenalties());
    }


}
