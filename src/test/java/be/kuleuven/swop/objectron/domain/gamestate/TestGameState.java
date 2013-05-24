package be.kuleuven.swop.objectron.domain.gamestate;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.*;
import be.kuleuven.swop.objectron.domain.grid.*;
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Nik
 * Date: 4/12/13
 * Time: 1:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestGameState {
    private Game state;

    @Before
    public void setUp() throws GridTooSmallException, NumberOfPlayersException {
        Position p1Pos = new Position(0, 0);
        Position p2Pos = new Position(1, 0);
        List<Position> positions = new ArrayList<>();
        positions.add(p1Pos);
        positions.add(p2Pos);

        List<String> playerNames = new ArrayList<>();
        playerNames.add("p1");
        playerNames.add("p2");

        Dimension dimension = new Dimension(10, 10);
        state = GameObjectMother.raceGameWithoutWallsItemsPowerFailures(dimension, playerNames, positions);
    }

    @Test
    public void test_win() throws GameOverException, NotEnoughActionsException, InvalidMoveException,
            SquareOccupiedException {
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
    }

    @Test
    public void test_win_ctf_mode() throws GridTooSmallException, GameOverException, NotEnoughActionsException,
            InvalidMoveException, InventoryFullException, SquareOccupiedException, NumberOfPlayersException {
        List<String> playerNames = new ArrayList<>();
        playerNames.add("p1");
        playerNames.add("p2");
        playerNames.add("p3");
        playerNames.add("p4");

        List<Position> positions = new ArrayList<>();

        positions.add(new Position(1, 2));
        positions.add(new Position(2, 1));
        positions.add(new Position(2, 2));
        positions.add(new Position(1, 1));

        state = GameObjectMother.ctfGameWithoutWallsItemsPowerFailures(new Dimension(10, 10), playerNames, positions);
        Grid grid = state.getGrid();

        //controleren of vlaggen op juiste square staan
        assertTrue(grid.getSquareAtPosition(new Position(1, 2)).getAvailableItems().size() == 1);
        assertTrue(grid.getSquareAtPosition(new Position(2, 1)).getAvailableItems().size() == 1);
        assertTrue(grid.getSquareAtPosition(new Position(1, 1)).getAvailableItems().size() == 1);
        assertTrue(grid.getSquareAtPosition(new Position(2, 2)).getAvailableItems().size() == 1);

        MovePlayerHandler movePlayerHandler = new MovePlayerHandler(state);
        PickUpItemHandler pickUpItemHandler = new PickUpItemHandler(state);
        EndTurnHandler endTurnHandler = new EndTurnHandler(state);
        TurnManager turnManager = state.getTurnManager();
        turnManager.getCurrentTurn().setMoved();
        turnManager.endTurn();

        //PLAYER 2
        Player playerTemp = turnManager.getCurrentTurn().getCurrentPlayer();
        movePlayerHandler.move(Direction.UP);
        for (int i = 0; i < 2; i++) {
            movePlayerHandler.move(Direction.RIGHT);
        }
        turnManager.getCurrentTurn().setMoved();
        endTurnHandler.endTurn();
        while (!turnManager.getCurrentTurn().getCurrentPlayer().equals(playerTemp)) {
            turnManager.getCurrentTurn().setMoved();
            endTurnHandler.endTurn();
        }
        for (int i = 0; i < 3; i++) {
            movePlayerHandler.move(Direction.RIGHT);
        }


        endTurnHandler.endTurn();

        //PLAYER 3
        playerTemp = turnManager.getCurrentTurn().getCurrentPlayer();
        for (int i = 0; i < 2; i++) {
            movePlayerHandler.move(Direction.DOWN);
        }

        turnManager.getCurrentTurn().setMoved();
        endTurnHandler.endTurn();
        while (!turnManager.getCurrentTurn().getCurrentPlayer().equals(playerTemp)) {
            turnManager.getCurrentTurn().setMoved();
            endTurnHandler.endTurn();
        }
        for (int i = 0; i < 3; i++) {
            movePlayerHandler.move(Direction.DOWN);
        }


        endTurnHandler.endTurn();


        //PLAYER 4
        playerTemp = turnManager.getCurrentTurn().getCurrentPlayer();
        movePlayerHandler.move(Direction.LEFT);
        for (int i = 0; i < 2; i++) {
            movePlayerHandler.move(Direction.DOWN);
        }

        endTurnHandler.endTurn();
        while (!turnManager.getCurrentTurn().getCurrentPlayer().equals(playerTemp)) {
            turnManager.getCurrentTurn().setMoved();
            endTurnHandler.endTurn();
        }
        for (int i = 0; i < 2; i++) {
            movePlayerHandler.move(Direction.DOWN);
        }
        endTurnHandler.endTurn();
        while (!turnManager.getCurrentTurn().getCurrentPlayer().equals(playerTemp)) {
            turnManager.getCurrentTurn().setMoved();
            endTurnHandler.endTurn();
        }
        for (int i = 0; i < 2; i++) {
            movePlayerHandler.move(Direction.DOWN);
        }
        endTurnHandler.endTurn();
        assertFalse(grid.getSquareAtPosition(new Position(1, 1)).isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(2, 1)).isObstructed());
        assertFalse(grid.getSquareAtPosition(new Position(2, 2)).isObstructed());

        //Verzamelen van de vlaggen
        //eerste vlag
        movePlayerHandler.move(Direction.RIGHT);
        pickUpItemHandler.pickUpItem(0);
        movePlayerHandler.move(Direction.RIGHT);
        endTurnWithoutGameOver(endTurnHandler, turnManager);
        movePlayerHandler.move(Direction.DOWN);

        movePlayerHandler.move(Direction.LEFT);
        movePlayerHandler.move(Direction.LEFT);

        endTurnWithoutGameOver(endTurnHandler, turnManager);

        //controleren of vlaggen op juiste square staan
        assertTrue(grid.getSquareAtPosition(new Position(1, 2)).getAvailableItems().size() == 1);
        assertTrue(grid.getSquareAtPosition(new Position(2, 1)).getAvailableItems().size() == 1);
        assertTrue(grid.getSquareAtPosition(new Position(1, 1)).getAvailableItems().size() == 1);
        assertFalse(grid.getSquareAtPosition(new Position(2, 2)).getAvailableItems().size() == 1);
        assertTrue(turnManager.getCurrentTurn().getCurrentPlayer().getInventoryItems().size() == 1);

        movePlayerHandler.move(Direction.UP);

        //controleren of vlaggen op juiste square staan
        assertTrue(grid.getSquareAtPosition(new Position(1, 2)).getAvailableItems().size() == 1);
        assertTrue(grid.getSquareAtPosition(new Position(2, 1)).getAvailableItems().size() == 1);
        assertTrue(grid.getSquareAtPosition(new Position(1, 1)).getAvailableItems().size() == 1);
        assertTrue(grid.getSquareAtPosition(new Position(2, 2)).getAvailableItems().size() == 1);
        assertTrue(turnManager.getCurrentTurn().getCurrentPlayer().getInventoryItems().size() == 0);

        //collecting 2de vlag
        movePlayerHandler.move(Direction.UP);

        pickUpItemHandler.pickUpItem(0);

        //controleren of vlaggen op juiste square staan
        assertTrue(grid.getSquareAtPosition(new Position(1, 2)).getAvailableItems().size() == 1);
        assertTrue(grid.getSquareAtPosition(new Position(2, 1)).getAvailableItems().size() == 1);
        assertFalse(grid.getSquareAtPosition(new Position(1, 1)).getAvailableItems().size() == 1);
        assertTrue(grid.getSquareAtPosition(new Position(2, 2)).getAvailableItems().size() == 1);
        assertTrue(turnManager.getCurrentTurn().getCurrentPlayer().getInventoryItems().size() == 1);

        endTurnWithoutGameOver(endTurnHandler, turnManager);

        for (int i = 0; i < 2; i++) {
            movePlayerHandler.move(Direction.RIGHT);
        }
        movePlayerHandler.move(Direction.DOWN);

        endTurnWithoutGameOver(endTurnHandler, turnManager);

        movePlayerHandler.move(Direction.DOWN);

        for (int i = 0; i < 2; i++) {
            movePlayerHandler.move(Direction.LEFT);
        }

        endTurnWithoutGameOver(endTurnHandler, turnManager);

        movePlayerHandler.move(Direction.UP);

        //controleren of vlaggen op juiste square staan
        assertTrue(grid.getSquareAtPosition(new Position(1, 2)).getAvailableItems().size() == 1);
        assertTrue(grid.getSquareAtPosition(new Position(2, 1)).getAvailableItems().size() == 1);
        assertTrue(grid.getSquareAtPosition(new Position(1, 1)).getAvailableItems().size() == 1);
        assertTrue(grid.getSquareAtPosition(new Position(2, 2)).getAvailableItems().size() == 1);
        assertTrue(turnManager.getCurrentTurn().getCurrentPlayer().getInventoryItems().size() == 0);

        //3de vlag

        movePlayerHandler.move(Direction.UP_RIGHT);

        pickUpItemHandler.pickUpItem(0);

        endTurnWithoutGameOver(endTurnHandler, turnManager);

        movePlayerHandler.move(Direction.RIGHT);

        for (int i = 0; i < 2; i++) {
            movePlayerHandler.move(Direction.DOWN);
        }

        endTurnWithoutGameOver(endTurnHandler, turnManager);


        for (int i = 0; i < 2; i++) {
            movePlayerHandler.move(Direction.LEFT);
        }

        //win
        boolean win = false;
        try {
            movePlayerHandler.move(Direction.UP);
        } catch (GameOverException exc) {
            win = true;
        }
        assertTrue(win);


    }

    private void endTurnWithoutGameOver(EndTurnHandler endTurnHandler, TurnManager turnManager) throws
            GameOverException {
        Player playerTemp;
        playerTemp = turnManager.getCurrentTurn().getCurrentPlayer();
        endTurnHandler.endTurn();
        while (!turnManager.getCurrentTurn().getCurrentPlayer().equals(playerTemp)) {
            turnManager.getCurrentTurn().setMoved();
            endTurnHandler.endTurn();
        }
    }


}
