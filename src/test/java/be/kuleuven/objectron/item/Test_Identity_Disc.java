package be.kuleuven.objectron.item;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.*;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.grid.GridFactory;
import be.kuleuven.swop.objectron.domain.item.IdentityDisc;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.domain.util.Position;
import be.kuleuven.swop.objectron.handler.EndTurnHandler;
import be.kuleuven.swop.objectron.handler.MovePlayerHandler;
import be.kuleuven.swop.objectron.handler.PickUpItemHandler;
import be.kuleuven.swop.objectron.handler.UseItemHandler;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 11/04/13
 * Time: 20:17
 * To change this template use File | Settings | File Templates.
 */
public class Test_Identity_Disc {
    private EndTurnHandler endTurnHandler;
    private MovePlayerHandler movePlayerHandler;
    private PickUpItemHandler pickUpItemHandler;
    private UseItemHandler useItemHandler;
    private GameState state;
    private Player player1;
    private Grid grid;

    @Before
    public void setUp() throws GridTooSmallException {
        Dimension dimension = new Dimension(10, 10);

        Position p1Pos = new Position(0, 9);
        Position p2Pos = new Position(5, 9);

        grid = GridFactory.gridWithoutWallsWithoutItems(dimension, p1Pos, p2Pos);
        state = new GameState("p1", "p2", p1Pos, p2Pos, grid);
        movePlayerHandler = new MovePlayerHandler(state);
        endTurnHandler = new EndTurnHandler(state);
        pickUpItemHandler = new PickUpItemHandler(state);
        useItemHandler = new UseItemHandler(state);
        player1 = state.getCurrentPlayer();
    }

    @Test
    public void test_Charged_identity_disc_no_hit() throws InventoryFullException, NotEnoughActionsException, SquareOccupiedException, NoItemSelectedException {
        Item identityDisc = new IdentityDisc(IdentityDisc.IdentityDiscTypeState.CHARGED_IDENTITY_DISC);
        grid.getSquareAtPosition(new Position(0, 9)).addItem(identityDisc);
        pickUpItemHandler.pickUpItem(0);
        useItemHandler.selectItemFromInventory(0);
        useItemHandler.useCurrentIdentityDisc(Direction.UP);
        for (int i = 9; i > 0; i--) {
            Square squareIdentityDisc = grid.getSquareAtPosition(new Position(0, i));
            assertFalse(squareIdentityDisc.getAvailableItems().contains(identityDisc));
        }

        Square squareIdentityDisc = grid.getSquareAtPosition(new Position(0, 0));
        assertTrue(squareIdentityDisc.getAvailableItems().contains(identityDisc));
    }

    @Test
    public void test_Charged_identity_disc_hit() throws InventoryFullException, NotEnoughActionsException, SquareOccupiedException, InvalidMoveException, GameOverException, NoItemSelectedException {
        Item identityDisc = new IdentityDisc(IdentityDisc.IdentityDiscTypeState.CHARGED_IDENTITY_DISC);
        grid.getSquareAtPosition(new Position(0, 9)).addItem(identityDisc);
        pickUpItemHandler.pickUpItem(0);

        movePlayerHandler.move(Direction.RIGHT);
        endTurnHandler.endTurn();
        movePlayerHandler.move(Direction.RIGHT);
        Player player2 = state.getCurrentPlayer();
        // System.out.println("test: " + state.getCurrentPlayer().getCurrentSquare());
        endTurnHandler.endTurn();
        useItemHandler.selectItemFromInventory(0);
        //System.out.println("remaining actions: " + state.getCurrentTurn().getActionsRemaining());
        assertTrue(state.getCurrentTurn().getActionsRemaining()==3);
        useItemHandler.useCurrentIdentityDisc(Direction.RIGHT);
       // System.out.println("remaining actions: " + state.getCurrentTurn().getActionsRemaining());
        assertTrue(state.getCurrentTurn().getActionsRemaining()==5);
        for (int i = 1; i < 5; i++) {
            Square squareIdentityDisc = grid.getSquareAtPosition(new Position(i, 9));
            assertFalse(squareIdentityDisc.getAvailableItems().contains(identityDisc));
        }
        Square squareIdentityDisc = grid.getSquareAtPosition(new Position(6, 9));
        assertTrue(squareIdentityDisc.getAvailableItems().contains(identityDisc));

    }
}
