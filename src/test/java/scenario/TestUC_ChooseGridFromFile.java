package scenario;

import be.kuleuven.swop.objectron.domain.grid.FileGridBuilder;
import be.kuleuven.swop.objectron.domain.grid.GeneratedGridBuilder;
import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.util.Position;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertTrue;

/**
 * @author : Kasper Vervaecke
 *         Date: 11/05/13
 *         Time: 14:24
 */
public class TestUC_ChooseGridFromFile {

    private FileGridBuilder gridBuilder;

    @Before
    public void setUp() throws IOException {
        gridBuilder = new FileGridBuilder("test_file.txt");
    }

    @Test
    public void test_basic_flow() {
        gridBuilder.initGrid();
        gridBuilder.buildWalls();
        Grid grid = gridBuilder.buildGrid();

        //check dimensions
        assertTrue(grid.getDimension().getHeight() == 10);
        assertTrue(grid.getDimension().getWidth() == 10);

        //check walls
        assertTrue(grid.getSquareAtPosition(new Position(2, 3)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(3, 3)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(4, 3)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(5, 3)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(6, 3)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(7, 3)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(8, 3)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(9, 3)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(0, 6)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(1, 6)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(2, 6)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(3, 6)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(4, 6)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(5, 6)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(6, 6)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(7, 6)).isObstructed());
    }
}
