package be.kuleuven.objectron.model;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.*;
import be.kuleuven.swop.objectron.domain.gamestate.Game;
import be.kuleuven.swop.objectron.domain.gamestate.RaceGame;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.grid.GeneratedGridBuilder;
import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.grid.GridBuilder;
import be.kuleuven.swop.objectron.domain.grid.GridObjectMother;
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

/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 13/03/13
 * Time: 21:26
 * To change this template use File | Settings | File Templates.
 */
public class TestSquare {
    private Player player2;
    private Game gamestate;
    private Grid grid;
    private MovePlayerHandler movePlayerHandler;

    @Before
    public void setUp() throws GridTooSmallException, SquareOccupiedException, FileInvalidException {
        Position p1Pos = new Position(5, 4);
        Position p2Pos = new Position(5, 3);

        List<Position> positions = new ArrayList<>();
        positions.add(p1Pos);
        positions.add(p2Pos);

        List<String> playerNames = new ArrayList<>();
        playerNames.add("p1");
        playerNames.add("p2");

        Dimension dimension = new Dimension(10, 10);

        GridBuilder builder = new GeneratedGridBuilder(dimension, 2);
        builder.setStartingPositions(positions);
        grid = GridObjectMother.gridWithoutWallsItemsPowerFailures(builder);
        gamestate = new RaceGame(playerNames, grid);

        TurnManager turnManager = gamestate.getTurnManager();
        turnManager.getCurrentTurn().setMoved();
        turnManager.endTurn();
        player2 = turnManager.getCurrentTurn().getCurrentPlayer();
        movePlayerHandler = new MovePlayerHandler(gamestate);
    }


    @Test
    public void test_square_lose_power() throws GameOverException, NotEnoughActionsException, InvalidMoveException,
            SquareOccupiedException {
        Square otherSquare = grid.getSquareAtPosition(new Position(5, 2));
        otherSquare.receivePowerFailure(PowerFailure.PF_PRIMARY_TURNS, PowerFailure.PF_PRIMARY_ACTIONS);
        movePlayerHandler.move(Direction.UP);
        assertNotEquals(player2, gamestate.getTurnManager().getCurrentTurn().getCurrentPlayer());
    }

    @Test
    public void test_action_loss_unpowered() {
        Square square = grid.getSquareAtPosition(new Position(5, 4));
        square.receivePowerFailure(PowerFailure.PF_PRIMARY_TURNS, PowerFailure.PF_PRIMARY_ACTIONS);
        gamestate.getTurnManager().getCurrentTurn().setMoved();
        gamestate.getTurnManager().endTurn();
        assertEquals(gamestate.getTurnManager().getCurrentTurn().getActionsRemaining(), 2);
    }


}
