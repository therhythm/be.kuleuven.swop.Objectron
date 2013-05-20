package be.kuleuven.swop.objectron.domain.grid;

import be.kuleuven.swop.objectron.domain.Wall;
import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.item.effect.PrimaryPowerFailure;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.square.SquareObserver;
import be.kuleuven.swop.objectron.domain.util.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Nik Torfs
 *         Date: 19/05/13
 *         Time: 21:22
 */
public class GridObjectMother {
    public static Grid normalGrid(GridBuilder builder, SquareObserver observer) throws GridTooSmallException {
        builder.initGrid();
        builder.buildWalls();
        builder.buildItems();
        builder.addObserver(observer);
        return builder.buildGrid();
    }


    // Contstruct a grid without walls and powerfailures
    public static Grid gridWithoutWallsPowerFailures(GridBuilder builder) throws GridTooSmallException {
        builder.initGrid();
        builder.buildItems();
        return builder.buildGrid();
    }

    // Contstruct a grid with specified walls and without powerfailures and Items
    public static Grid gridWithSpecifiedWallsWithoutItemsAndPowerFailures(GridBuilder builder,
                                                                          List<Position> wallPositions) throws
            GridTooSmallException {
        builder.initGrid();
        List<Wall> walls = new ArrayList<>();

        for (Position position : wallPositions) {
            Wall wall = new Wall();
            wall.addSquare(builder.buildGrid().getSquareAtPosition(position));
            builder.buildGrid().getSquareAtPosition(position).addObstruction(wall);
            walls.add(wall);
        }
        builder.buildWalls(walls);

        return builder.buildGrid();
    }

    // Contstruct a grid with specified walls and without powerfailures
    public static Grid gridWithSpecifiedWallsPowerFailures(GridBuilder builder, List<Position> wallPositions) throws
            GridTooSmallException {
        builder.initGrid();
        List<Wall> walls = new ArrayList<Wall>();

        for (Position position : wallPositions) {
            Wall wall = new Wall();
            wall.addSquare(builder.buildGrid().getSquareAtPosition(position));
            builder.buildGrid().getSquareAtPosition(position).addObstruction(wall);
            walls.add(wall);
        }
        builder.buildWalls(walls);
        builder.buildItems();

        return builder.buildGrid();
    }

    // Contstruct a grid with specified walls and without powerfailures
    public static Grid gridWithSpecifiedWallsPowerFailuresItems(GridBuilder builder,
                                                                List<Position> wallPositions) throws
            GridTooSmallException {
        builder.initGrid();
        List<Wall> walls = new ArrayList<Wall>();

        for (Position position : wallPositions) {
            Wall wall = new Wall();
            wall.addSquare(builder.buildGrid().getSquareAtPosition(position));
            builder.buildGrid().getSquareAtPosition(position).addObstruction(wall);
            walls.add(wall);
        }
        builder.buildWalls(walls);

        return builder.buildGrid();
    }

    // Contstruct a grid without powerfailures
    public static Grid gridWithoutPowerFailures(GridBuilder builder) throws GridTooSmallException {
        builder.initGrid();
        builder.buildItems();
        builder.buildWalls();
        return builder.buildGrid();
    }

    // Contstruct a grid without walls, items and powerfailures
    public static Grid gridWithoutWallsItemsPowerFailures(GridBuilder builder) throws GridTooSmallException {
        builder.initGrid();
        return builder.buildGrid();
    }

    //construct a grid without walls
    public static Grid gridWithoutWalls(GridBuilder builder) throws GridTooSmallException {
        builder.initGrid();
        builder.buildItems();
        return builder.buildGrid();
    }


    //construct a grid without items
    public static Grid gridWithoutItems(GridBuilder builder) throws GridTooSmallException {
        builder.initGrid();
        builder.buildWalls();
        return builder.buildGrid();
    }

    // construct a grid without power , walls or items
    public static Grid unpoweredGridWithoutWallsWithoutItems(GridBuilder builder) throws GridTooSmallException {
        builder.initGrid();
        Grid g = builder.buildGrid();

        // powerfailure on every square
        for (int x = 0; x < g.getDimension().getWidth(); x++) {
            for (int y = 0; y < g.getDimension().getHeight(); y++) {
              //  new PrimaryPowerFailure(g.getSquareAtPosition(new Position(x, y)));
            }
        }
        return g;
    }

    // construct a grid without power
    public static Grid unpoweredGrid(GridBuilder builder) throws GridTooSmallException {
        builder.initGrid();
        builder.buildWalls();
        builder.buildItems();
        Grid g = builder.buildGrid();

        // powerfailure on every square
        for (int x = 0; x < g.getDimension().getWidth(); x++) {
            for (int y = 0; y < g.getDimension().getHeight(); y++) {
               // new PrimaryPowerFailure(g.getSquareAtPosition(new Position(x, y)));
            }
        }
        return g;
    }
}
