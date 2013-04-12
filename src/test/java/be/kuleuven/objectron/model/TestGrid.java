package be.kuleuven.objectron.model;

import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.grid.GridFactory;
import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.item.LightMine;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.domain.util.Position;
import be.kuleuven.swop.objectron.handler.EndTurnHandler;
import be.kuleuven.swop.objectron.handler.MovePlayerHandler;
import be.kuleuven.swop.objectron.domain.*;
import be.kuleuven.swop.objectron.domain.exception.GameOverException;
import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 11/03/13
 * Time: 20:10
 * To change this template use File | Settings | File Templates.
 */
public class TestGrid {

    private EndTurnHandler endTurnHandler;
    private MovePlayerHandler movePlayerHandler;
    private Grid grid;
    private GameState state;
    private Position p1Pos;
    private Position p2Pos;
    private Dimension dimension;

    @Before
    public void setUp() throws GridTooSmallException {

        p1Pos = new Position(1, 8);
        p2Pos = new Position(3, 8);
        dimension = new Dimension(10, 10);
        grid = GridFactory.gridWithoutWallsPowerFailures(dimension, p1Pos, p2Pos);
        state = new GameState("p1", "p2", p1Pos, p2Pos, grid);
        movePlayerHandler = new MovePlayerHandler(state);
        endTurnHandler = new EndTurnHandler(state);
    }

    @Test(expected = InvalidMoveException.class)
    public void test_invalid_move_diagonal() throws InvalidMoveException, NotEnoughActionsException, GameOverException {
        movePlayerHandler.move(Direction.RIGHT);
        movePlayerHandler.move(Direction.UP_RIGHT);
        endTurnHandler.endTurn();

        movePlayerHandler.move(Direction.UP_LEFT);
    }

    @Test
    public void test_valid_move_diagonal() throws InvalidMoveException, NotEnoughActionsException, GameOverException {
        movePlayerHandler.move(Direction.RIGHT);
        movePlayerHandler.move(Direction.UP_RIGHT);
        endTurnHandler.endTurn();

        movePlayerHandler.move(Direction.DOWN_LEFT);
        movePlayerHandler.move(Direction.LEFT);
        movePlayerHandler.move(Direction.UP_LEFT);
    }

    /**
     * Deze test gaat controleren of er wel degelijk items in de grid geplaatst worden.
     */
    @Test
    public void test_items_grid() {
        int lightmines = 0;
        int identitydiscs = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                for (Item item : grid.getSquareAtPosition(new Position(i, j)).getAvailableItems()) {
                    if (item instanceof LightMine)
                        lightmines++;

                    if (item.getName() == "Uncharged Identity Disc")
                        identitydiscs++;

                }
            }
        }
        assertTrue(lightmines == 2);
        assertTrue(identitydiscs == 2);

    }

    private int calculatedistance(Position position1, Position position2) {
        int x = Math.abs(position1.getHIndex() - position2.getHIndex());
        int y = Math.abs(position1.getVIndex() - position2.getVIndex());
        return Math.round((x + y) / 2);

    }

    @Test
    public void test_charged_identity_disc() throws GridTooSmallException {
        grid = GridFactory.gridWithoutWallsPowerFailures(dimension, new Position(0,9), new Position(9,0));
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                for (Item item : grid.getSquareAtPosition(new Position(i, j)).getAvailableItems()) {
                    if (item.getName() == "Charged Identity Disc") {
                        int distanceFromPlayer1 = calculatedistance(p1Pos, new Position(i, j));
                        int distanceFromPlayer2 = calculatedistance(p2Pos, new Position(i, j));
                        System.out.println("distance from player1: " + distanceFromPlayer1);
                        System.out.println("distance from player2: " + distanceFromPlayer2);
                        assertTrue(Math.abs(distanceFromPlayer1 - distanceFromPlayer2) < 2);

                    }

                }
            }
        }
    }

    @Test
    public void test_no_charged_identity_disc() throws GridTooSmallException {
        List<Position> wallPositions = new ArrayList<Position>();
        wallPositions.add(new Position(4,5));
        wallPositions.add(new Position(5,5));
        wallPositions.add(new Position(5,4));
        wallPositions.add(new Position(4,4));
        wallPositions.add(new Position(4,3));
                   int aantal = 0;
        grid = GridFactory.gridWithSpecifiedWallsPowerFailures(dimension, new Position(0,9), new Position(9,0),wallPositions);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                for (Item item : grid.getSquareAtPosition(new Position(i, j)).getAvailableItems()) {
                    if (item.getName() == "Charged Identity Disc") {
                        int distanceFromPlayer1 = calculatedistance(p1Pos, new Position(i, j));
                        int distanceFromPlayer2 = calculatedistance(p2Pos, new Position(i, j));
                        System.out.println("squaere:");
                        System.out.println(grid.getSquareAtPosition(new Position(i, j)));
                        System.out.println("distance from player1: " + distanceFromPlayer1);
                        System.out.println("distance from player2: " + distanceFromPlayer2);
                        assertTrue(Math.abs(distanceFromPlayer1 - distanceFromPlayer2) < 2);
                                                                             aantal++;
                    }

                }
            }
        }
        assertTrue(aantal==1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_getInvalidSquare() {
        grid.getSquareAtPosition(new Position(11, 11));
    }

    @Test(expected = InvalidMoveException.class)
    public void test_invalid_move_neighbor() throws InvalidMoveException, NotEnoughActionsException, GameOverException {
        movePlayerHandler.move(Direction.RIGHT);
        movePlayerHandler.move(Direction.RIGHT);
        endTurnHandler.endTurn();
    }
}
