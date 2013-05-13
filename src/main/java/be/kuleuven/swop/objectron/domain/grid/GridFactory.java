package be.kuleuven.swop.objectron.domain.grid;

import be.kuleuven.swop.objectron.domain.Wall;
import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.square.PowerFailure;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.square.SquareObserver;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.domain.util.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Nik
 * Date: 4/4/13
 * Time: 2:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class GridFactory {

    private GridBuilder builder;

    public GridFactory(GridBuilder builder) {
        this.builder = builder;
    }

    public Grid normalGrid(Dimension dimension, Position p1, Position p2, SquareObserver observer) throws GridTooSmallException {
        builder.setDimension(dimension);
        builder.setStartingPositions(p1, p2);
        builder.initGrid(Square.POWER_FAILURE_CHANCE);
        builder.buildWalls();
        builder.buildItems();
        builder.addObserver(observer);
        return builder.getGrid();
    }

    // Contstruct a grid without walls and powerfailures
    public Grid gridWithoutWallsPowerFailures(Dimension dimension, Position p1, Position p2) throws GridTooSmallException {
        builder.setDimension(dimension);
        builder.setStartingPositions(p1, p2);
        builder.initGrid(0);
        builder.buildItems();
        return builder.getGrid();
    }

    // Contstruct a grid with specified walls and without powerfailures and Items
    public Grid gridWithSpecifiedWallsWithoutItemsAndPowerFailures(Dimension dimension, Position p1, Position p2, List<Position> wallPositions) throws GridTooSmallException {
        builder.setDimension(dimension);
        builder.setStartingPositions(p1, p2);
        builder.initGrid(0);
        List<Wall> walls = new ArrayList<Wall>();

        for (Position position : wallPositions) {
            Wall wall = new Wall();
            wall.addSquare(builder.getGrid().getSquareAtPosition(position));
            builder.getGrid().getSquareAtPosition(position).addObstruction(wall);
            walls.add(wall);
        }
        builder.buildWalls(walls);

        return builder.getGrid();
    }

    // Contstruct a grid with specified walls and without powerfailures
    public Grid gridWithSpecifiedWallsPowerFailures(Dimension dimension, Position p1, Position p2, List<Position> wallPositions) throws GridTooSmallException {
        builder.setDimension(dimension);
        builder.setStartingPositions(p1, p2);
        builder.initGrid(0);
        List<Wall> walls = new ArrayList<Wall>();

        for (Position position : wallPositions) {
            Wall wall = new Wall();
            wall.addSquare(builder.getGrid().getSquareAtPosition(position));
            builder.getGrid().getSquareAtPosition(position).addObstruction(wall);
            walls.add(wall);
        }
        builder.buildWalls(walls);
        builder.buildItems();

        return builder.getGrid();
    }

    // Contstruct a grid with specified walls and without powerfailures
    public Grid gridWithSpecifiedWallsPowerFailuresItems(Dimension dimension, Position p1, Position p2, List<Position> wallPositions) throws GridTooSmallException {
        builder.setDimension(dimension);
        builder.setStartingPositions(p1, p2);
        builder.initGrid(0);
        List<Wall> walls = new ArrayList<Wall>();

        for (Position position : wallPositions) {
            Wall wall = new Wall();
            wall.addSquare(builder.getGrid().getSquareAtPosition(position));
            builder.getGrid().getSquareAtPosition(position).addObstruction(wall);
            walls.add(wall);
        }
        builder.buildWalls(walls);

        return builder.getGrid();
    }

    // Contstruct a grid without powerfailures
    public Grid gridWithoutPowerFailures(Dimension dimension, Position p1, Position p2) throws GridTooSmallException {
        builder.setDimension(dimension);
        builder.setStartingPositions(p1, p2);
        builder.initGrid(0);
        builder.buildItems();
        builder.buildWalls();
        return builder.getGrid();
    }

    // Contstruct a grid without walls, items and powerfailures
    public Grid gridWithoutWallsItemsPowerFailures(Dimension dimension, Position p1, Position p2) throws GridTooSmallException {
        builder.setDimension(dimension);
        builder.setStartingPositions(p1, p2);
        builder.initGrid(0);
        return builder.getGrid();
    }

    //construct a grid without walls
    public Grid gridWithoutWalls(Dimension dimension, Position p1, Position p2) throws GridTooSmallException {
        builder.setDimension(dimension);
        builder.setStartingPositions(p1, p2);
        builder.initGrid(Square.POWER_FAILURE_CHANCE);
        builder.buildItems();
        return builder.getGrid();
    }


    //construct a grid without items
    public Grid gridWithoutItems(Dimension dimension, Position p1, Position p2) throws GridTooSmallException {
        builder.setDimension(dimension);
        builder.setStartingPositions(p1, p2);
        builder.initGrid(Square.POWER_FAILURE_CHANCE);
        builder.buildWalls();
        return builder.getGrid();
    }

    // construct a grid without power , walls or items
    public Grid unpoweredGridWithoutWallsWithoutItems(Dimension dimension, Position p1, Position p2) throws GridTooSmallException {
        builder.setDimension(dimension);
        builder.setStartingPositions(p1, p2);
        builder.initGrid(Square.POWER_FAILURE_CHANCE);
        Grid g = builder.getGrid();

        // powerfailure on every square
        for (int x = 0; x < dimension.getWidth(); x++) {
            for (int y = 0; y < dimension.getHeight(); y++) {
                g.getSquareAtPosition(new Position(x, y)).receivePowerFailure(PowerFailure.PF_PRIMARY_TURNS, PowerFailure.PF_PRIMARY_ACTIONS);
            }
        }
        return g;
    }

    // construct a grid without power
    public Grid unpoweredGrid(Dimension dimension, Position p1, Position p2) throws GridTooSmallException {
        builder.setDimension(dimension);
        builder.setStartingPositions(p1, p2);
        builder.initGrid(Square.POWER_FAILURE_CHANCE);
        builder.buildWalls();
        builder.buildItems();
        Grid g = builder.getGrid();

        // powerfailure on every square
        for (int x = 0; x < dimension.getWidth(); x++) {
            for (int y = 0; y < dimension.getHeight(); y++) {
                g.getSquareAtPosition(new Position(x, y)).receivePowerFailure(PowerFailure.PF_PRIMARY_TURNS, PowerFailure.PF_PRIMARY_ACTIONS);
            }
        }
        return g;
    }
}
