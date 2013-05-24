package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.effect.Teleporter;
import be.kuleuven.swop.objectron.domain.exception.*;
import be.kuleuven.swop.objectron.domain.gamestate.Game;
import be.kuleuven.swop.objectron.domain.gamestate.GameObjectMother;
import be.kuleuven.swop.objectron.domain.grid.*;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.domain.util.Position;
import be.kuleuven.swop.objectron.handler.MovePlayerHandler;
import be.kuleuven.swop.objectron.handler.PickUpItemHandler;
import be.kuleuven.swop.objectron.handler.UseItemHandler;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 15/05/13
 * Time: 14:21
 * To change this template use File | Settings | File Templates.
 */
public class Test_Flag {

    private MovePlayerHandler movePlayerHandler;
    private PickUpItemHandler pickUpItemHandler;
    private UseItemHandler useItemHandler;
    private Player player1, player2;
    private Grid grid;

    private Position p1Pos;
    private Position p2Pos;
    private Game state;

    @Before
    public void setUp() throws GridTooSmallException, NumberOfPlayersException {
        Dimension dimension = new Dimension(10, 10);

        p1Pos = new Position(0, 9);
        p2Pos = new Position(2, 9);

        List<Position> positions = new ArrayList<>();
        positions.add(p1Pos);
        positions.add(p2Pos);

        List<String> playerNames = new ArrayList<>();
        playerNames.add("p1");
        playerNames.add("p2");

        state = GameObjectMother.ctfGameWithoutWallsItemsPowerFailures(dimension, playerNames, positions);
        grid = state.getGrid();

        movePlayerHandler = new MovePlayerHandler(state);
        pickUpItemHandler = new PickUpItemHandler(state);
        useItemHandler = new UseItemHandler(state);

        player1 = state.getTurnManager().getCurrentTurn().getCurrentPlayer();
        state.getTurnManager().getCurrentTurn().setMoved();
        state.getTurnManager().endTurn();

        player2 = state.getTurnManager().getCurrentTurn().getCurrentPlayer();
        state.getTurnManager().getCurrentTurn().setMoved();
        state.getTurnManager().endTurn();
    }

    @Test
    public void test_amount_flags() throws InventoryFullException, NotEnoughActionsException, InvalidMoveException,
            GameOverException, SquareOccupiedException {
        // grid.getSquareAtPosition(new Position(0, 9)).addItem(new Flag(player2, grid.getSquareAtPosition(p2Pos)));
        Flag vlag = new Flag(player2, grid.getSquareAtPosition(p2Pos));
        grid.getSquareAtPosition(new Position(1, 9)).addItem(vlag);
        grid.getSquareAtPosition(new Position(1, 8)).addItem(new Flag(player2, grid.getSquareAtPosition(p2Pos)));

        movePlayerHandler.move(Direction.RIGHT);
        pickUpItemHandler.pickUpItem(0);
        movePlayerHandler.move(Direction.UP);
        state.getTurnManager().getCurrentTurn().setMoved();
        state.getTurnManager().endTurn();
        pickUpItemHandler.pickUpItem(0);

        assertTrue(player1.getInventoryItems().size() == 1);
        assertTrue(player1.getCurrentSquare().getAvailableItems().size() == 1);
        assertTrue(grid.getSquareAtPosition(new Position(1, 9)).getAvailableItems().size() == 0);
    }

    @Test
    public void test_capture_own_Flag() throws InvalidMoveException, GameOverException, SquareOccupiedException, NotEnoughActionsException, InventoryFullException {
        grid.getSquareAtPosition(new Position(1, 9)).addItem(new Flag(player1, grid.getSquareAtPosition(p1Pos)));

        movePlayerHandler.move(Direction.RIGHT);
        assertTrue(grid.getSquareAtPosition(p1Pos).getAvailableItems().size() == 1);
        pickUpItemHandler.pickUpItem(0);
        assertTrue(grid.getSquareAtPosition(p1Pos).getAvailableItems().size() == 2);
        assertTrue(player1.getInventoryItems().size() == 0);
    }

    @Test
    public void test_player_hit_lightmine_flag_drop() throws InventoryFullException, NotEnoughActionsException, GameOverException, SquareOccupiedException, InvalidMoveException, NoItemSelectedException {

        grid.getSquareAtPosition(new Position(1, 9)).addItem(new LightMine());

        System.out.println(player1.getCurrentSquare().getPosition());
        movePlayerHandler.move(Direction.RIGHT);
        System.out.println(player1.getCurrentSquare().getPosition());

        pickUpItemHandler.pickUpItem(0);

        useItemHandler.selectItemFromInventory(0);
        useItemHandler.useCurrentItem();

        //ervoor zorgen dat lighttrail player 2 uit de weg is
        state.getTurnManager().getCurrentTurn().setMoved();
        state.getTurnManager().endTurn();
        state.getTurnManager().getCurrentTurn().setMoved();
        state.getTurnManager().endTurn();
        for (int i = 0; i < 3; i++) {
            movePlayerHandler.move(Direction.UP);
        }
        state.getTurnManager().endTurn();
        state.getTurnManager().getCurrentTurn().setMoved();
        state.getTurnManager().endTurn();
        for (int i = 0; i < 3; i++) {
            movePlayerHandler.move(Direction.UP);
        }
        state.getTurnManager().endTurn();
        System.out.println(player2.getCurrentSquare().getPosition());
        movePlayerHandler.move(Direction.UP_LEFT);
        System.out.println(player2.getCurrentSquare().getPosition());
        movePlayerHandler.move(Direction.DOWN_LEFT);
        System.out.println(player2.getCurrentSquare().getPosition());

        pickUpItemHandler.pickUpItem(0);
        assertTrue(player2.getInventoryItems().size() == 1);
        movePlayerHandler.move(Direction.RIGHT);
        assertTrue(player2.getInventoryItems().size() == 0);

        boolean checkVlagRandomNeighborSquare = false;
        for (Direction direction : Direction.values()) {
            if (player2.getCurrentSquare().getNeighbour(direction) != null) {
                for (Item item : player2.getCurrentSquare().getNeighbour(direction).getAvailableItems()) {
                    if (item instanceof Flag) {
                        Flag vlag = (Flag) item;
                        if (vlag.getOwner().equals(player1))
                            checkVlagRandomNeighborSquare = true;
                    }

                }
            }
        }
        assertTrue(checkVlagRandomNeighborSquare);
    }

    @Test
    public void test_player_teleport_flag_drop() throws InventoryFullException, NotEnoughActionsException, GameOverException, SquareOccupiedException, InvalidMoveException, NoItemSelectedException {
        Square squareTeleport1 = grid.getSquareAtPosition(new Position(1, 9));
        Square squareTeleport2 = grid.getSquareAtPosition(new Position(8, 9));

        Teleporter teleporter1 = new Teleporter(squareTeleport1);
        Teleporter teleporter2 = new Teleporter(squareTeleport2);
        teleporter1.setDestination(teleporter2);
        teleporter2.setDestination(teleporter1);
        squareTeleport1.addEffect(teleporter1);

        squareTeleport2.addEffect(teleporter2);


        //ervoor zorgen dat lighttrail player 2 uit de weg is
        state.getTurnManager().getCurrentTurn().setMoved();
        state.getTurnManager().endTurn();
        state.getTurnManager().getCurrentTurn().setMoved();
        state.getTurnManager().endTurn();
        for (int i = 0; i < 3; i++) {
            movePlayerHandler.move(Direction.UP);
        }
        state.getTurnManager().endTurn();
        state.getTurnManager().getCurrentTurn().setMoved();
        state.getTurnManager().endTurn();
        for (int i = 0; i < 3; i++) {
            movePlayerHandler.move(Direction.UP);
        }
        state.getTurnManager().endTurn();
        System.out.println(player2.getCurrentSquare().getPosition());
        movePlayerHandler.move(Direction.UP_LEFT);
        System.out.println(player2.getCurrentSquare().getPosition());
        movePlayerHandler.move(Direction.DOWN_LEFT);
        System.out.println(player2.getCurrentSquare().getPosition());

        pickUpItemHandler.pickUpItem(0);
        assertTrue(player2.getInventoryItems().size() == 1);
        movePlayerHandler.move(Direction.RIGHT);
        assertTrue(state.getTurnManager().getCurrentTurn().getCurrentPlayer().getCurrentSquare().equals(squareTeleport2));
        assertTrue(player2.getInventoryItems().size() == 0);

        boolean checkVlagRandomNeighborSquare = false;
        for (Direction direction : Direction.values()) {
            if (player2.getCurrentSquare().getNeighbour(direction) != null) {
                for (Item item : player2.getCurrentSquare().getNeighbour(direction).getAvailableItems()) {
                    if (item instanceof Flag) {
                        Flag vlag = (Flag) item;
                        if (vlag.getOwner().equals(player1))
                            checkVlagRandomNeighborSquare = true;
                    }

                }
            }
        }
        assertTrue(checkVlagRandomNeighborSquare);
    }

    @Test
    public void test_player_hit_identityDisc_flag_drop() throws InventoryFullException, NotEnoughActionsException, GameOverException, SquareOccupiedException, InvalidMoveException, NoItemSelectedException {

        grid.getSquareAtPosition(new Position(1, 9)).addItem(new UnchargedIdentityDisc());

        System.out.println(player1.getCurrentSquare().getPosition());
        movePlayerHandler.move(Direction.RIGHT);
        System.out.println(player1.getCurrentSquare().getPosition());

        pickUpItemHandler.pickUpItem(0);

        movePlayerHandler.move(Direction.UP_LEFT);
        System.out.println(player1.getCurrentSquare().getPosition());

        state.getTurnManager().endTurn();
        state.getTurnManager().getCurrentTurn().setMoved();
        state.getTurnManager().endTurn();
        for (int i = 0; i < 2; i++) {
            movePlayerHandler.move(Direction.UP);
            System.out.println(player1.getCurrentSquare().getPosition());
        }
        movePlayerHandler.move(Direction.RIGHT);
        System.out.println(player1.getCurrentSquare().getPosition());

        state.getTurnManager().endTurn();

        System.out.println("player 2");
        System.out.println(player2.getCurrentSquare().getPosition());
        movePlayerHandler.move(Direction.UP_LEFT);
        System.out.println(player2.getCurrentSquare().getPosition());
        movePlayerHandler.move(Direction.DOWN_LEFT);
        System.out.println(player2.getCurrentSquare().getPosition());

        pickUpItemHandler.pickUpItem(0);
        state.getTurnManager().endTurn();
        state.getTurnManager().getCurrentTurn().setMoved();
        state.getTurnManager().endTurn();

        movePlayerHandler.move(Direction.RIGHT);
        System.out.println(player2.getCurrentSquare().getPosition());
        state.getTurnManager().endTurn();
        useItemHandler.selectItemFromInventory(0);
        assertTrue(player2.getInventoryItems().size() == 1);
        assertTrue(state.getTurnManager().getCurrentTurn().getCurrentPlayer().equals(player1));
        useItemHandler.useCurrentIdentityDisc(Direction.DOWN);


        assertTrue(player2.getInventoryItems().size() == 0);

        boolean checkVlagRandomNeighborSquare = false;
        for (Direction direction : Direction.values()) {
            if (player2.getCurrentSquare().getNeighbour(direction) != null) {
                for (Item item : player2.getCurrentSquare().getNeighbour(direction).getAvailableItems()) {
                    if (item instanceof Flag) {
                        Flag vlag = (Flag) item;
                        if (vlag.getOwner().equals(player1))
                            checkVlagRandomNeighborSquare = true;
                    }

                }
            }
        }
        assertTrue(checkVlagRandomNeighborSquare);
    }


}
