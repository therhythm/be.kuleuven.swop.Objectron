package scenario;

import be.kuleuven.swop.objectron.GameStateImpl;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.Square;
import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.exception.InventoryEmptyException;
import be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException;
import be.kuleuven.swop.objectron.domain.item.LightMine;
import be.kuleuven.swop.objectron.handler.StartGameHandler;
import be.kuleuven.swop.objectron.handler.UseItemHandler;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 13/03/13
 * Time: 14:11
 * To change this template use File | Settings | File Templates.
 */
public class TestUC_Start_New_Game {
    private String player1 = "player1";
    private String player2 = "player2";
    private StartGameHandler startGameHandler;

    @Before
    public void setUp() throws Exception {
        startGameHandler = new StartGameHandler();

    }

    @Test
    public void test_basic_flow() throws InventoryEmptyException, GridTooSmallException {
         int horizontal = 10;
         int vertical = 10;
         startGameHandler.startNewGame(player1,player2,horizontal,vertical);




    }

    @Test (expected = GridTooSmallException.class)
    public void test_invalid_size() throws InventoryEmptyException, GridTooSmallException {
        int horizontal = 9;
        int vertical = 10;
        startGameHandler.startNewGame(player1,player2,horizontal,vertical);


    }
}
