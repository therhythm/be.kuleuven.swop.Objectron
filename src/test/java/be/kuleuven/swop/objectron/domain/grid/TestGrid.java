package be.kuleuven.swop.objectron.domain.grid;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.exception.*;
import be.kuleuven.swop.objectron.domain.gamestate.Game;
import be.kuleuven.swop.objectron.domain.gamestate.GameBuilder;
import be.kuleuven.swop.objectron.domain.gamestate.RaceGameBuilder;
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
    private GridBuilder builder;


    @Before

    public void setUp() throws GridTooSmallException, NumberOfPlayersException {
        Position p1Pos = new Position(1, 8);
        Position p2Pos = new Position(3, 8);

        List<Position> positions = new ArrayList<>();
        positions.add(p1Pos);
        positions.add(p2Pos);

        List<String> playerNames = new ArrayList<>();
        playerNames.add("p1");
        playerNames.add("p2");

        Dimension dimension = new Dimension(10, 10);
        builder = new GeneratedGridBuilder(dimension, 2);
        builder.setStartingPositions(positions);
        builder.initGrid(0);

        GameBuilder gameBuilder = new RaceGameBuilder(playerNames, dimension);
        gameBuilder.withBuilder(builder);
        Game state = gameBuilder.buildGame();
        grid = state.getGrid();

        movePlayerHandler = new MovePlayerHandler(state);
        endTurnHandler = new EndTurnHandler(state);
    }

    @Test(expected = InvalidMoveException.class)
    public void test_invalid_move_diagonal() throws InvalidMoveException, NotEnoughActionsException,
            GameOverException, SquareOccupiedException {
        movePlayerHandler.move(Direction.RIGHT);
        movePlayerHandler.move(Direction.UP_RIGHT);
        endTurnHandler.endTurn();

        movePlayerHandler.move(Direction.UP_LEFT);
    }

    @Test
    public void test_valid_move_diagonal() throws InvalidMoveException, NotEnoughActionsException, GameOverException,
            SquareOccupiedException {
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
    public void test_items_grid() throws GridTooSmallException{
        grid = GridObjectMother.gridWithoutWallsPowerFailures(builder);

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
                        if (item.getName().equals("Light Mine")) {
                            numberOfLightMines++;
                        }
                        if (item.getName().equals("Teleporter")) {
                            numberOfTeleporters++;
                        }
                        if (item.getName().equals("Uncharged Identity Disc"))
                            numberOfIdentitydiscs++;
                        if (item.getName().equals("Force Field"))
                            numberOfForceFields++;

                        if (item.getName().equals("Charged Identity Disc"))
                            numberOfIdentityDiscsCharged++;
                    }
                }
            }
        }
        Dimension dimension = grid.getDimension();
        assertTrue(numberOfLightMines <= (int) Math.ceil(GeneratedGridBuilder.PERCENTAGE_OF_LIGHTMINES * dimension
                .area()));
        assertTrue(numberOfTeleporters <= (int) Math.ceil(GeneratedGridBuilder.PERCENTAGE_OF_TELEPORTERS * dimension
                .area()));
        assertTrue(numberOfIdentitydiscs <= (int) Math.ceil(GeneratedGridBuilder.PERCENTAGE_OF_IDENTITYDISCS *
                dimension.area()));
        assertTrue(numberOfForceFields <= (int) Math.ceil(GeneratedGridBuilder.PERCENTAGE_OF_FORCEFIELDS * dimension
                .area()));
        assertTrue(numberOfIdentityDiscsCharged == 1);
        assertTrue(hasItems);

    }

    private int calculatedistance(Position position1, Position position2) {
        int x = Math.abs(position1.getHIndex() - position2.getHIndex());
        int y = Math.abs(position1.getVIndex() - position2.getVIndex());
        return Math.round((x + y) / 2);

    }

    @Test
    public void test_charged_identity_disc() throws GridTooSmallException, NumberOfPlayersException {
        Position p1Pos = new Position(0, 9);
        Position p2Pos = new Position(9, 0);

        List<Position> positions = new ArrayList<>();
        positions.add(p1Pos);
        positions.add(p2Pos);
        builder.setStartingPositions(positions);
        grid = GridObjectMother.gridWithoutWallsPowerFailures(builder);

        int aantal = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                for (Item item : grid.getSquareAtPosition(new Position(i, j)).getAvailableItems()) {
                    if (item.getName().equals("Charged Identity Disc")) {
                        int distanceFromPlayer1 = calculatedistance(p1Pos, new Position(i, j));
                        int distanceFromPlayer2 = calculatedistance(p2Pos, new Position(i, j));
                        assertTrue(Math.abs(distanceFromPlayer1 - distanceFromPlayer2) < 2);
                        aantal++;
                    }

                }
            }
        }
        assertTrue(aantal == 1);
    }

    @Test(expected = InvalidMoveException.class)
    public void test_invalid_move_neighbor() throws InvalidMoveException, NotEnoughActionsException,
            GameOverException, SquareOccupiedException {
        movePlayerHandler.move(Direction.RIGHT);
        movePlayerHandler.move(Direction.RIGHT);
        endTurnHandler.endTurn();
    }

    @Test
    public void test_place_charged_identity_disc() throws GridTooSmallException, NumberOfPlayersException {
        List<Position> positions = new ArrayList<>();
        positions.add(new Position(0, 9));
        positions.add(new Position(9, 0));

        Dimension dimension = grid.getDimension();
        GridBuilder builder = new GeneratedGridBuilder(dimension, 2);
        builder.setStartingPositions(positions);
        grid = GridObjectMother.gridWithoutWallsPowerFailures(builder);

        int numberOfIdentityDiscsCharged = 0;
        for (int i = 0; i < dimension.getWidth(); i++) {
            for (int j = 0; j < dimension.getHeight(); j++) {
                if (grid.getSquareAtPosition(new Position(i, j)).getAvailableItems().size() != 0) {
                    for (Item item : grid.getSquareAtPosition(new Position(i, j)).getAvailableItems()) {
                        if (item.getName().equals("Charged Identity Disc"))
                            numberOfIdentityDiscsCharged++;
                    }
                }
            }
        }
        assertTrue(numberOfIdentityDiscsCharged == 1);
    }

    @Test
    public void test_place_force_field_max_one_per_square() throws GridTooSmallException, NumberOfPlayersException {
        List<Position> positions = new ArrayList<>();
        positions.add(new Position(0, 9));
        positions.add(new Position(9, 0));

        Dimension dimension = grid.getDimension();
        for (int repeat = 0; repeat < 100; repeat++) {
            GridBuilder builder = new GeneratedGridBuilder(dimension, 2);
            builder.setStartingPositions(positions);
            grid = GridObjectMother.gridWithoutWallsItemsPowerFailures(builder);


            for (int i = 0; i < dimension.getWidth(); i++) {
                for (int j = 0; j < dimension.getHeight(); j++) {
                    Square square = grid.getSquareAtPosition(new Position(i, j));
                    int aantalForceFields = 0;
                    for (Item item : square.getAvailableItems()) {
                        if (item.getName().equals("Force Field"))
                            aantalForceFields++;
                    }
                    assertTrue(aantalForceFields <= 1);
                }
            }
        }
    }

}
