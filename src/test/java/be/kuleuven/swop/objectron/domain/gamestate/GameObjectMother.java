package be.kuleuven.swop.objectron.domain.gamestate;

import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.exception.NumberOfPlayersException;
import be.kuleuven.swop.objectron.domain.grid.GeneratedGridBuilder;
import be.kuleuven.swop.objectron.domain.grid.GridBuilder;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.domain.util.Position;

import java.util.List;

/**
 * @author : Nik Torfs
 *         Date: 20/05/13
 *         Time: 22:32
 */
public class GameObjectMother {

    // Contstruct a grid without walls and powerfailures
    public static Game raceGameWithoutWallsPowerFailures(Dimension dimension,
                                                         List<String> playerNames, List<Position> playerPositions) throws GridTooSmallException, NumberOfPlayersException {
        GridBuilder builder = new GeneratedGridBuilder(dimension, playerNames.size());
        builder.initGrid(0);
        builder.setStartingPositions(playerPositions);
        builder.buildItems();

        GameBuilder gameBuilder = new RaceGameBuilder(playerNames, dimension);
        gameBuilder.withBuilder(builder);
        return gameBuilder.buildGame();
    }

    // Contstruct a grid with specified walls and without powerfailures and Items
    public static Game raceGameWithSpecifiedWallsWithoutItemsAndPowerFailures(Dimension dimension,
                                                                              List<String> playerNames, List<Position> playerPositions, List<List<Position>> wallPositions) throws
            GridTooSmallException, NumberOfPlayersException {
        GridBuilder builder = new GeneratedGridBuilder(dimension, playerNames.size());
        builder.initGrid(0);
        builder.setStartingPositions(playerPositions);
        builder.buildWalls(wallPositions);

        GameBuilder gameBuilder = new RaceGameBuilder(playerNames, dimension);
        gameBuilder.withBuilder(builder);
        return gameBuilder.buildGame();
    }

    // Contstruct a grid without walls, items and powerfailures
    public static Game raceGameWithoutWallsItemsPowerFailures(Dimension dimension,
                                                              List<String> playerNames, List<Position> playerPositions) throws GridTooSmallException, NumberOfPlayersException {
        GridBuilder builder = new GeneratedGridBuilder(dimension, playerNames.size());
        builder.initGrid(0);
        builder.setStartingPositions(playerPositions);

        GameBuilder gameBuilder = new RaceGameBuilder(playerNames, dimension);
        gameBuilder.withBuilder(builder);
        return gameBuilder.buildGame();
    }

    // Contstruct a grid without walls, items and powerfailures
    public static Game ctfGameWithoutWallsItemsPowerFailures(Dimension dimension,
                                                              List<String> playerNames, List<Position> playerPositions) throws GridTooSmallException, NumberOfPlayersException {
        GridBuilder builder = new GeneratedGridBuilder(dimension, playerNames.size());
        builder.initGrid(0);
        builder.setStartingPositions(playerPositions);

        GameBuilder gameBuilder = new CTFGameBuilder(playerNames, dimension);
        gameBuilder.withBuilder(builder);
        return gameBuilder.buildGame();
    }

    //construct a grid without walls
    public static Game raceGameWithoutWalls(Dimension dimension,
                                            List<String> playerNames, List<Position> playerPositions) throws GridTooSmallException, NumberOfPlayersException {
        GridBuilder builder = new GeneratedGridBuilder(dimension, playerNames.size());
        builder.setStartingPositions(playerPositions);
        builder.buildItems();

        GameBuilder gameBuilder = new RaceGameBuilder(playerNames, dimension);
        gameBuilder.withBuilder(builder);
        return gameBuilder.buildGame();
    }

    //construct a grid without items
    public static Game raceGameWithoutItems(Dimension dimension,
                                            List<String> playerNames, List<Position> playerPositions) throws GridTooSmallException, NumberOfPlayersException {
        GridBuilder builder = new GeneratedGridBuilder(dimension, playerNames.size());
        builder.setStartingPositions(playerPositions);
        builder.buildWalls();

        GameBuilder gameBuilder = new RaceGameBuilder(playerNames, dimension);
        gameBuilder.withBuilder(builder);
        return gameBuilder.buildGame();
    }
}
