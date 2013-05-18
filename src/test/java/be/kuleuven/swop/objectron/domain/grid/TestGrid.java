package be.kuleuven.swop.objectron.domain.grid;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.exception.*;
import be.kuleuven.swop.objectron.domain.gamestate.Game;
import be.kuleuven.swop.objectron.domain.gamestate.RaceGame;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.domain.util.Position;
import be.kuleuven.swop.objectron.handler.EndTurnHandler;
import be.kuleuven.swop.objectron.handler.MovePlayerHandler;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

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
    private Game state;
    private Position p1Pos;
    private Position p2Pos;
    private Dimension dimension;
    private  List<Position> positions;

    @Before
    public void setUp() throws GridTooSmallException {

        p1Pos = new Position(1, 8);
        p2Pos = new Position(3, 8);

       positions = new ArrayList<Position>();
        positions.add(p1Pos);
        positions.add(p2Pos);

        List<String> playerNames = new ArrayList<String>();
        playerNames.add("p1");
        playerNames.add("p2");

        dimension = new Dimension(10, 10);
        grid = GridFactory.gridWithoutWallsItemsPowerFailures(dimension, positions);
        state = new RaceGame(playerNames, grid);

        movePlayerHandler = new MovePlayerHandler(state);
        endTurnHandler = new EndTurnHandler(state);
    }

    @Test(expected = InvalidMoveException.class)
    public void test_invalid_move_diagonal() throws InvalidMoveException, NotEnoughActionsException, GameOverException, SquareOccupiedException {
        movePlayerHandler.move(Direction.RIGHT);
        movePlayerHandler.move(Direction.UP_RIGHT);
        endTurnHandler.endTurn();

        movePlayerHandler.move(Direction.UP_LEFT);
    }

    @Test
    public void test_valid_move_diagonal() throws InvalidMoveException, NotEnoughActionsException, GameOverException, SquareOccupiedException {
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
    public void test_items_grid() throws GridTooSmallException {


        grid = GridFactory.gridWithoutWallsPowerFailures(dimension, positions);
        boolean hasItems = false;
        int numberOfLightMines = 0;
        int numberOfTeleporters = 0;
        int numberOfIdentitydiscs = 0;
        int numberOfForceFields = 0;
        int numberOfIdentityDiscsCharged = 0;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (grid.getSquareAtPosition(new Position(i, j)).getAvailableItems().size() != 0) {
                    hasItems = true;
                    for (Item item : grid.getSquareAtPosition(new Position(i, j)).getAvailableItems()) {
                        if (item.getName() == "Light Mine") {
                            numberOfLightMines++;
                        }
                        if (item.getName() == "Teleporter") {
                            numberOfTeleporters++;
                        }
                        if (item.getName() == "Uncharged Identity Disc")
                            numberOfIdentitydiscs++;
                        if (item.getName() == "Force Field")
                            numberOfForceFields++;

                        if (item.getName() == "Charged Identity Disc")
                            numberOfIdentityDiscsCharged++;
                    }
                }
            }
        }
        System.out.println(numberOfIdentityDiscsCharged);
        assertTrue(numberOfLightMines <= (int) Math.ceil(GridBuilder.PERCENTAGE_OF_LIGHTMINES * dimension.area()));
        assertTrue(numberOfTeleporters <= (int) Math.ceil(GridBuilder.PERCENTAGE_OF_TELEPORTERS * dimension.area()));
        assertTrue(numberOfIdentitydiscs <= (int) Math.ceil(GridBuilder.PERCENTAGE_OF_IDENTITYDISCS * dimension.area()));
        assertTrue(numberOfForceFields <= (int) Math.ceil(GridBuilder.PERCENTAGE_OF_FORCEFIELDS * dimension.area()));
        assertTrue(numberOfIdentityDiscsCharged == 1);
        assertTrue(hasItems);

    }

    private int calculatedistance(Position position1, Position position2) {
        int x = Math.abs(position1.getHIndex() - position2.getHIndex());
        int y = Math.abs(position1.getVIndex() - position2.getVIndex());
        return Math.round((x + y) / 2);

    }

    @Test
    public void test_charged_identity_disc() throws GridTooSmallException {
        positions = new ArrayList<Position>();
        positions.add( new Position(0, 9));
        positions.add( new Position(9, 0));
        grid = GridFactory.gridWithoutWallsPowerFailures(dimension,positions);
        int aantal = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                for (Item item : grid.getSquareAtPosition(new Position(i, j)).getAvailableItems()) {
                    if (item.getName() == "Charged Identity Disc") {
                        int distanceFromPlayer1 = calculatedistance(p1Pos, new Position(i, j));
                        int distanceFromPlayer2 = calculatedistance(p2Pos, new Position(i, j));
                        System.out.println("distance from player1: " + distanceFromPlayer1);
                        System.out.println("distance from player2: " + distanceFromPlayer2);
                        assertTrue(Math.abs(distanceFromPlayer1 - distanceFromPlayer2) < 2);
                        aantal++;

                    }

                }
            }
        }
        assertTrue(aantal == 1);
    }

    @Test
    public void test_no_charged_identity_disc() throws GridTooSmallException {
        List<Position> wallPositions = new ArrayList<Position>();
        wallPositions.add(new Position(4, 5));
        wallPositions.add(new Position(5, 5));
        wallPositions.add(new Position(5, 4));
        wallPositions.add(new Position(4, 4));
        wallPositions.add(new Position(4, 3));
        int aantal = 0;

        positions = new ArrayList<Position>();
        positions.add( new Position(0, 9));
        positions.add( new Position(9, 0));

        grid = GridFactory.gridWithSpecifiedWallsPowerFailures(dimension, positions, wallPositions);
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
        assertTrue(aantal == 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_getInvalidSquare() {
        grid.getSquareAtPosition(new Position(grid.getDimension().getHeight() + 1, grid.getDimension().getWidth() + 1));
    }

    @Test(expected = InvalidMoveException.class)
    public void test_invalid_move_neighbor() throws InvalidMoveException, NotEnoughActionsException, GameOverException, SquareOccupiedException {
        movePlayerHandler.move(Direction.RIGHT);
        movePlayerHandler.move(Direction.RIGHT);
        endTurnHandler.endTurn();
    }

    @Test
    public void test_place_charged_identity_disc() throws GridTooSmallException {
        positions = new ArrayList<Position>();
        positions.add( new Position(0, 9));
        positions.add( new Position(9, 0));

        grid = GridFactory.gridWithoutWallsPowerFailures(dimension, positions);
        int numberOfIdentityDiscsCharged = 0;
        Square square_charged_ID = null;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (grid.getSquareAtPosition(new Position(i, j)).getAvailableItems().size() != 0) {
                    for (Item item : grid.getSquareAtPosition(new Position(i, j)).getAvailableItems()) {
                        if (item.getName() == "Charged Identity Disc")
                            numberOfIdentityDiscsCharged++;
                        square_charged_ID = grid.getSquareAtPosition(new Position(i, j));
                    }
                }
            }
        }
        System.out.println(square_charged_ID.getPosition());
        assertTrue(numberOfIdentityDiscsCharged == 1);
    }

    @Test
    public void test_place_force_field_max_one_per_square() throws GridTooSmallException {
        p1Pos = new Position(0, 1);
        p2Pos = new Position(0, 0);

        positions = new ArrayList<Position>();
        positions.add( new Position(0, 9));
        positions.add( new Position(9, 0));


        List<Position> wallPositions = new ArrayList<Position>();
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                wallPositions.add(new Position(i, j));
            }
        }
        for (int repeat = 0; repeat < 100; repeat++) {
            grid = GridFactory.gridWithSpecifiedWallsPowerFailures(dimension, positions, wallPositions);
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    Square square = grid.getSquareAtPosition(new Position(i, j));
                    int aantalForceFields = 0;
                    for (Item item : square.getAvailableItems()) {
                        if (item.getName() == "Force Field")
                            aantalForceFields++;
                    }
                    assertTrue(aantalForceFields <= 1);
                }
            }
        }
    }

}
