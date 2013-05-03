package be.kuleuven.objectron.model;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.*;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.grid.GridFactory;
import be.kuleuven.swop.objectron.domain.item.LightMine;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.domain.util.Position;
import be.kuleuven.swop.objectron.handler.MovePlayerHandler;
import org.junit.Before;
import org.junit.Test;

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
    private GameState gamestate;
    private Position p1Pos;
    private Position p2Pos;
    private Dimension dimension;
    private Grid grid;
    private MovePlayerHandler movePlayerHandler;
    //todo
    /*
    @Before
    public void setUp() throws GridTooSmallException, SquareOccupiedException {
        p1Pos = new Position(5, 4);
        p2Pos = new Position(5, 3);
        dimension = new Dimension(10, 10);
        grid = GridFactory.gridWithoutWallsItemsPowerFailures(dimension, p1Pos, p2Pos);
        gamestate = new GameState("p1", "p2", p1Pos, p2Pos, grid);

        square = new Square(new Position(5, 5));
        square.setActiveItem(new LightMine());
        player1 = gamestate.getCurrentPlayer();
        gamestate.endTurn();
        player2 = gamestate.getCurrentPlayer();
        //player = new Player("test", new Square(new Position(5, 4)));
        movePlayerHandler = new MovePlayerHandler(gamestate);
    }


    @Test
    public void test_square_lose_power() throws GameOverException, NotEnoughActionsException, InvalidMoveException {
        Square otherSquare = grid.getSquareAtPosition(new Position(5, 2));
        otherSquare.receivePowerFailure();
        movePlayerHandler.move(Direction.LEFT);
        System.out.println(gamestate.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
        assertTrue(gamestate.getCurrentTurn().getActionsRemaining() == (Turn.ACTIONS_EACH_TURN - UnpoweredSquareState.ACTIONS_TO_REDUCE));
    }        */


}
