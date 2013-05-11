package scenario;

import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.grid.GridBuilder;
import be.kuleuven.swop.objectron.domain.grid.GridFactory;
import be.kuleuven.swop.objectron.domain.util.Position;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author : Kasper Vervaecke
 *         Date: 11/05/13
 *         Time: 14:24
 */
public class TestUC_ChooseGridFromFile {

    private char[][] input;
    private GridBuilder gridBuilder;

    @Before
    public void setUp() {
        input = new char[][]{
                {'*', '*', '*', '*', '*', '*', '*'},
                {'*', ' ', ' ', ' ', '#', '2', '*'},
                {'*', ' ', ' ', ' ', '#', ' ', '*'},
                {'*', ' ', '#', ' ', '#', ' ', '*'},
                {'*', ' ', '#', ' ', ' ', ' ', '*'},
                {'*', '1', '#', ' ', ' ', ' ', '*'},
                {'*', '*', '*', '*', '*', '*', '*'}
        };
        gridBuilder = new FileGridBuilder();
    }

    @Test
    public void test_basic_flow() {
        gridBuilder.gridFromFile(input);
        Grid grid = gridBuilder.getGrid();

        //check dimensions
        assertTrue(grid.getDimension().getHeight() == 5);
        assertTrue(grid.getDimension().getWidth() == 5);

        //check walls
        assertTrue(grid.getSquareAtPosition(new Position(3,0)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(3,1)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(3,2)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(1,2)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(1,3)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(1,4)).isObstructed());

        //check start positions
        assertTrue(grid.getSquareAtPosition(new Position(0,4)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(4,0)).isObstructed());
    }
}
