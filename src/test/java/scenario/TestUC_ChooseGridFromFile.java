package scenario;

import be.kuleuven.swop.objectron.domain.grid.FileGridBuilder;
import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.util.GridFileReader;
import be.kuleuven.swop.objectron.domain.util.Position;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * @author : Kasper Vervaecke
 *         Date: 11/05/13
 *         Time: 14:24
 */
public class TestUC_ChooseGridFromFile {

    private char[][] input;
    private FileGridBuilder gridBuilder;

    @Before
    public void setUp() throws IOException {
        String input_file = ClassLoader.getSystemClassLoader().getResource("test_file.txt").getFile();
        gridBuilder = new FileGridBuilder(input_file);
    }

    @Test
    public void test_basic_flow() {
        gridBuilder.initGrid(Square.POWER_FAILURE_CHANCE);
        gridBuilder.buildWalls();
        Grid grid = gridBuilder.getGrid();

        //check dimensions
        assertTrue(grid.getDimension().getHeight() == 10);
        assertTrue(grid.getDimension().getWidth() == 10);

        //check walls
        assertTrue(grid.getSquareAtPosition(new Position(3,2)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(3,3)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(3,4)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(3,5)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(3,6)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(3,7)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(3,8)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(3,9)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(6,0)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(6,1)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(6,2)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(6,3)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(6,4)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(6,5)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(6,6)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(6,7)).isObstructed());

        //check start positions
        assertTrue(grid.getSquareAtPosition(new Position(0,9)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(9,0)).isObstructed());
    }
}
