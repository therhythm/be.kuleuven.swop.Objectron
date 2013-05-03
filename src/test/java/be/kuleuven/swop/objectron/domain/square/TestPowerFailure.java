package be.kuleuven.swop.objectron.domain.square;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.*;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.grid.GridFactory;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.item.LightMine;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.square.SquareObserver;
import be.kuleuven.swop.objectron.domain.square.UnpoweredState;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.domain.util.Position;
import be.kuleuven.swop.objectron.handler.MovePlayerHandler;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 13/03/13
 * Time: 21:26
 * To change this template use File | Settings | File Templates.
 */
public class TestPowerFailure implements SquareObserver {
    private Square currentSquare;
    private Player player;
    private GameState state;
    private MovePlayerHandler movePlayerHandler;
    private Grid grid;
    private boolean regainedPower;
    private boolean powerLoss;
    private int powerLossCounter;

    @Before
    public void setUp() throws GridTooSmallException, SquareOccupiedException {
        Dimension dimension = new Dimension(10, 10);
        grid = GridFactory.gridWithoutWallsPowerFailures(dimension, new Position(0, 9), new Position(9, 0));
        state = new GameState("p1", "p2", dimension, grid);
        player = state.getTurnManager().getCurrentTurn().getCurrentPlayer();
        currentSquare = player.getCurrentSquare();
        movePlayerHandler = new MovePlayerHandler(state);
        regainedPower = false;
        powerLoss = false;
        powerLossCounter = 0;
    }

    @Test
    public void testStartUnpowered() {
        TurnManager turnManager = state.getTurnManager();
        turnManager.endTurn();
        currentSquare.receivePowerFailure();
        turnManager.endTurn();
        assertEquals(Turn.ACTIONS_EACH_TURN - 1, turnManager.getCurrentTurn().getActionsRemaining());
    }

    @Test
    public void testStepOnUnpoweredSquare() throws GameOverException, InvalidMoveException, NotEnoughActionsException {
        currentSquare.getNeighbour(Direction.UP).receivePowerFailure();
        assertEquals(player, state.getTurnManager().getCurrentTurn().getCurrentPlayer());
        movePlayerHandler.move(Direction.UP);
        assertNotEquals(player, state.getTurnManager().getCurrentTurn().getCurrentPlayer());
    }

    @Test
    public void testStepOnActiveUnpowered() throws NotEnoughActionsException, SquareOccupiedException, InvalidMoveException, GameOverException {
        new LightMine().place(currentSquare.getNeighbour(Direction.UP));
        currentSquare.getNeighbour(Direction.UP).receivePowerFailure();
        int remainingActionsAfterMove = state.getTurnManager().getCurrentTurn().getActionsRemaining() - 1;
        movePlayerHandler.move(Direction.UP);

        state.getTurnManager().endTurn();
        assertEquals(1 + Turn.ACTIONS_EACH_TURN - remainingActionsAfterMove, player.getRemainingPenalties());
    }

    @Test
    public void checkReceivingPowerFailure() throws GridTooSmallException {
        grid = GridFactory.gridWithoutWalls(new Dimension(10, 10), new Position(0, 0), new Position(2, 2));
        Square currentSquare = grid.getSquareAtPosition(new Position(5, 5));
        currentSquare.attach(this);
        for (Direction d : Direction.values()) {
            currentSquare.getNeighbour(d).attach(this);
        }

        while (!powerLoss) {
            currentSquare.newTurn(new Turn(player));
        }

        assertEquals(powerLossCounter, 9);
    }

    @Test
    public void testRegainPower() {
        currentSquare.receivePowerFailure();
        currentSquare.attach(this);
        for (int i = 0; i < UnpoweredState.TURNS_WITHOUT_POWER; i++) {
            state.getTurnManager().endTurn();
        }
        assertEquals(true, regainedPower);
    }


    @Override
    public void lostPower(Position position) {
        powerLossCounter++;
        if (position.getHIndex() == 5 && position.getVIndex() == 5) {
            powerLoss = true;
        }
    }

    @Override
    public void regainedPower(Position position) {
        regainedPower = true;
    }

    @Override
    public void itemPlaced(Item item, Position position) {
        //irrelevant
    }
}
