package be.kuleuven.swop.objectron.domain.grid;

import be.kuleuven.swop.objectron.domain.Wall;
import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.square.SquareObserver;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.domain.util.Position;

import java.util.List;

/**
 * @author : Kasper Vervaecke
 *         Date: 13/05/13
 *         Time: 10:13
 */

public interface GridBuilder {

    public void setStartingPositions(List<Position> positions);

    public void buildWalls();

    public void buildWalls(List<Wall> walls);

    public void buildItems();

    public void addObserver(SquareObserver observer);

    public void initGrid(int powerFailureChance);

    public Grid buildGrid();

}
