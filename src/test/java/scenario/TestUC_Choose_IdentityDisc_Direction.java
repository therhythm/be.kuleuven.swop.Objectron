package scenario;

import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.grid.GridFactory;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.domain.util.Position;
import be.kuleuven.swop.objectron.handler.EndTurnHandler;
import be.kuleuven.swop.objectron.handler.MovePlayerHandler;
import org.junit.Before;

/**
 * Created with IntelliJ IDEA.
 * User: Peter
 * Date: 8/04/13
 * Time: 20:30
 * To change this template use File | Settings | File Templates.
 */
public class TestUC_Choose_IdentityDisc_Direction {
    private EndTurnHandler endTurnHandler;
    private MovePlayerHandler movePlayerHandler;
    private GameState state;
    private Player player1;


    @Before
    public void setUp() throws GridTooSmallException {
        Dimension dimension = new Dimension(10, 10);
        Grid grid = GridFactory.gridWithoutWalls(dimension, new Position(0, 9), new Position(9, 0));
        state = new GameState("jos", "piet", dimension, grid);
        endTurnHandler = new EndTurnHandler(state);
        movePlayerHandler = new MovePlayerHandler(state);
        player1 = state.getCurrentPlayer();


    }


}
