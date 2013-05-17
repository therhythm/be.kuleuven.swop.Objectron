package be.kuleuven.swop.objectron.domain.gamestate;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.*;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.gamestate.gamemode.CtfMode;
import be.kuleuven.swop.objectron.domain.gamestate.gamemode.RaceMode;
import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.grid.GridFactory;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.domain.util.Position;
import be.kuleuven.swop.objectron.handler.EndTurnHandler;
import be.kuleuven.swop.objectron.handler.MovePlayerHandler;
import be.kuleuven.swop.objectron.handler.PickUpItemHandler;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Created with IntelliJ IDEA.
 * User: Nik
 * Date: 4/12/13
 * Time: 1:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestGameState {

    private GameState state;
    private Grid grid;

    @Before
    public void setUp() throws GridTooSmallException {
        Position p1Pos = new Position(0, 0);
        Position p2Pos = new Position(1, 0);

        List<Position> positions = new ArrayList<Position>();
        positions.add(p1Pos);
        positions.add(p2Pos);

        List<String> playerNames = new ArrayList<>();
        playerNames.add("p1");
        playerNames.add("p2");

        grid = GridFactory.gridWithoutWallsItemsPowerFailures(new Dimension(10, 10), positions);
        state = new GameState(playerNames, positions, grid,new RaceMode());

    }

    @Test
    public void test_win() throws GameOverException, NotEnoughActionsException, InvalidMoveException, SquareOccupiedException {
        MovePlayerHandler handler = new MovePlayerHandler(state);
        TurnManager manager = state.getTurnManager();
        handler.move(Direction.DOWN);
        handler.move(Direction.DOWN);
        handler.move(Direction.DOWN);
        assertTrue(manager.getPlayers().size() == 2);
        manager.endTurn();
        assertTrue(manager.getPlayers().size() == 2);
        manager.endTurn();
        assertTrue(manager.getPlayers().size() == 1);
        handler.move(Direction.DOWN);
        manager.endTurn();
        Square sq = state.getGrid().getSquareAtPosition(new Position(0, 0));
        manager.getCurrentTurn().getCurrentPlayer().move(sq, manager);

        // assertTrue(state.checkWin());
    }


    @Test
    public void test_win_meerdere_players() throws GridTooSmallException, GameOverException, InvalidMoveException, NotEnoughActionsException, SquareOccupiedException {


        List<String> playerNames = new ArrayList<String>();
        playerNames.add("p1");
        playerNames.add("p2");
        playerNames.add("p3");
        playerNames.add("p4");

        List<Position> positions = new ArrayList<Position>();

        positions.add(new Position(0, 9));
        positions.add(new Position(9, 0));
        positions.add(new Position(9, 9));
        positions.add(new Position(0, 0));

        grid = GridFactory.gridWithoutWallsItemsPowerFailures(new Dimension(10, 10), positions);

        state = new GameState(playerNames, positions, grid, new RaceMode());
        EndTurnHandler endTurnHandler = new EndTurnHandler(state);
        MovePlayerHandler movePlayerHandler = new MovePlayerHandler(state);

        assertTrue(state.getTurnManager().getPlayers().size() == 4);
        endTurnHandler.endTurn();
        assertTrue(state.getTurnManager().getPlayers().size() == 3);
        movePlayerHandler.move(Direction.DOWN);
        endTurnHandler.endTurn();
        assertTrue(state.getTurnManager().getPlayers().size() == 3);
        endTurnHandler.endTurn();

        assertTrue(state.getTurnManager().getPlayers().size() == 2);
    }

    @Test
    public void test_win_ctf_mode() throws GridTooSmallException, GameOverException, NotEnoughActionsException, InvalidMoveException, InventoryFullException, SquareOccupiedException {
        List<String> playerNames = new ArrayList<String>();
        playerNames.add("p1");
        playerNames.add("p2");
        playerNames.add("p3");
        playerNames.add("p4");

        List<Position> positions = new ArrayList<Position>();

        positions.add(new Position(1, 2));
        positions.add(new Position(2, 1));
        positions.add(new Position(2, 2));
        positions.add(new Position(1, 1));

        grid = GridFactory.gridWithoutWallsItemsPowerFailures(new Dimension(10, 10), positions);
        state = new GameState(playerNames, positions, grid, new CtfMode());

        //controleren of vlaggen op juiste square staan
        assertTrue(grid.getSquareAtPosition(new Position(1,2)).getAvailableItems().size()==1);
        assertTrue(grid.getSquareAtPosition(new Position(2,1)).getAvailableItems().size()==1);
        assertTrue(grid.getSquareAtPosition(new Position(1,1)).getAvailableItems().size()==1);
        assertTrue(grid.getSquareAtPosition(new Position(2,2)).getAvailableItems().size()==1);

        MovePlayerHandler movePlayerHandler = new MovePlayerHandler(state);
        PickUpItemHandler pickUpItemHandler = new PickUpItemHandler(state);
        EndTurnHandler endTurnHandler = new EndTurnHandler(state);
        TurnManager turnManager = state.getTurnManager();
        //player 1 gaat winnen
        //andere 3 players verplaatsen zodat ze niet meer op hun startpositie staan
        turnManager.getCurrentTurn().setMoved();
        turnManager.endTurn();

        //PLAYER 2
        Player playerTemp = turnManager.getCurrentTurn().getCurrentPlayer();
        movePlayerHandler.move(Direction.UP);
        for (int i = 0; i < 2; i++) {
            System.out.println("1--------------");
            System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
            movePlayerHandler.move(Direction.RIGHT);
            System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
            System.out.println("1--------------");
        }
        turnManager.getCurrentTurn().setMoved();
        endTurnHandler.endTurn();
        while (!turnManager.getCurrentTurn().getCurrentPlayer().equals(playerTemp)) {
            turnManager.getCurrentTurn().setMoved();
            endTurnHandler.endTurn();
        }
        for (int i = 0; i < 3; i++) {
            System.out.println("2--------------");
            System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
            movePlayerHandler.move(Direction.RIGHT);
            System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
            System.out.println("2--------------");
        }


        endTurnHandler.endTurn();

        //PLAYER 3
        playerTemp = turnManager.getCurrentTurn().getCurrentPlayer();
        for (int i = 0; i < 2; i++) {
            System.out.println("3--------------");
            System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
            movePlayerHandler.move(Direction.DOWN);
            System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
            System.out.println("3--------------");
        }
        turnManager.getCurrentTurn().setMoved();
        endTurnHandler.endTurn();
        while (!turnManager.getCurrentTurn().getCurrentPlayer().equals(playerTemp)) {
            turnManager.getCurrentTurn().setMoved();
            endTurnHandler.endTurn();
        }
        for (int i = 0; i < 3; i++) {
            System.out.println("3--------------");
            System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
            movePlayerHandler.move(Direction.DOWN);
            System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
            System.out.println("3--------------");
        }


        endTurnHandler.endTurn();


        //PLAYER 4
        playerTemp = turnManager.getCurrentTurn().getCurrentPlayer();
        System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
        movePlayerHandler.move(Direction.LEFT);
        System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
        for (int i = 0; i < 2; i++) {
            movePlayerHandler.move(Direction.DOWN);
        }

        endTurnHandler.endTurn();
        while (!turnManager.getCurrentTurn().getCurrentPlayer().equals(playerTemp)) {
            turnManager.getCurrentTurn().setMoved();
            endTurnHandler.endTurn();
        }
        for (int i = 0; i < 2; i++) {
            System.out.println("4--------------");
            System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
            movePlayerHandler.move(Direction.DOWN);
            System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
            System.out.println("4--------------");
        }
        endTurnHandler.endTurn();
        while (!turnManager.getCurrentTurn().getCurrentPlayer().equals(playerTemp)) {
            turnManager.getCurrentTurn().setMoved();
            endTurnHandler.endTurn();
        }
        for (int i = 0; i < 2; i++) {
            System.out.println("4--------------");
            System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
            movePlayerHandler.move(Direction.DOWN);
            System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
            System.out.println("4--------------");
        }
        endTurnHandler.endTurn();
        assertFalse(grid.getSquareAtPosition(new Position(1, 1)).isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(2, 1)).isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(2, 2)).isObstructed());

        //Verzamelen van de vlaggen
        //eerste vlag
        System.out.println(0);
        System.out.println("collecting flags");
        System.out.println("*--------------");
        System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
        movePlayerHandler.move(Direction.RIGHT);
        System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
        System.out.println("*--------------");
        pickUpItemHandler.pickUpItem(0);
        movePlayerHandler.move(Direction.RIGHT);
        endTurnWithoutGameOver(endTurnHandler, turnManager);
        System.out.println("*--------------");
        System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
        movePlayerHandler.move(Direction.DOWN);
        System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
        System.out.println("*--------------");

        System.out.println("*--------------");
        System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
        movePlayerHandler.move(Direction.LEFT);
        System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
        System.out.println("*--------------");
        movePlayerHandler.move(Direction.LEFT);

        endTurnWithoutGameOver(endTurnHandler, turnManager);

        //controleren of vlaggen op juiste square staan
        assertTrue(grid.getSquareAtPosition(new Position(1,2)).getAvailableItems().size()==1);
        assertTrue(grid.getSquareAtPosition(new Position(2,1)).getAvailableItems().size()==1);
        assertTrue(grid.getSquareAtPosition(new Position(1,1)).getAvailableItems().size()==1);
        assertFalse(grid.getSquareAtPosition(new Position(2, 2)).getAvailableItems().size() == 1);
         assertTrue(turnManager.getCurrentTurn().getCurrentPlayer().getInventoryItems().size()==1);

        System.out.println("*--------------");
        System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
        movePlayerHandler.move(Direction.UP);
        System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
        System.out.println("*--------------");

        //controleren of vlaggen op juiste square staan
        assertTrue(grid.getSquareAtPosition(new Position(1,2)).getAvailableItems().size()==1);
        assertTrue(grid.getSquareAtPosition(new Position(2,1)).getAvailableItems().size()==1);
        assertTrue(grid.getSquareAtPosition(new Position(1,1)).getAvailableItems().size()==1);
        assertTrue(grid.getSquareAtPosition(new Position(2, 2)).getAvailableItems().size() == 1);
        assertTrue(turnManager.getCurrentTurn().getCurrentPlayer().getInventoryItems().size()==0);

        //collecting 2de vlag
        System.out.println("2de vlag");
        System.out.println("*--------------");
        System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
        movePlayerHandler.move(Direction.UP);
        System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
        System.out.println("*--------------");

        pickUpItemHandler.pickUpItem(0);

        //controleren of vlaggen op juiste square staan
        assertTrue(grid.getSquareAtPosition(new Position(1,2)).getAvailableItems().size()==1);
        assertTrue(grid.getSquareAtPosition(new Position(2,1)).getAvailableItems().size()==1);
        assertFalse(grid.getSquareAtPosition(new Position(1, 1)).getAvailableItems().size() == 1);
        assertTrue(grid.getSquareAtPosition(new Position(2, 2)).getAvailableItems().size() == 1);
        assertTrue(turnManager.getCurrentTurn().getCurrentPlayer().getInventoryItems().size() == 1);

        endTurnWithoutGameOver(endTurnHandler, turnManager);

        for(int i = 0;i<2;i++){
            System.out.println("*--------------");
            System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
            movePlayerHandler.move(Direction.RIGHT);
            System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
            System.out.println("*--------------");
        }
        System.out.println("*--------------");
        System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
        movePlayerHandler.move(Direction.DOWN);
        System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
        System.out.println("*--------------");

        endTurnWithoutGameOver(endTurnHandler, turnManager);

        System.out.println("*--------------");
        System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
        movePlayerHandler.move(Direction.DOWN);
        System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
        System.out.println("*--------------");

        for(int i = 0;i<2;i++){
            System.out.println("*--------------");
            System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
            movePlayerHandler.move(Direction.LEFT);
            System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
            System.out.println("*--------------");
        }

        endTurnWithoutGameOver(endTurnHandler, turnManager);

        System.out.println("*--------------");
        System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
        movePlayerHandler.move(Direction.UP);
        System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
        System.out.println("*--------------");

        //controleren of vlaggen op juiste square staan
        assertTrue(grid.getSquareAtPosition(new Position(1,2)).getAvailableItems().size()==1);
        assertTrue(grid.getSquareAtPosition(new Position(2,1)).getAvailableItems().size()==1);
        assertTrue(grid.getSquareAtPosition(new Position(1,1)).getAvailableItems().size()==1);
        assertTrue(grid.getSquareAtPosition(new Position(2,2)).getAvailableItems().size()==1);
        assertTrue(turnManager.getCurrentTurn().getCurrentPlayer().getInventoryItems().size()==0);

          //3de vlag
        System.out.println("3de vlag");
        System.out.println("*--------------");
        System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
        movePlayerHandler.move(Direction.UP_RIGHT);
        System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
        System.out.println("*--------------");

        pickUpItemHandler.pickUpItem(0);

        endTurnWithoutGameOver(endTurnHandler, turnManager);

        System.out.println("*--------------");
        System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
        movePlayerHandler.move(Direction.RIGHT);
        System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
        System.out.println("*--------------");

        for(int i = 0;i<2;i++){
            System.out.println("*--------------");
            System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
            movePlayerHandler.move(Direction.DOWN);
            System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
            System.out.println("*--------------");
        }

        endTurnWithoutGameOver(endTurnHandler, turnManager);


        for(int i = 0;i<2;i++){
            System.out.println("*--------------");
            System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
            movePlayerHandler.move(Direction.LEFT);
            System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
            System.out.println("*--------------");
        }

        //win
        boolean win = false;
        try{
        System.out.println("*--------------");
        System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
        movePlayerHandler.move(Direction.UP);
        System.out.println(turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().getPosition());
        System.out.println("*--------------");
        }catch(GameOverException exc){
            win = true;
        }
        assertTrue(win);


    }

    private void endTurnWithoutGameOver(EndTurnHandler endTurnHandler, TurnManager turnManager) throws GameOverException {
        Player playerTemp;
        playerTemp = turnManager.getCurrentTurn().getCurrentPlayer();
        endTurnHandler.endTurn();
        while (!turnManager.getCurrentTurn().getCurrentPlayer().equals(playerTemp)) {
            turnManager.getCurrentTurn().setMoved();
            endTurnHandler.endTurn();
        }
    }


}
