package be.kuleuven.swop.objectron.domain.grid;

import be.kuleuven.swop.objectron.domain.Wall;
import be.kuleuven.swop.objectron.domain.square.SquareObserver;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.domain.util.GridFileReader;
import be.kuleuven.swop.objectron.domain.util.Position;

import java.io.IOException;
import java.util.List;

/**
 * @author : Kasper Vervaecke
 *         Date: 11/05/13
 *         Time: 15:49
 */
public class FileGridBuilder implements GridBuilder {
    public FileGridBuilder(String file) throws IOException {
        GridFileReader fileReader = new GridFileReader();
        char[][] input = fileReader.readGridFile(file);
    }

    @Override
    public void setDimension(Dimension dimension) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setStartingPositions(Position playerOnePosition, Position playerTwoPosition) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void buildWalls() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void buildWalls(List<Wall> walls) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void buildItems() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addObserver(SquareObserver observer) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void initGrid(int powerFailureChance) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Grid getGrid() {
        throw new RuntimeException("Not yet implemented.");
    }
}
