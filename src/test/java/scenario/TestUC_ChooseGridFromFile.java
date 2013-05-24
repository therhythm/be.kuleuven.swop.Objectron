package scenario;

import be.kuleuven.swop.objectron.domain.exception.NumberOfPlayersException;
import be.kuleuven.swop.objectron.domain.grid.FileGridBuilder;
import be.kuleuven.swop.objectron.domain.exception.InvalidFileException;
import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.util.Position;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * @author : Kasper Vervaecke
 *         Date: 11/05/13
 *         Time: 14:24
 */
public class TestUC_ChooseGridFromFile {




    @Test
    public void test_basic_flow() throws InvalidFileException, NumberOfPlayersException {
        String input_file = ClassLoader.getSystemClassLoader().getResource("test_file.txt").getFile();
        File file = new File(input_file);
         FileGridBuilder gridBuilder = new FileGridBuilder(file.getAbsolutePath(), 2);
        gridBuilder.buildWalls();
        Grid grid = gridBuilder.buildGrid();

        //check dimensions
        assertTrue(grid.getDimension().getHeight() == 10);
        assertTrue(grid.getDimension().getWidth() == 10);

        //check walls
        assertTrue(grid.getSquareAtPosition(new Position(3, 2)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(3, 3)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(3, 4)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(3, 5)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(3, 6)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(3, 7)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(3, 8)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(3, 9)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(6, 0)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(6, 1)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(6, 2)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(6, 3)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(6, 4)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(6, 5)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(6, 6)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(6, 7)).isObstructed());
    }

    @Test (expected = InvalidFileException.class)
    public void test_unreachable_square() throws InvalidFileException, IOException, NumberOfPlayersException {
        String input_file = ClassLoader.getSystemClassLoader().getResource("test_file_unreachable_square.txt").getFile();
        File file = new File(input_file);
        new FileGridBuilder(file.getAbsolutePath(), 2);
    }

    @Test (expected = InvalidFileException.class)
    public void test_wrong_number_of_players() throws InvalidFileException, IOException, NumberOfPlayersException {
        String input_file = ClassLoader.getSystemClassLoader().getResource("test_file_multiple_starting_positions.txt").getFile();
        File file = new File(input_file);
        new FileGridBuilder(file.getAbsolutePath(), 2);
    }
}
