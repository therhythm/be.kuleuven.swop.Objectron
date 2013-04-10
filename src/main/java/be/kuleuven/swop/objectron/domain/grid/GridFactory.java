package be.kuleuven.swop.objectron.domain.grid;

import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.domain.util.Position;

/**
 * Created with IntelliJ IDEA.
 * User: Nik
 * Date: 4/4/13
 * Time: 2:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class GridFactory {
    public static Grid normalGrid(Dimension dimension, Position p1, Position p2) throws GridTooSmallException {
        GridBuilder builder = new GridBuilder(dimension, p1, p2);
        builder.buildWalls();
        builder.buildItems();
        return builder.getGrid();
    }

    public static Grid emptyGrid(Dimension dimension, Position p1, Position p2) throws GridTooSmallException {
        GridBuilder builder = new GridBuilder(dimension, p1, p2);
        return builder.getGrid();
    }

    public static Grid gridWithoutWalls(Dimension dimension, Position p1, Position p2) throws GridTooSmallException {
        GridBuilder builder = new GridBuilder(dimension, p1, p2);
        builder.buildItems();
        return builder.getGrid();
    }



    public static Grid gridWithoutItems(Dimension dimension, Position p1, Position p2) throws GridTooSmallException {
        GridBuilder builder = new GridBuilder(dimension, p1, p2);
        builder.buildWalls();
        return builder.getGrid();
    }

    public static Grid gridWithoutWallsWithoutItems(Dimension dimension, Position p1, Position p2) throws GridTooSmallException {
        GridBuilder builder = new GridBuilder(dimension, p1, p2);
        return builder.getGrid();
    }

    public static Grid unpoweredGrid(Dimension dimension, Position p1, Position p2) throws GridTooSmallException {
        GridBuilder builder = new GridBuilder(dimension, p1, p2);
        builder.buildWalls();
        builder.buildItems();
        Grid g = builder.getGrid();

        // powerfailure on every square
        for(int x = 0; x < dimension.getWidth(); x++){
            for(int y = 0; y < dimension.getHeight(); y++){
                g.getSquareAtPosition(new Position(x,y)).receivePowerFailure();
            }
        }
        return g;
    }
}
