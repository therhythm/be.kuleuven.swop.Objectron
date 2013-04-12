package be.kuleuven.objectron.item;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.*;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.grid.GridFactory;
import be.kuleuven.swop.objectron.domain.item.ChargedIdentityDiscBehavior;
import be.kuleuven.swop.objectron.domain.item.IdentityDisc;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.item.NormalIdentityDiscBehavior;
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

    private Dimension dimension;
    private Position p1Pos;
    private Position p2Pos;

    @Before
    public void setUp() throws GridTooSmallException {
        dimension = new Dimension(10, 10);

        p1Pos = new Position(0, 9);
        p2Pos = new Position(5, 9);

        grid = GridFactory.gridWithoutWallsItemsPowerFailures(dimension, p1Pos, p2Pos);
        state = new GameState("p1", "p2", p1Pos, p2Pos, grid);
        movePlayerHandler = new MovePlayerHandler(state);
        endTurnHandler = new EndTurnHandler(state);
        pickUpItemHandler = new PickUpItemHandler(state);
        useItemHandler = new UseItemHandler(state);
        player1 = state.getCurrentPlayer();
    }

    @Test
    public void test_Charged_identity_disc_no_hit() throws InventoryFullException, NotEnoughActionsException, SquareOccupiedException, NoItemSelectedException {
        Item identityDisc = new IdentityDisc(new ChargedIdentityDiscBehavior());
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
    public void test_UnCharged_identity_disc_hit_boundary() throws InventoryFullException, NotEnoughActionsException, SquareOccupiedException, NoItemSelectedException {
        Item identityDisc = new IdentityDisc(new NormalIdentityDiscBehavior());
        grid.getSquareAtPosition(new Position(0, 9)).addItem(identityDisc);
        pickUpItemHandler.pickUpItem(0);
        useItemHandler.selectItemFromInventory(0);
        useItemHandler.useCurrentIdentityDisc(Direction.DOWN);

        Square squareIdentityDisc = grid.getSquareAtPosition(new Position(0, 9));
        assertTrue(squareIdentityDisc.getAvailableItems().contains(identityDisc));
    }

    @Test
    public void test_Charged_identity_disc_hit() throws InventoryFullException, NotEnoughActionsException, SquareOccupiedException, InvalidMoveException, GameOverException, NoItemSelectedException {
        Item identityDisc = new IdentityDisc(new ChargedIdentityDiscBehavior());
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
        assertTrue(state.getCurrentTurn().getActionsRemaining() == 3);
        useItemHandler.useCurrentIdentityDisc(Direction.RIGHT);
        // System.out.println("remaining actions: " + state.getCurrentTurn().getActionsRemaining());
        assertTrue(state.getCurrentTurn().getActionsRemaining() == 5);
        for (int i = 1; i < 5; i++) {
            Square squareIdentityDisc = grid.getSquareAtPosition(new Position(i, 9));
            assertFalse(squareIdentityDisc.getAvailableItems().contains(identityDisc));
        }
        Square squareIdentityDisc = grid.getSquareAtPosition(new Position(6, 9));
        assertTrue(squareIdentityDisc.getAvailableItems().contains(identityDisc));

    }

    @Test
    public void test_Uncharged_IdentityDisc_Wall() throws InventoryFullException, NotEnoughActionsException, SquareOccupiedException, NoItemSelectedException, GridTooSmallException {
        List<Position> wallPositions = new ArrayList<Position>();
        wallPositions.add(new Position(0, 6));

        grid = GridFactory.gridWithSpecifiedWallsWithoutItemsAndPowerFailures(dimension, p1Pos, p2Pos, wallPositions);
        state = new GameState("p1", "p2", p1Pos, p2Pos, grid);
        pickUpItemHandler = new PickUpItemHandler(state);
        useItemHandler = new UseItemHandler(state);

        Item identityDisc = new IdentityDisc(new NormalIdentityDiscBehavior());
        grid.getSquareAtPosition(p1Pos).addItem(identityDisc);
        assertTrue(grid.getSquareAtPosition(p1Pos).getAvailableItems().size() > 0);
        pickUpItemHandler.pickUpItem(0);
        useItemHandler.selectItemFromInventory(0);
        useItemHandler.useCurrentIdentityDisc(Direction.UP);
        for (int i = 9; i > 7; i--) {
            Square squareIdentityDisc = grid.getSquareAtPosition(new Position(0, i));
            assertFalse(squareIdentityDisc.getAvailableItems().contains(identityDisc));
        }

        Square squareIdentityDisc = grid.getSquareAtPosition(new Position(0, 7));
        assertTrue(squareIdentityDisc.getAvailableItems().contains(identityDisc));
    }

    @Test
    public void test_Charged_IdentityDisc_Wall() throws InventoryFullException, NotEnoughActionsException, SquareOccupiedException, NoItemSelectedException, GridTooSmallException {
        List<Position> wallPositions = new ArrayList<Position>();
        wallPositions.add(new Position(0, 3));

        grid = GridFactory.gridWithSpecifiedWallsWithoutItemsAndPowerFailures(dimension, p1Pos, p2Pos, wallPositions);
        state = new GameState("p1", "p2", p1Pos, p2Pos, grid);
        pickUpItemHandler = new PickUpItemHandler(state);
        useItemHandler = new UseItemHandler(state);

        Item identityDisc = new IdentityDisc(new ChargedIdentityDiscBehavior());
        grid.getSquareAtPosition(p1Pos).addItem(identityDisc);
        assertTrue(grid.getSquareAtPosition(p1Pos).getAvailableItems().size()>0);
        pickUpItemHandler.pickUpItem(0);
        useItemHandler.selectItemFromInventory(0);
        useItemHandler.useCurrentIdentityDisc(Direction.UP);
        for (int i = 9; i > 4; i--) {
            Square squareIdentityDisc = grid.getSquareAtPosition(new Position(0, i));
            assertFalse(squareIdentityDisc.getAvailableItems().contains(identityDisc));
        }

        Square squareIdentityDisc = grid.getSquareAtPosition(new Position(0, 4));
        assertTrue(squareIdentityDisc.getAvailableItems().contains(identityDisc));
    }

    @Test
    public void test_string_contains() {
        Item item = new IdentityDisc(new NormalIdentityDiscBehavior());
        assertTrue(item.getName().contains("Identity Disc"));

        Item item2 = new IdentityDisc(new ChargedIdentityDiscBehavior());
        assertTrue(item2.getName().contains("Identity Disc"));
    }
}