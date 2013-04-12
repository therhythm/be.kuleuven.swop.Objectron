package be.kuleuven.objectron.model;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Player;

import be.kuleuven.swop.objectron.domain.Settings;
import be.kuleuven.swop.objectron.domain.Wall;
import be.kuleuven.swop.objectron.domain.exception.*;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.grid.GridFactory;
import be.kuleuven.swop.objectron.domain.item.LightMine;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.square.SquareObserver;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.domain.util.Position;
import be.kuleuven.swop.objectron.handler.MovePlayerHandler;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

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
    public void setUp()throws GridTooSmallException, SquareOccupiedException {
        Dimension dimension = new Dimension(10, 10);
        grid = GridFactory.gridWithoutWallsPowerFailures(dimension, new Position(0, 9), new Position(9, 0));
        state = new GameState("p1", "p2", dimension, grid);
        player = state.getCurrentPlayer();
        currentSquare = player.getCurrentSquare();
        movePlayerHandler = new MovePlayerHandler(state);
        regainedPower = false;
        powerLoss = false;
        powerLossCounter = 0;
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
    public void testStepOnActiveUnpowered() throws NotEnoughActionsException, SquareOccupiedException, InvalidMoveException, GameOverException{
        currentSquare.getNeighbour(Direction.UP).setActiveItem(new LightMine());
        currentSquare.getNeighbour(Direction.UP).receivePowerFailure();
        int remainingActionsAfterMove = state.getCurrentTurn().getActionsRemaining() - 1;
        movePlayerHandler.move(Direction.UP);
        state.endTurn();
        assertEquals(4 - remainingActionsAfterMove, player.getRemainingPenalties());
    }

    @Test
    public void checkReceivingPowerFailure() throws GridTooSmallException{
        grid = GridFactory.gridWithoutWalls(new Dimension(10,10), new Position(0,0), new Position(2,2));
        Square currentSquare = grid.getSquareAtPosition(new Position(5,5));
        currentSquare.attach(this);
        for(Direction d: Direction.values()){
            currentSquare.getNeighbour(d).attach(this);
        }

        while (!powerLoss){
            currentSquare.newTurn(new Turn(player));
        }

        assertEquals(powerLossCounter, 9);
    }

    @Test
    public void testRegainPower(){
        currentSquare.receivePowerFailure();
        currentSquare.attach(this);
        for(int i = 0; i < Settings.SQUARE_TURNS_WITHOUT_POWER; i++){
             state.endTurn();
        }
        assertEquals(true, regainedPower);
    }


    @Override
    public void lostPower(Position position) {
        powerLossCounter ++;
        if(position.getHIndex() == 5 && position.getVIndex() == 5){
            powerLoss = true;
        }
    }

    @Override
    public void regainedPower(Position position) {
       regainedPower = true;
    }
}
