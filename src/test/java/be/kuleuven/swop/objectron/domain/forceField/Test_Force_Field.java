package be.kuleuven.swop.objectron.domain.forceField;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.exception.*;
import be.kuleuven.swop.objectron.domain.gamestate.Game;
import be.kuleuven.swop.objectron.domain.gamestate.RaceGame;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.grid.GeneratedGridBuilder;
import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.grid.GridBuilder;
import be.kuleuven.swop.objectron.domain.grid.GridObjectMother;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.item.forceField.ForcefieldGenerator;
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

import java.util.ArrayList;
import java.util.List;

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
    private Game state;
    private Grid grid;

    private Dimension dimension;
    private Position p1Pos;
    private Position p2Pos;

    @Before
    public void setUp() throws GridTooSmallException {
        dimension = new Dimension(10, 10);

        p1Pos = new Position(4, 5);
        p2Pos = new Position(7, 6);

        List<Position> positions = new ArrayList<>();
        positions.add(p1Pos);
        positions.add(p2Pos);

        GridBuilder gridBuilder = new GeneratedGridBuilder(dimension, 2);
        gridBuilder.setStartingPositions(positions);

        List<String> playerNames = new ArrayList<>();
        playerNames.add("p1");
        playerNames.add("p2");

        grid = GridObjectMother.gridWithoutWallsItemsPowerFailures(gridBuilder);
        state = new RaceGame(playerNames, grid);

        movePlayerHandler = new MovePlayerHandler(state);
        endTurnHandler = new EndTurnHandler(state);
        pickUpItemHandler = new PickUpItemHandler(state);
        useItemHandler = new UseItemHandler(state);
    }

    @Test(expected = InvalidMoveException.class)
    public void test_Force_Field_move_in_force_field_Direction_Left() throws InventoryFullException, NotEnoughActionsException, NoItemSelectedException, SquareOccupiedException, InvalidMoveException, GameOverException {
        Square squareFF1 = grid.getSquareAtPosition(new Position(4, 5));
        Square squareFF2 = grid.getSquareAtPosition(new Position(8, 5));
        ForceFieldArea forceFieldArea = grid.getForceFieldArea();

        Item forcefieldGenerator1 = new ForcefieldGenerator(forceFieldArea);
        Item forcefieldGenerator2 = new ForcefieldGenerator(forceFieldArea);

        squareFF1.addItem(forcefieldGenerator1);
        squareFF2.addItem(forcefieldGenerator2);

        forceFieldArea.placeForceField(forcefieldGenerator1, squareFF1);
        forceFieldArea.placeForceField(forcefieldGenerator2, squareFF2);

        pickUpItemHandler.pickUpItem(0);
        movePlayerHandler.move(Direction.RIGHT);
        useItemHandler.selectItemFromInventory(0);
        assertFalse(squareFF2.isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(6, 5)).isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(7, 5)).isObstructed());
        useItemHandler.useCurrentItem();

        assertFalse(squareFF2.isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(6, 5)).isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(7, 5)).isObstructed());
        endTurnHandler.endTurn();
        movePlayerHandler.move(Direction.LEFT);
        assertFalse(squareFF2.isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(6, 5)).isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(7, 5)).isObstructed());

        movePlayerHandler.move(Direction.LEFT);
        assertTrue(squareFF1.isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(6, 5)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(7, 5)).isObstructed());

        assertTrue(squareFF2.isObstructed());
        movePlayerHandler.move(Direction.UP);
        System.out.println(state.getTurnManager().getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
    }

    @Test
    public void test_Force_Field_Direction_Left_Up() throws InventoryFullException, NotEnoughActionsException, NoItemSelectedException, SquareOccupiedException, InvalidMoveException, GameOverException {
        Square squareFF1 = grid.getSquareAtPosition(new Position(4, 5));
        Square squareFF2 = grid.getSquareAtPosition(new Position(8, 2));
        ForceFieldArea forceFieldArea = grid.getForceFieldArea();

        Item forcefieldGenerator1 = new ForcefieldGenerator(forceFieldArea);
        Item forcefieldGenerator2 = new ForcefieldGenerator(forceFieldArea);

        squareFF1.addItem(forcefieldGenerator1);
        squareFF2.addItem(forcefieldGenerator2);


        forceFieldArea.placeForceField(forcefieldGenerator1, squareFF1);
        forceFieldArea.placeForceField(forcefieldGenerator2, squareFF2);

        pickUpItemHandler.pickUpItem(0);
        movePlayerHandler.move(Direction.RIGHT);
        useItemHandler.selectItemFromInventory(0);
        assertFalse(squareFF2.isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(6, 4)).isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(7, 3)).isObstructed());
        useItemHandler.useCurrentItem();
        assertFalse(squareFF2.isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(6, 4)).isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(7, 3)).isObstructed());
        endTurnHandler.endTurn();
        movePlayerHandler.move(Direction.LEFT);
        assertFalse(squareFF2.isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(6, 4)).isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(7, 3)).isObstructed());

        movePlayerHandler.move(Direction.LEFT);

        assertTrue(squareFF1.isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(6, 4)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(7, 3)).isObstructed());

        assertTrue(squareFF2.isObstructed());
    }

    @Test
    public void test_Force_Field_Direction_Left_Up_no_field() throws InventoryFullException, NotEnoughActionsException, NoItemSelectedException, SquareOccupiedException, InvalidMoveException, GameOverException {
        Square squareFF1 = grid.getSquareAtPosition(new Position(4, 5));
        Square squareFF2 = grid.getSquareAtPosition(new Position(8, 1));
        ForceFieldArea forceFieldArea = grid.getForceFieldArea();

        Item forcefieldGenerator1 = new ForcefieldGenerator(forceFieldArea);
        Item forcefieldGenerator2 = new ForcefieldGenerator(forceFieldArea);

        squareFF1.addItem(forcefieldGenerator1);
        squareFF2.addItem(forcefieldGenerator2);

        forceFieldArea.placeForceField(forcefieldGenerator1, squareFF1);
        forceFieldArea.placeForceField(forcefieldGenerator2, squareFF2);

        pickUpItemHandler.pickUpItem(0);
        movePlayerHandler.move(Direction.RIGHT);
        useItemHandler.selectItemFromInventory(0);
        assertFalse(squareFF2.isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(6, 4)).isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(7, 3)).isObstructed());
        useItemHandler.useCurrentItem();
        assertFalse(squareFF2.isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(6, 4)).isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(7, 3)).isObstructed());
        endTurnHandler.endTurn();
        movePlayerHandler.move(Direction.LEFT);
        assertFalse(squareFF2.isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(6, 4)).isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(7, 3)).isObstructed());

        movePlayerHandler.move(Direction.LEFT);

        assertFalse(squareFF2.isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(6, 4)).isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(7, 3)).isObstructed());
    }

    @Test
    public void test_Force_Field_Direction_Up() throws InventoryFullException, NotEnoughActionsException, NoItemSelectedException, SquareOccupiedException, InvalidMoveException, GameOverException {
        Square squareFF1 = grid.getSquareAtPosition(new Position(4, 5));
        Square squareFF2 = grid.getSquareAtPosition(new Position(5, 2));
        ForceFieldArea forceFieldArea = grid.getForceFieldArea();

        Item forceFieldGenerator1 = new ForcefieldGenerator(forceFieldArea);
        Item forceFieldGenerator2 = new ForcefieldGenerator(forceFieldArea);

        squareFF1.addItem(forceFieldGenerator1);
        squareFF2.addItem(forceFieldGenerator2);

        forceFieldArea.placeForceField(forceFieldGenerator1, squareFF1);
        forceFieldArea.placeForceField(forceFieldGenerator2, squareFF2);
        Turn currentTurn = state.getTurnManager().getCurrentTurn();
        //currentTurn.attach(forceFieldArea);

        pickUpItemHandler.pickUpItem(0);
        movePlayerHandler.move(Direction.RIGHT);
        assertTrue(currentTurn.getCurrentPlayer().getCurrentSquare().equals(grid.getSquareAtPosition(new Position(5, 5))));
        useItemHandler.selectItemFromInventory(0);
        assertFalse(squareFF2.isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(5, 4)).isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(5, 3)).isObstructed());
        useItemHandler.useCurrentItem();
        assertFalse(squareFF2.isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(5, 4)).isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(5, 3)).isObstructed());
        endTurnHandler.endTurn();
        movePlayerHandler.move(Direction.UP);
        assertFalse(squareFF2.isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(5, 4)).isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(5, 3)).isObstructed());
        movePlayerHandler.move(Direction.UP);
        assertTrue(squareFF1.isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(5, 4)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(5, 3)).isObstructed());

        assertTrue(squareFF2.isObstructed());
    }

    @Test
    public void test_Force_Field_Direction_Up_no_field() throws InventoryFullException, NotEnoughActionsException, NoItemSelectedException, SquareOccupiedException, InvalidMoveException, GameOverException {
        Square squareFF1 = grid.getSquareAtPosition(new Position(4, 5));
        Square squareFF2 = grid.getSquareAtPosition(new Position(4, 3));
        ForceFieldArea forceFieldArea = grid.getForceFieldArea();

        Item forceFieldGenerator1 = new ForcefieldGenerator(forceFieldArea);
        Item forceFieldGenerator2 = new ForcefieldGenerator(forceFieldArea);

        squareFF1.addItem(forceFieldGenerator1);
        squareFF2.addItem(forceFieldGenerator2);

        forceFieldArea.placeForceField(forceFieldGenerator1, squareFF1);
        forceFieldArea.placeForceField(forceFieldGenerator2, squareFF2);
        Turn currentTurn = state.getTurnManager().getCurrentTurn();
        //currentTurn.attach(forceFieldArea);

        pickUpItemHandler.pickUpItem(0);
        movePlayerHandler.move(Direction.RIGHT);
        assertTrue(currentTurn.getCurrentPlayer().getCurrentSquare().equals(grid.getSquareAtPosition(new Position(5, 5))));
        useItemHandler.selectItemFromInventory(0);
        assertFalse(squareFF2.isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(5, 4)).isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(5, 3)).isObstructed());
        useItemHandler.useCurrentItem();
        assertFalse(squareFF2.isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(5, 4)).isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(5, 3)).isObstructed());
        endTurnHandler.endTurn();
        movePlayerHandler.move(Direction.UP);
        assertFalse(squareFF2.isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(5, 4)).isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(5, 3)).isObstructed());
        movePlayerHandler.move(Direction.UP);
        assertFalse(squareFF2.isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(5, 4)).isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(5, 3)).isObstructed());
    }

    @Test
    public void test_Force_Field_Direction_Up_Right() throws InventoryFullException, NotEnoughActionsException, NoItemSelectedException, SquareOccupiedException, InvalidMoveException, GameOverException {
        Square squareFF1 = grid.getSquareAtPosition(new Position(4, 5));
        Square squareFF2 = grid.getSquareAtPosition(new Position(2, 2));
        ForceFieldArea forceFieldArea = grid.getForceFieldArea();

        Item forceFieldGenerator1 = new ForcefieldGenerator(forceFieldArea);
        Item forceFieldGenerator2 = new ForcefieldGenerator(forceFieldArea);

        squareFF1.addItem(forceFieldGenerator1);
        squareFF2.addItem(forceFieldGenerator2);

        forceFieldArea.placeForceField(forceFieldGenerator1, squareFF1);
        forceFieldArea.placeForceField(forceFieldGenerator2, squareFF2);

        pickUpItemHandler.pickUpItem(0);
        movePlayerHandler.move(Direction.RIGHT);
        useItemHandler.selectItemFromInventory(0);
        assertFalse(squareFF2.isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(4, 4)).isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(3, 3)).isObstructed());

        useItemHandler.useCurrentItem();
        assertFalse(squareFF2.isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(4, 4)).isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(3, 3)).isObstructed());
        endTurnHandler.endTurn();
        movePlayerHandler.move(Direction.UP);
        assertFalse(squareFF2.isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(4, 4)).isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(3, 3)).isObstructed());
        movePlayerHandler.move(Direction.UP);
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

        Item forceFieldGenerator1 = new ForcefieldGenerator(forceFieldArea);
        Item forceFieldGenerator2 = new ForcefieldGenerator(forceFieldArea);

        squareFF1.addItem(forceFieldGenerator1);
        squareFF2.addItem(forceFieldGenerator2);

        forceFieldArea.placeForceField(forceFieldGenerator1, squareFF1);
        forceFieldArea.placeForceField(forceFieldGenerator2, squareFF2);

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

        Item forceFieldGenerator1 = new ForcefieldGenerator(forceFieldArea);
        Item forceFieldGenerator2 = new ForcefieldGenerator(forceFieldArea);

        squareFF1.addItem(forceFieldGenerator1);
        squareFF2.addItem(forceFieldGenerator2);

        forceFieldArea.placeForceField(forceFieldGenerator1, squareFF1);
        forceFieldArea.placeForceField(forceFieldGenerator2, squareFF2);
        // Turn currentTurn = game.getCurrentTurn();
        //currentTurn.attach(forceFieldArea);

        pickUpItemHandler.pickUpItem(0);
        movePlayerHandler.move(Direction.RIGHT);
        useItemHandler.selectItemFromInventory(0);
        assertFalse(squareFF2.isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(6, 5)).isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(7, 5)).isObstructed());
        useItemHandler.useCurrentItem();
        assertFalse(squareFF2.isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(6, 5)).isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(7, 5)).isObstructed());
        endTurnHandler.endTurn();
        movePlayerHandler.move(Direction.RIGHT);
        assertFalse(squareFF2.isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(6, 5)).isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(7, 5)).isObstructed());
        movePlayerHandler.move(Direction.RIGHT);

        assertTrue(squareFF1.isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(6, 5)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(7, 5)).isObstructed());

        assertTrue(squareFF2.isObstructed());
        endTurnHandler.endTurn();
        movePlayerHandler.move(Direction.DOWN);
        movePlayerHandler.move(Direction.DOWN);
        assertFalse(squareFF2.isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(6, 5)).isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(7, 5)).isObstructed());
        endTurnHandler.endTurn();

        movePlayerHandler.move(Direction.UP);
        movePlayerHandler.move(Direction.UP);
        //  assertTrue(squareFF1.isObstructed());
        assertTrue(squareFF2.isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(5, 5)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(6, 5)).isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(7, 5)).isObstructed());

    }

    @Test
    public void test_Force_Field_size_1() throws InventoryFullException, NotEnoughActionsException, NoItemSelectedException, SquareOccupiedException, InvalidMoveException, GameOverException {
        Square squareFF1 = grid.getSquareAtPosition(new Position(9, 0));
        Square squareFF2 = grid.getSquareAtPosition(new Position(7, 0));
        ForceFieldArea forceFieldArea = grid.getForceFieldArea();

        Item forceFieldGenerator1 = new ForcefieldGenerator(forceFieldArea);
        Item forceFieldGenerator2 = new ForcefieldGenerator(forceFieldArea);

        squareFF1.addItem(forceFieldGenerator1);
        squareFF2.addItem(forceFieldGenerator2);
        forceFieldArea.placeForceField(forceFieldGenerator1, squareFF1);
        forceFieldArea.placeForceField(forceFieldGenerator2, squareFF2);


        assertFalse(squareFF1.isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(8, 0)).isObstructed());
        assertFalse(squareFF2.isObstructed());
        movePlayerHandler.move(Direction.RIGHT);
        assertFalse(squareFF1.isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(8, 0)).isObstructed());
        assertFalse(squareFF2.isObstructed());
        movePlayerHandler.move(Direction.RIGHT);
        assertTrue(squareFF1.isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(8, 0)).isObstructed());
        assertTrue(squareFF2.isObstructed());

    }

    @Test
    public void test_Force_Field_Direction_Left_Up_Pick_Up_ForceField() throws InventoryFullException, NotEnoughActionsException, NoItemSelectedException, SquareOccupiedException, InvalidMoveException, GameOverException, GridTooSmallException {
        dimension = new Dimension(10, 10);
        p1Pos = new Position(4, 5);
        p2Pos = new Position(6, 6);

        List<Position> positions = new ArrayList<>();
        positions.add(p1Pos);
        positions.add(p2Pos);

        List<String> playerNames = new ArrayList<>();
        playerNames.add("p1");
        playerNames.add("p2");

        GridBuilder builder = new GeneratedGridBuilder(dimension, 2);
        builder.setStartingPositions(positions);
        grid = GridObjectMother.gridWithoutWallsItemsPowerFailures(builder);
        state = new RaceGame(playerNames, grid);

        movePlayerHandler = new MovePlayerHandler(state);
        endTurnHandler = new EndTurnHandler(state);
        pickUpItemHandler = new PickUpItemHandler(state);
        useItemHandler = new UseItemHandler(state);


        Square squareFF1 = grid.getSquareAtPosition(new Position(4, 5));
        Square squareFF2 = grid.getSquareAtPosition(new Position(7, 5));
        ForceFieldArea forceFieldArea = grid.getForceFieldArea();

        Item forceFieldGenerator1 = new ForcefieldGenerator(forceFieldArea);
        Item forceFieldGenerator2 = new ForcefieldGenerator(forceFieldArea);

        squareFF1.addItem(forceFieldGenerator1);
        squareFF2.addItem(forceFieldGenerator2);


        forceFieldArea.placeForceField(forceFieldGenerator1, squareFF1);
        forceFieldArea.placeForceField(forceFieldGenerator2, squareFF2);

        pickUpItemHandler.pickUpItem(0);
        movePlayerHandler.move(Direction.RIGHT);

        useItemHandler.selectItemFromInventory(0);
        assertFalse(grid.getSquareAtPosition(new Position(6, 5)).isObstructed());
        useItemHandler.useCurrentItem();
        endTurnHandler.endTurn();
        movePlayerHandler.move(Direction.DOWN);
        assertFalse(grid.getSquareAtPosition(new Position(6, 5)).isObstructed());
        movePlayerHandler.move(Direction.LEFT);
        assertTrue(squareFF1.isObstructed());
        assertTrue(grid.getSquareAtPosition(new Position(6, 5)).isObstructed());
        endTurnHandler.endTurn();
        pickUpItemHandler.pickUpItem(0);
        assertFalse(grid.getSquareAtPosition(new Position(6, 5)).isObstructed());
        //assertTrue(squareFF2.isObstructed());
    }

   @Test(expected = SquareOccupiedException.class)
    public void test_max_one_force_field_per_square() throws SquareOccupiedException {
       Square square = grid.getSquareAtPosition(new Position(0,0));
       ForceFieldArea forceFieldArea =  grid.getForceFieldArea();

       forceFieldArea.placeForceField(new ForcefieldGenerator(forceFieldArea),square);
       forceFieldArea.placeForceField(new ForcefieldGenerator(forceFieldArea),square);

   }

}
