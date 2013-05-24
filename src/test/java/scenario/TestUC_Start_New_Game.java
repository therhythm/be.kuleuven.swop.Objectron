package scenario;


import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.exception.InvalidFileException;
import be.kuleuven.swop.objectron.domain.exception.InventoryEmptyException;
import be.kuleuven.swop.objectron.domain.exception.NumberOfPlayersException;
import be.kuleuven.swop.objectron.domain.grid.FileGridBuilder;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.handler.StartGameHandler;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 13/03/13
 * Time: 14:11
 * To change this template use File | Settings | File Templates.
 */
public class TestUC_Start_New_Game {
    private StartGameHandler startGameHandler;
    private List<String> players = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        startGameHandler = new StartGameHandler();
        players.add("player 1");
        players.add("player 2");

    }

    @Test
    public void test_basic_flow() throws InventoryEmptyException, GridTooSmallException, InvalidFileException, NumberOfPlayersException {
        Dimension dimension = new Dimension(10, 10);
        startGameHandler.startNewRaceGame(players, dimension, "");
    }

    @Test(expected = GridTooSmallException.class)
    public void test_invalid_size() throws InventoryEmptyException, GridTooSmallException, InvalidFileException, NumberOfPlayersException {
        Dimension dimension = new Dimension(9, 10);
        startGameHandler.startNewRaceGame(players, dimension, "");
    }

    @Test(expected = NumberOfPlayersException.class)
    public void test_too_many_for_race() throws InvalidFileException, NumberOfPlayersException, GridTooSmallException {
        Dimension dimension = new Dimension(10,10);
        players.add("player 3");
        startGameHandler.startNewRaceGame(players, dimension, "");
    }

    @Test
    public void test_ctf_with_four() throws InvalidFileException, NumberOfPlayersException, GridTooSmallException {
        Dimension dimension = new Dimension(10,10);
        players.add("player 3");
        players.add("player 4");
        startGameHandler.startNewCTFGame(players, dimension, "");
    }

    @Test (expected = NumberOfPlayersException.class)
    public void test_too_many_for_ctf() throws InvalidFileException, NumberOfPlayersException, GridTooSmallException {
        Dimension dimension = new Dimension(10,10);
        players.add("player 3");
        players.add("player 4");
        players.add("player 5");
        startGameHandler.startNewCTFGame(players, dimension, "");
    }

    @Test(expected = NumberOfPlayersException.class)
    public void test_file_start_race_too_many_players() throws InvalidFileException, NumberOfPlayersException, GridTooSmallException {
        String input_file = ClassLoader.getSystemClassLoader().getResource("test_file.txt").getFile();
        File file = new File(input_file);
        new FileGridBuilder(file.getAbsolutePath(), 3);
    }
}
