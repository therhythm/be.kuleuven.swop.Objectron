package be.kuleuven.objectron.model;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException;
import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.grid.GridFactory;
import be.kuleuven.swop.objectron.domain.item.Teleporter;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.domain.util.Position;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author : Kasper Vervaecke
 *         Date: 10/04/13
 *         Time: 13:18
 */
public class TestTeleporter {
    private GameState gameState;
    private Player player;
    private Square currentSquare;

    @Before
    public void setUp() throws GridTooSmallException {
        Dimension dimension = new Dimension(10,10);
        Grid grid = GridFactory.gridWithoutWalls(dimension, new Position(0,9), new Position(9,0));
        gameState = new GameState("p1", "p2", dimension, grid);
        player = gameState.getCurrentPlayer();
        currentSquare = player.getCurrentSquare();
    }

    @Test
    public void test_basic_flow() throws NotEnoughActionsException, SquareOccupiedException {
        Square upNeighbor = currentSquare.getNeighbour(Direction.UP);
        Square rightNeighbor = currentSquare.getNeighbour(Direction.RIGHT);
        Teleporter teleporter1 = new Teleporter(upNeighbor);
        Teleporter teleporter2 = new Teleporter(rightNeighbor);
        teleporter1.setDestination(teleporter2);
        teleporter2.setDestination(teleporter1);
        upNeighbor.addItem(teleporter1);
        upNeighbor.setActiveItem(teleporter1);
        rightNeighbor.addItem(teleporter2);
        rightNeighbor.setActiveItem(teleporter2);

        player.move(upNeighbor);

        assertTrue(player.getCurrentSquare() == rightNeighbor);
    }
}
