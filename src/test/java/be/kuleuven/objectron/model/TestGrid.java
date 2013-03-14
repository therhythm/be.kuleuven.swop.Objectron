package be.kuleuven.objectron.model;

import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.handler.EndTurnHandler;
import be.kuleuven.swop.objectron.handler.MovePlayerHandler;
import be.kuleuven.swop.objectron.domain.*;
import be.kuleuven.swop.objectron.domain.exception.GameOverException;
import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 11/03/13
 * Time: 20:10
 * To change this template use File | Settings | File Templates.
 */
public class TestGrid {

    private EndTurnHandler endTurnHandler;
    private MovePlayerHandler movePlayerHandler;
    private Player player1;
    private Player player2;
    private Grid grid;
    private GameState state;

    @Before

    public void setUp()throws GridTooSmallException{
         //square 1
        //vert pos = 1
        //hor pos = 8

        //square 2
        //vert pos = 3
        //hor pos = 8
        state = new GameStateStub("p1", "p2",10, 10,1,8,3,8);


        grid = state.getGrid();
        movePlayerHandler = new MovePlayerHandler(state);
        endTurnHandler = new EndTurnHandler(state);

    }
    @Test (expected = InvalidMoveException.class)
    public void test_invalid_move_diagonal() throws InvalidMoveException, NotEnoughActionsException, GameOverException {
        player1 = state.getCurrentPlayer();

        try {
            //System.out.println("player1");
            //System.out.println(player1.getCurrentSquare());
            movePlayerHandler.move(Direction.RIGHT);
            //System.out.println(player1.getCurrentSquare());
            movePlayerHandler.move(Direction.UP_RIGHT);
            //System.out.println(player1.getCurrentSquare());


            endTurnHandler.endTurn();
        } catch (GameOverException e) {
            fail();
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        //System.out.println("player2");
        player2 = state.getCurrentPlayer();

        //System.out.println(player2.getCurrentSquare());
        movePlayerHandler.move(Direction.UP_LEFT);
        //System.out.println(player2.getCurrentSquare());

    }

    @Test
    public void test_valid_move_diagonal() throws InvalidMoveException, NotEnoughActionsException, GameOverException {

        player1 = state.getCurrentPlayer();

        try {
            //System.out.println("player1");
            //System.out.println(player1.getCurrentSquare());
            movePlayerHandler.move(Direction.RIGHT);
            //System.out.println(player1.getCurrentSquare());
            movePlayerHandler.move(Direction.UP_RIGHT);
            //System.out.println(player1.getCurrentSquare());


            endTurnHandler.endTurn();
        } catch (GameOverException e) {
            fail();
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        //System.out.println("player2");
        player2 = state.getCurrentPlayer();

        //System.out.println(player2.getCurrentSquare());
        movePlayerHandler.move(Direction.DOWN_LEFT);
        //System.out.println(player2.getCurrentSquare());
        movePlayerHandler.move(Direction.LEFT);
        //System.out.println(player2.getCurrentSquare());
        movePlayerHandler.move(Direction.UP_LEFT);
        //System.out.println(player2.getCurrentSquare());
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

    @Test(expected = IllegalArgumentException.class)
    public void test_getInvalidSquare(){
        grid.getSquareAtPosition(11,11);
    }

    @Test (expected = InvalidMoveException.class)
    public void test_invalid_move_neighbor() throws InvalidMoveException, NotEnoughActionsException, GameOverException {
        player1 = state.getCurrentPlayer();


            movePlayerHandler.move(Direction.RIGHT);
            movePlayerHandler.move(Direction.RIGHT);



    }

    public class GameStateStub implements GameState{
        private Grid gameGrid;
        private Player currentPlayer;
        private List<Player> players = new ArrayList<Player>();

        public GameStateStub(String player1Name, String player2Name, int horizontalTiles, int verticalTiles,int square1HorizontalPosition,int square1VerticalPosition,int square2HorizontalPosition,int square2VerticalPosition) throws GridTooSmallException{
            gameGrid = new Grid(horizontalTiles, verticalTiles);
            Player p1 = new Player(player1Name, gameGrid.getSquareAtPosition(square1VerticalPosition, square1HorizontalPosition));
            Player p2 = new Player(player2Name, gameGrid.getSquareAtPosition(square2VerticalPosition, square2HorizontalPosition));
            gameGrid.buildGrid(p1.getCurrentSquare(), p2.getCurrentSquare());
            currentPlayer = p1;
            players.add(p1);
            players.add(p2);

        }

        public Player getPlayer1(){
            return this.players.get(0);
        }

        public Player getPlayer2(){
            return this.players.get(1);
        }



        public Player getCurrentPlayer() {
            return currentPlayer;
        }

        public Grid getGrid() {
            return gameGrid;
        }

        public void nextPlayer() {
            int index = players.indexOf(currentPlayer);
            index = (index + 1) % players.size();
            currentPlayer = players.get(index);
        }


    }

}
