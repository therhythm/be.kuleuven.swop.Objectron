package be.kuleuven.objectron.model;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.*;
import be.kuleuven.swop.objectron.domain.gamestate.Game;
import be.kuleuven.swop.objectron.domain.gamestate.RaceGame;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.grid.GridFactory;
import be.kuleuven.swop.objectron.domain.item.LightMine;
import be.kuleuven.swop.objectron.domain.square.PowerFailure;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.domain.util.Position;
import be.kuleuven.swop.objectron.handler.MovePlayerHandler;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 13/03/13
 * Time: 21:26
 * To change this template use File | Settings | File Templates.
 */
public class TestSquare {
    private Square square;
    private Player player1;
    private Player player2;
    private Game gamestate;
    private Position p1Pos;
    private Position p2Pos;
    private Dimension dimension;
    private Grid grid;
    private MovePlayerHandler movePlayerHandler;


    @Before
    public void setUp() throws GridTooSmallException, SquareOccupiedException {
        p1Pos = new Position(5, 4);
        p2Pos = new Position(5, 3);

        List<Position> positions = new ArrayList<Position>();
        positions.add(p1Pos);
        positions.add(p2Pos);

        List<String> playerNames = new ArrayList<String>();
        playerNames.add("p1");
        playerNames.add("p2");

        dimension = new Dimension(10, 10);
        grid = GridFactory.gridWithoutWallsItemsPowerFailures(dimension, positions);
        gamestate = new RaceGame(playerNames,grid);

        square = new Square(new Position(5, 5));
        new LightMine().place(square);
        TurnManager turnManager = gamestate.getTurnManager();
        player1 = turnManager.getCurrentTurn().getCurrentPlayer();
        turnManager.getCurrentTurn().setMoved();
        turnManager.endTurn();
        player2 = turnManager.getCurrentTurn().getCurrentPlayer();
        movePlayerHandler = new MovePlayerHandler(gamestate);
    }


    @Test
    public void test_square_lose_power() throws GameOverException, NotEnoughActionsException, InvalidMoveException, SquareOccupiedException {
        Square otherSquare = grid.getSquareAtPosition(new Position(5, 2));
        otherSquare.receivePowerFailure(PowerFailure.PF_PRIMARY_TURNS, PowerFailure.PF_PRIMARY_ACTIONS);
        movePlayerHandler.move(Direction.UP);
        assertNotEquals(player2, gamestate.getTurnManager().getCurrentTurn().getCurrentPlayer());
    }

    @Test
    public void test_action_loss_unpowered(){
        Square square = grid.getSquareAtPosition(new Position(5,4));
        square.receivePowerFailure(PowerFailure.PF_PRIMARY_TURNS, PowerFailure.PF_PRIMARY_ACTIONS);
        gamestate.getTurnManager().getCurrentTurn().setMoved();
        gamestate.getTurnManager().endTurn();
        assertEquals(gamestate.getTurnManager().getCurrentTurn().getActionsRemaining(), 2);
    }


}
