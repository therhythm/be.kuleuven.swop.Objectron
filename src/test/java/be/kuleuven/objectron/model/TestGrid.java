package be.kuleuven.objectron.model;

import be.kuleuven.swop.objectron.GameState;
import be.kuleuven.swop.objectron.controller.GameController;
import be.kuleuven.swop.objectron.model.*;
import be.kuleuven.swop.objectron.model.exception.GameOverException;
import be.kuleuven.swop.objectron.model.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.model.exception.NotEnoughActionsException;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 11/03/13
 * Time: 20:10
 * To change this template use File | Settings | File Templates.
 */
public class TestGrid {

    private GameController controller;
    private Player player1;
    private Player player2;
    private Grid grid;


    @Before
    public void setUp(){
         grid = new Grid(10, 10);
        Square square1 = grid.getSquareAtPosition(9,0);

        player1 = new PlayerImpl("p1", square1);



        GameState stateMock = mock(GameState.class);
        when(stateMock.getCurrentPlayer()).thenReturn(player1);
        when(stateMock.getGrid()).thenReturn(grid);

        controller = new GameController(stateMock);
    }
    @Test
    public void test_valid_move_diagonal() throws InvalidMoveException, NotEnoughActionsException {
        Square square2 = grid.getSquareAtPosition(7,1);
        player2 = new PlayerImpl("p2", square2);

        Square prev = player1.getCurrentSquare();
        try {
        controller.move(Direction.UP);
        controller.move(Direction.UP);
        controller.move(Direction.UP_RIGHT);


            controller.endTurn();
        } catch (GameOverException e) {
            fail();
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        controller.move(Direction.UP_LEFT);


    }
    @Test(expected = InvalidMoveException.class)
    public void test_lightTrail() throws InvalidMoveException, NotEnoughActionsException {
        Square square2 = grid.getSquareAtPosition(9,3);
        player2 = new PlayerImpl("p2", square2);

        Square prev = player1.getCurrentSquare();

        controller.move(Direction.UP);
        controller.move(Direction.UP);
        controller.move(Direction.UP_LEFT);
        try {
            controller.endTurn();
        } catch (GameOverException e) {
            fail();
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        controller.move(Direction.DOWN_LEFT);


    }

    /**
     * Deze test gaat controleren of er wel degelijk items in de grid geplaatst worden.
     */
    @Test
    public void test_items_grid(){
        boolean hasItems=false;

    for(int i = 0;i<10;i++){
        for(int j = 0;j<10;j++){
            if(grid.getSquareAtPosition(i,j).getAvailableItems().size() !=0)
                hasItems=true;
        }
    }
        assertTrue(hasItems);
    }
}
