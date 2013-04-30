package be.kuleuven.objectron.item.forceField;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.*;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.grid.GridFactory;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.item.forceField.ForceField;
import be.kuleuven.swop.objectron.domain.item.forceField.ForceFieldArea;
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
 * Date: 28/04/13
 * Time: 15:56
 * To change this template use File | Settings | File Templates.
 */
public class Test_Force_Field {
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

        p1Pos = new Position(4, 5);
        p2Pos = new Position(7, 6);

        grid = GridFactory.gridWithoutWallsItemsPowerFailures(dimension, p1Pos, p2Pos);
        state = new GameState("p1", "p2", p1Pos, p2Pos, grid);
        movePlayerHandler = new MovePlayerHandler(state);
        endTurnHandler = new EndTurnHandler(state);
        pickUpItemHandler = new PickUpItemHandler(state);
        useItemHandler = new UseItemHandler(state);
        player1 = state.getCurrentPlayer();
    }

    @Test(expected = InvalidMoveException.class)
    public void test_Force_Field_move_in_force_field_Direction_Left() throws InventoryFullException, NotEnoughActionsException, NoItemSelectedException, SquareOccupiedException, InvalidMoveException, GameOverException {
        Square squareFF1 = grid.getSquareAtPosition(new Position(4, 5));
        Square squareFF2 = grid.getSquareAtPosition(new Position(8, 5));
        ForceFieldArea forceFieldArea = grid.getForceFieldArea();

        Item forceField1 = new ForceField(forceFieldArea);
        Item forceField2 = new ForceField(forceFieldArea);

        squareFF1.addItem(forceField1);
        squareFF2.addItem(forceField2);

        forceFieldArea.placeForceField(forceField1, squareFF1);
        forceFieldArea.placeForceField(forceField2, squareFF2);
        Turn currentTurn = state.getCurrentTurn();
        //currentTurn.attach(forceFieldArea);

        pickUpItemHandler.pickUpItem(0);
        movePlayerHandler.move(Direction.RIGHT);
        useItemHandler.selectItemFromInventory(0);
        assertFalse(squareFF2.isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(6, 5)).isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(7, 5)).isObstructed());
        useItemHandler.useCurrentItem();
        assertTrue(squareFF1.isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(6, 5)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(7, 5)).isObstructed());

        assertTrue(squareFF2.isObstructed());
        endTurnHandler.endTurn();
        movePlayerHandler.move(Direction.UP);
        System.out.println(state.getCurrentPlayer().getCurrentSquare().getPosition());
    }

    @Test
    public void test_Force_Field_Direction_Left_Up() throws InventoryFullException, NotEnoughActionsException, NoItemSelectedException, SquareOccupiedException, InvalidMoveException, GameOverException {
        Square squareFF1 = grid.getSquareAtPosition(new Position(4, 5));
        Square squareFF2 = grid.getSquareAtPosition(new Position(8, 2));
        ForceFieldArea forceFieldArea = grid.getForceFieldArea();

        Item forceField1 = new ForceField(forceFieldArea);
        Item forceField2 = new ForceField(forceFieldArea);

        squareFF1.addItem(forceField1);
        squareFF2.addItem(forceField2);

        forceFieldArea.placeForceField(forceField1, squareFF1);
        forceFieldArea.placeForceField(forceField2, squareFF2);
        Turn currentTurn = state.getCurrentTurn();
        //currentTurn.attach(forceFieldArea);

        pickUpItemHandler.pickUpItem(0);
        movePlayerHandler.move(Direction.RIGHT);
        useItemHandler.selectItemFromInventory(0);
        assertFalse(squareFF2.isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(6, 4)).isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(7, 3)).isObstructed());
        useItemHandler.useCurrentItem();
        assertTrue(squareFF1.isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(6, 4)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(7, 3)).isObstructed());

        assertTrue(squareFF2.isObstructed());
    }

    @Test
    public void test_Force_Field_Direction_Up() throws InventoryFullException, NotEnoughActionsException, NoItemSelectedException, SquareOccupiedException, InvalidMoveException, GameOverException {
        Square squareFF1 = grid.getSquareAtPosition(new Position(4, 5));
        Square squareFF2 = grid.getSquareAtPosition(new Position(5, 2));
        ForceFieldArea forceFieldArea = grid.getForceFieldArea();

        Item forceField1 = new ForceField(forceFieldArea);
        Item forceField2 = new ForceField(forceFieldArea);

        squareFF1.addItem(forceField1);
        squareFF2.addItem(forceField2);

        forceFieldArea.placeForceField(forceField1, squareFF1);
        forceFieldArea.placeForceField(forceField2, squareFF2);
        Turn currentTurn = state.getCurrentTurn();
        //currentTurn.attach(forceFieldArea);

        pickUpItemHandler.pickUpItem(0);
        movePlayerHandler.move(Direction.RIGHT);
        assertTrue(currentTurn.getCurrentPlayer().getCurrentSquare().equals(grid.getSquareAtPosition(new Position(5, 5))));
        useItemHandler.selectItemFromInventory(0);
        assertFalse(squareFF2.isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(5, 4)).isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(5, 3)).isObstructed());
        useItemHandler.useCurrentItem();
        assertTrue(squareFF1.isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(5, 4)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(5, 3)).isObstructed());

        assertTrue(squareFF2.isObstructed());
    }

    @Test
    public void test_Force_Field_Direction_Up_Right() throws InventoryFullException, NotEnoughActionsException, NoItemSelectedException, SquareOccupiedException, InvalidMoveException, GameOverException {
        Square squareFF1 = grid.getSquareAtPosition(new Position(4, 5));
        Square squareFF2 = grid.getSquareAtPosition(new Position(2, 2));
        ForceFieldArea forceFieldArea = grid.getForceFieldArea();

        Item forceField1 = new ForceField(forceFieldArea);
        Item forceField2 = new ForceField(forceFieldArea);

        squareFF1.addItem(forceField1);
        squareFF2.addItem(forceField2);

        forceFieldArea.placeForceField(forceField1, squareFF1);
        forceFieldArea.placeForceField(forceField2, squareFF2);
        Turn currentTurn = state.getCurrentTurn();
        //currentTurn.attach(forceFieldArea);

        pickUpItemHandler.pickUpItem(0);
        movePlayerHandler.move(Direction.RIGHT);
        useItemHandler.selectItemFromInventory(0);
        assertFalse(squareFF2.isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(4, 4)).isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(3, 3)).isObstructed());
        useItemHandler.useCurrentItem();
        assertTrue(squareFF1.isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(4, 4)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(3, 3)).isObstructed());

        assertTrue(squareFF2.isObstructed());
    }

    @Test
    public void test_Force_Field_Direction_Left_No_ForceField() throws InventoryFullException, NotEnoughActionsException, NoItemSelectedException, SquareOccupiedException, InvalidMoveException, GameOverException {
        Square squareFF1 = grid.getSquareAtPosition(new Position(4, 5));
        Square squareFF2 = grid.getSquareAtPosition(new Position(9, 5));
        ForceFieldArea forceFieldArea = grid.getForceFieldArea();

        Item forceField1 = new ForceField(forceFieldArea);
        Item forceField2 = new ForceField(forceFieldArea);

        squareFF1.addItem(forceField1);
        squareFF2.addItem(forceField2);

        forceFieldArea.placeForceField(forceField1, squareFF1);
        forceFieldArea.placeForceField(forceField2, squareFF2);
        Turn currentTurn = state.getCurrentTurn();
        // currentTurn.attach(forceFieldArea);

        pickUpItemHandler.pickUpItem(0);
        movePlayerHandler.move(Direction.RIGHT);
        useItemHandler.selectItemFromInventory(0);
        assertFalse(squareFF2.isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(6, 5)).isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(7, 5)).isObstructed());
        useItemHandler.useCurrentItem();
        assertFalse(grid.getSquareAtPosition(new Position(6, 5)).isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(7, 5)).isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(8, 5)).isObstructed());
        assertFalse(squareFF2.isObstructed());
    }

    @Test
    public void test_Force_Field_Direction_Left_Activate_Disactivate() throws InventoryFullException, NotEnoughActionsException, NoItemSelectedException, SquareOccupiedException, InvalidMoveException, GameOverException {
        Square squareFF1 = grid.getSquareAtPosition(new Position(4, 5));
        Square squareFF2 = grid.getSquareAtPosition(new Position(8, 5));
        ForceFieldArea forceFieldArea = grid.getForceFieldArea();

        Item forceField1 = new ForceField(forceFieldArea);
        Item forceField2 = new ForceField(forceFieldArea);

        squareFF1.addItem(forceField1);
        squareFF2.addItem(forceField2);

        forceFieldArea.placeForceField(forceField1, squareFF1);
        forceFieldArea.placeForceField(forceField2, squareFF2);
        Turn currentTurn = state.getCurrentTurn();
        //currentTurn.attach(forceFieldArea);

        pickUpItemHandler.pickUpItem(0);
        movePlayerHandler.move(Direction.RIGHT);
        useItemHandler.selectItemFromInventory(0);
        assertFalse(squareFF2.isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(6, 5)).isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(7, 5)).isObstructed());
        useItemHandler.useCurrentItem();
        assertTrue(squareFF1.isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(6, 5)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(7, 5)).isObstructed());

        assertTrue(squareFF2.isObstructed());
        endTurnHandler.endTurn();
        state.getCurrentTurn().attach(forceFieldArea);
        movePlayerHandler.move(Direction.DOWN);
        movePlayerHandler.move(Direction.DOWN);
        assertFalse(squareFF2.isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(6, 5)).isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(7, 5)).isObstructed());
    }

    @Test
    public void test_Force_Field_size_1() throws InventoryFullException, NotEnoughActionsException, NoItemSelectedException, SquareOccupiedException, InvalidMoveException, GameOverException {
        Square squareFF1 = grid.getSquareAtPosition(new Position(9, 0));
        Square squareFF2 = grid.getSquareAtPosition(new Position(8, 0));
        ForceFieldArea forceFieldArea = grid.getForceFieldArea();

        Item forceField1 = new ForceField(forceFieldArea);
        Item forceField2 = new ForceField(forceFieldArea);

        squareFF1.addItem(forceField1);
        squareFF2.addItem(forceField2);

        assertTrue(squareFF1.isObstructed());
        assertTrue(squareFF2.isObstructed());
    }

    @Test
    public void test_Force_Field_Direction_Left_Up_Pick_Up_ForceField() throws InventoryFullException, NotEnoughActionsException, NoItemSelectedException, SquareOccupiedException, InvalidMoveException, GameOverException, GridTooSmallException {
        dimension = new Dimension(10, 10);
        p1Pos = new Position(4, 5);
        p2Pos = new Position(7, 5);

        grid = GridFactory.gridWithoutWallsItemsPowerFailures(dimension, p1Pos, p2Pos);
        state = new GameState("p1", "p2", p1Pos, p2Pos, grid);
        movePlayerHandler = new MovePlayerHandler(state);
        endTurnHandler = new EndTurnHandler(state);
        pickUpItemHandler = new PickUpItemHandler(state);
        useItemHandler = new UseItemHandler(state);
        player1 = state.getCurrentPlayer();


        Square squareFF1 = grid.getSquareAtPosition(new Position(4, 5));
        Square squareFF2 = grid.getSquareAtPosition(new Position(7, 5));
        ForceFieldArea forceFieldArea = grid.getForceFieldArea();

        Item forceField1 = new ForceField(forceFieldArea);
        Item forceField2 = new ForceField(forceFieldArea);

        squareFF1.addItem(forceField1);
        squareFF2.addItem(forceField2);

        forceFieldArea.placeForceField(forceField1, squareFF1);
        forceFieldArea.placeForceField(forceField2, squareFF2);
        Turn currentTurn = state.getCurrentTurn();
        //currentTurn.attach(forceFieldArea);

        pickUpItemHandler.pickUpItem(0);
        movePlayerHandler.move(Direction.RIGHT);
        System.out.println(state.getCurrentPlayer().getCurrentSquare().getPosition());
        useItemHandler.selectItemFromInventory(0);
        assertFalse(grid.getSquareAtPosition(new Position(6, 5)).isObstructed());
        useItemHandler.useCurrentItem();
        assertTrue(squareFF1.isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(6, 5)).isObstructed());
        endTurnHandler.endTurn();
        pickUpItemHandler.pickUpItem(0);
        assertFalse(grid.getSquareAtPosition(new Position(6, 5)).isObstructed());
        //assertTrue(squareFF2.isObstructed());
    }


}
