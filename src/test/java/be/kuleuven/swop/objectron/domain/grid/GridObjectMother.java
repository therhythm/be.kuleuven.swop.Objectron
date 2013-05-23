package be.kuleuven.swop.objectron.domain.grid;


import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.util.Position;

import java.util.List;

/**
 * @author : Nik Torfs
 *         Date: 19/05/13
 *         Time: 21:22
 */
public class GridObjectMother {

    // Contstruct a grid without walls and powerfailures
    public static Grid gridWithoutWallsPowerFailures(GridBuilder builder) throws GridTooSmallException {
        builder.initGrid(0);
        builder.buildItems();
        return builder.buildGrid();
    }

    // Contstruct a grid with specified walls and without powerfailures
    public static Grid gridWithSpecifiedWallsPowerFailuresItems(GridBuilder builder,
                                                                List<List<Position>> wallPositions) throws
            GridTooSmallException {
        builder.initGrid(0);
        builder.buildWalls(wallPositions);

        return builder.buildGrid();
    }

    // Contstruct a grid without walls, items and powerfailures
    public static Grid gridWithoutWallsItemsPowerFailures(GridBuilder builder) throws GridTooSmallException {
        builder.initGrid(0);
        return builder.buildGrid();
    }
}
