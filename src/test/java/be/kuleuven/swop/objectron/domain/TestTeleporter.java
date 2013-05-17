package be.kuleuven.swop.objectron.domain;

import be.kuleuven.swop.objectron.domain.effect.Teleporter;
import be.kuleuven.swop.objectron.domain.gamestate.gamemode.RaceMode;
import be.kuleuven.swop.objectron.domain.exception.*;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.grid.GridFactory;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.domain.util.Position;
import be.kuleuven.swop.objectron.handler.MovePlayerHandler;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;


/**
 * @author : Kasper Vervaecke
 *         Date: 10/04/13
 *         Time: 13:18
 */
public class TestTeleporter {
    private GameState gameState;
    private Player player;
    private Square currentSquare;
    private MovePlayerHandler movePlayerHandler;

    @Before
    public void setUp() throws GridTooSmallException {
        Dimension dimension = new Dimension(10, 10);

        List<Position> positions = new ArrayList<Position>();
        positions.add(new Position(0, 9));
        positions.add(new Position(9, 0));

        Grid grid = GridFactory.gridWithoutWallsItemsPowerFailures(dimension, positions);
        List<String> playerNames = new ArrayList<>();
        playerNames.add("p1");
        playerNames.add("p2");
        gameState = new GameState(playerNames, positions, grid,new RaceMode());
        player = gameState.getTurnManager().getCurrentTurn().getCurrentPlayer();
        currentSquare = player.getCurrentSquare();
        movePlayerHandler = new MovePlayerHandler(gameState);
    }

    @Test
    public void test_basic_flow() throws NotEnoughActionsException, SquareOccupiedException, InvalidMoveException, GameOverException {
        Square upNeighbor = currentSquare.getNeighbour(Direction.UP);
        Square rightNeighbor = currentSquare.getNeighbour(Direction.RIGHT);
        Teleporter teleporter1 = new Teleporter(upNeighbor);
        Teleporter teleporter2 = new Teleporter(rightNeighbor);
        teleporter1.setDestination(teleporter2);
        teleporter2.setDestination(teleporter1);
        upNeighbor.addEffect(teleporter1);

        rightNeighbor.addEffect(teleporter2);


        movePlayerHandler.move(Direction.UP);

        assertTrue(player.getCurrentSquare() == rightNeighbor);
    }
}
