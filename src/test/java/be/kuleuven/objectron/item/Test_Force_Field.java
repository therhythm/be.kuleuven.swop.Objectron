package be.kuleuven.objectron.item;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.*;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.grid.GridFactory;
import be.kuleuven.swop.objectron.domain.item.ChargedIdentityDiscBehavior;
import be.kuleuven.swop.objectron.domain.item.IdentityDisc;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.item.forceField.ForceField;
import be.kuleuven.swop.objectron.domain.item.forceField.ForceFieldArea;
import be.kuleuven.swop.objectron.domain.item.forceField.ForceFieldObserver;
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

     @Test(expected = InvalidMoveException.class)
    public void test_Force_Field() throws InventoryFullException, NotEnoughActionsException, NoItemSelectedException, SquareOccupiedException, InvalidMoveException, GameOverException {
        Square squareFF1 = grid.getSquareAtPosition(new Position(0, 9));
        Square squareFF2 = grid.getSquareAtPosition(new Position(2, 9));
        ForceFieldArea forceFieldArea = new ForceFieldArea();

        Item forceField1 = new ForceField(forceFieldArea);
        Item forceField2 = new ForceField(forceFieldArea);

        squareFF1.addItem(forceField1);
        squareFF2.addItem(forceField2);

        forceFieldArea.addForceField(squareFF1,forceField1);
        forceFieldArea.addForceField(squareFF2,forceField2);
         Turn currentTurn = state.getCurrentTurn();
          currentTurn.attach(forceFieldArea);

        pickUpItemHandler.pickUpItem(0);
          useItemHandler.selectItemFromInventory(0);
          useItemHandler.useCurrentItem();
         movePlayerHandler.move(Direction.RIGHT);
        System.out.println(forceFieldArea.sizeListForceFieldPairs() );


    }
}
