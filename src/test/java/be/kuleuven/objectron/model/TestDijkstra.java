package be.kuleuven.objectron.model;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.exception.GameOverException;
import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.grid.Dijkstra.Dijkstra;
import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.grid.GridFactory;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.domain.util.Position;
import be.kuleuven.swop.objectron.handler.EndTurnHandler;
import be.kuleuven.swop.objectron.handler.MovePlayerHandler;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 1/05/13
 * Time: 16:00
 * To change this template use File | Settings | File Templates.
 */
public class TestDijkstra {
    private EndTurnHandler endTurnHandler;
    private MovePlayerHandler movePlayerHandler;
    private Grid grid;
    private GameState state;
    private Position p1Pos;
    private Position p2Pos;
    private Dimension dimension;

    @Before
    public void setUp() throws GridTooSmallException {

        p1Pos = new Position(1, 8);
        p2Pos = new Position(3, 8);
        dimension = new Dimension(10, 10);
        grid = GridFactory.gridWithoutWallsItemsPowerFailures(dimension, p1Pos, p2Pos);
        state = new GameState("p1", "p2", p1Pos, p2Pos, grid);
        movePlayerHandler = new MovePlayerHandler(state);
        endTurnHandler = new EndTurnHandler(state);
    }

    @Test
         public void test_DijkstraShortestPath_straight_line()  {
        Square startSquare = grid.getSquareAtPosition(new Position(0,0));
        Square destinationSquare = grid.getSquareAtPosition(new Position(0,5));
        Dijkstra dijkstra = new Dijkstra(startSquare,destinationSquare,grid.getSquaresNotObstructed()) ;

        //System.out.println(dijkstra.toStringEdges());
        Double distance = dijkstra.getShortestDistance();
        System.out.println("Distance: " + distance);
        assertTrue(distance ==5);
    }

    @Test
    public void test_DijkstraShortestPath_not_straight()  {
        Square startSquare = grid.getSquareAtPosition(new Position(0,0));
        Square destinationSquare = grid.getSquareAtPosition(new Position(0,5));
        Dijkstra dijkstra = new Dijkstra(startSquare,destinationSquare,grid.getSquaresNotObstructed()) ;

        //System.out.println(dijkstra.toStringEdges());
        System.out.println("Distance: " + dijkstra.getShortestDistance());
        assertTrue(dijkstra.getShortestDistance()==5);
    }

}
