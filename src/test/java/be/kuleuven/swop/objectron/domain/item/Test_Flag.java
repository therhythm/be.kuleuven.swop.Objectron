package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.*;
import be.kuleuven.swop.objectron.domain.gamestate.Game;
import be.kuleuven.swop.objectron.domain.gamestate.RaceGame;
import be.kuleuven.swop.objectron.domain.grid.GeneratedGridBuilder;
import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.grid.GridBuilder;
import be.kuleuven.swop.objectron.domain.grid.GridObjectMother;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.domain.util.Position;
import be.kuleuven.swop.objectron.handler.MovePlayerHandler;
import be.kuleuven.swop.objectron.handler.PickUpItemHandler;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 15/05/13
 * Time: 14:21
 * To change this template use File | Settings | File Templates.
 */
public class Test_Flag {

    private MovePlayerHandler movePlayerHandler;
    private PickUpItemHandler pickUpItemHandler;
    private Player player1,player2;
    private Grid grid;

    private Position p1Pos;
    private Position p2Pos;

    @Before
    public void setUp() throws GridTooSmallException {
        Dimension dimension = new Dimension(10, 10);

        p1Pos = new Position(0, 9);
        p2Pos = new Position(5, 9);

        List<Position> positions = new ArrayList<>();
        positions.add(p1Pos);
        positions.add(p2Pos);

        List<String> playerNames = new ArrayList<>();
        playerNames.add("p1");
        playerNames.add("p2");

        GridBuilder builder = new GeneratedGridBuilder(dimension, 2);
        builder.setStartingPositions(positions);
        grid = GridObjectMother.gridWithoutWallsItemsPowerFailures(builder);
        Game state = new RaceGame(playerNames, grid);
        movePlayerHandler = new MovePlayerHandler(state);
        pickUpItemHandler = new PickUpItemHandler(state);
        player1 = state.getTurnManager().getCurrentTurn().getCurrentPlayer();
        state.getTurnManager().getCurrentTurn().setMoved();
        state.getTurnManager().endTurn();
        player2 = state.getTurnManager().getCurrentTurn().getCurrentPlayer();
        state.getTurnManager().getCurrentTurn().setMoved();
        state.getTurnManager().endTurn();
    }

    @Test
    public void test_amount_flags() throws InventoryFullException, NotEnoughActionsException, InvalidMoveException, GameOverException, SquareOccupiedException {
        grid.getSquareAtPosition(new Position(0,9)).addItem(new Flag(player2, grid.getSquareAtPosition(p2Pos)));
        grid.getSquareAtPosition(new Position(1,9)).addItem(new Flag(player2, grid.getSquareAtPosition(p2Pos)));
        pickUpItemHandler.pickUpItem(0);
        movePlayerHandler.move(Direction.RIGHT);
        pickUpItemHandler.pickUpItem(0);

        assertTrue(player1.getInventoryItems().size()==1);
        assertTrue(player1.getCurrentSquare().getAvailableItems().size()==1);
        assertTrue(grid.getSquareAtPosition(p1Pos).getAvailableItems().size()==0);
    }

}
