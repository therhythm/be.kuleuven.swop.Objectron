package scenario;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.exception.*;
import be.kuleuven.swop.objectron.domain.gamestate.Game;
import be.kuleuven.swop.objectron.domain.gamestate.GameObjectMother;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.grid.*;
import be.kuleuven.swop.objectron.domain.item.IdentityDisc;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.item.NormalIdentityDiscBehavior;
import be.kuleuven.swop.objectron.domain.item.UnchargedIdentityDisc;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.domain.util.Position;
import be.kuleuven.swop.objectron.handler.EndTurnHandler;
import be.kuleuven.swop.objectron.handler.MovePlayerHandler;
import be.kuleuven.swop.objectron.handler.PickUpItemHandler;
import be.kuleuven.swop.objectron.handler.UseItemHandler;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
    private PickUpItemHandler pickUpItemHandler;
    private UseItemHandler useItemHandler;
    private Game state;
    private Grid grid;

    @Before
    public void setUp() throws GridTooSmallException, NumberOfPlayersException {
        Dimension dimension = new Dimension(10, 10);

        Position p1Pos = new Position(0, 9);
        Position p2Pos = new Position(2, 9);
        List<Position> positions = new ArrayList<>();
        positions.add(p1Pos);
        positions.add(p2Pos);

        List<String> playerNames = new ArrayList<>();
        playerNames.add("p1");
        playerNames.add("p2");

        state = GameObjectMother.raceGameWithoutWallsItemsPowerFailures(dimension, playerNames, positions);
        grid = state.getGrid();

        movePlayerHandler = new MovePlayerHandler(state);
        endTurnHandler = new EndTurnHandler(state);
        pickUpItemHandler = new PickUpItemHandler(state);
        useItemHandler = new UseItemHandler(state);
    }

    @Test
    public void basic_flow() throws InventoryFullException, NotEnoughActionsException, SquareOccupiedException,
            NoItemSelectedException, GameOverException {
        Item identityDisc = new UnchargedIdentityDisc();
        grid.getSquareAtPosition(new Position(0, 9)).addItem(identityDisc);
        pickUpItemHandler.pickUpItem(0);
        useItemHandler.selectItemFromInventory(0);
        useItemHandler.useCurrentIdentityDisc(Direction.UP);
        for (int i = 9; i > 5; i--) {
            Square squareIdentityDisc = grid.getSquareAtPosition(new Position(0, i));
            assertFalse(squareIdentityDisc.getAvailableItems().contains(identityDisc));
        }

        Square squareIdentityDisc = grid.getSquareAtPosition(new Position(0, 5));
        assertTrue(squareIdentityDisc.getAvailableItems().contains(identityDisc));
    }

    @Test
    public void alternate_flow_player_hit() throws InventoryFullException, NotEnoughActionsException,
            SquareOccupiedException, InvalidMoveException, GameOverException, NoItemSelectedException {
        Item identityDisc = new UnchargedIdentityDisc();
        grid.getSquareAtPosition(new Position(0, 9)).addItem(identityDisc);
        pickUpItemHandler.pickUpItem(0);

        movePlayerHandler.move(Direction.RIGHT);
        endTurnHandler.endTurn();
        movePlayerHandler.move(Direction.RIGHT);

        System.out.println("test: " + state.getTurnManager().getCurrentTurn().getCurrentPlayer().getCurrentSquare());

        endTurnHandler.endTurn();

        System.out.println("test: " + state.getTurnManager().getCurrentTurn().getCurrentPlayer().getCurrentSquare());
        useItemHandler.selectItemFromInventory(0);

        assertTrue(state.getTurnManager().getCurrentTurn().getActionsRemaining() == Turn.ACTIONS_EACH_TURN);
        useItemHandler.useCurrentIdentityDisc(Direction.RIGHT);
        assertTrue(state.getTurnManager().getCurrentTurn().getActionsRemaining() == Turn.ACTIONS_EACH_TURN * 2 - 1);

        for (int i = 1; i < 3; i++) {
            Square squareIdentityDisc = grid.getSquareAtPosition(new Position(i, 9));
            assertFalse(squareIdentityDisc.getAvailableItems().contains(identityDisc));
        }
        Square squareIdentityDisc = grid.getSquareAtPosition(new Position(3, 9));
        assertTrue(squareIdentityDisc.getAvailableItems().contains(identityDisc));

    }


}
