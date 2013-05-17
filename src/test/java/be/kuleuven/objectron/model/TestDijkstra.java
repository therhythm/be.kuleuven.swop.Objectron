package be.kuleuven.objectron.model;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.exception.GameOverException;
import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.gamestate.gamemode.RaceMode;
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

import java.util.ArrayList;
import java.util.List;

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
    private List<Position> positions;
    private Dimension dimension;

    @Before
    public void setUp() throws GridTooSmallException {
                                    positions = new ArrayList<>();

      Position  p1Pos = new Position(1, 8);
       Position p2Pos = new Position(3, 8);

        positions.add(p1Pos);
        positions.add(p2Pos);
        List<String> playerNames = new ArrayList<>();
        playerNames.add("p1");
        playerNames.add("p2");

        dimension = new Dimension(10, 10);
        ArrayList<Position> wallPositions = new ArrayList<Position>();
        wallPositions.add(new Position(6, 6));
        wallPositions.add(new Position(5, 6));
        wallPositions.add(new Position(4, 6));



        grid = GridFactory.gridWithSpecifiedWallsPowerFailuresItems(dimension,positions, wallPositions);

        state = new GameState(playerNames, positions, grid,new RaceMode());
    }

    @Test
    public void test_DijkstraShortestPath_straight_line() {
        Square startSquare = grid.getSquareAtPosition(new Position(0, 0));
        Square destinationSquare = grid.getSquareAtPosition(new Position(0, 5));
        Dijkstra dijkstra = new Dijkstra(grid.getSquaresNotObstructed());

        //System.out.println(dijkstra.toStringEdges());
        Double distance = dijkstra.getShortestDistance(startSquare, destinationSquare);
        System.out.println("Distance: " + distance);
        assertTrue(distance == 5);
    }

    @Test
    public void test_DijkstraShortestPath_not_straight_1() {
        Square startSquare = grid.getSquareAtPosition(new Position(0, 0));
        Square destinationSquare = grid.getSquareAtPosition(new Position(1, 5));
        Dijkstra dijkstra = new Dijkstra(grid.getSquaresNotObstructed());

        Double distance = dijkstra.getShortestDistance(startSquare, destinationSquare);
        System.out.println("Distance: " + distance);
        assertTrue(distance == 5);
    }

    @Test
    public void test_DijkstraShortestPath_not_straight_2() {
        Square startSquare = grid.getSquareAtPosition(new Position(0, 0));
        Square destinationSquare = grid.getSquareAtPosition(new Position(2, 6));
        Dijkstra dijkstra = new Dijkstra(grid.getSquaresNotObstructed());

        Double distance = dijkstra.getShortestDistance(startSquare, destinationSquare);
        System.out.println("Distance: " + distance);
        assertTrue(distance == 6);
    }

    @Test
    public void test_DijkstraShortestPath_not_straight_3() {
        Square startSquare = grid.getSquareAtPosition(new Position(5, 5));
        Square destinationSquare = grid.getSquareAtPosition(new Position(2, 3));
        Dijkstra dijkstra = new Dijkstra(grid.getSquaresNotObstructed());

        Double distance = dijkstra.getShortestDistance(startSquare, destinationSquare);
        System.out.println("Distance: " + distance);
        assertTrue(distance == 3);
    }

    @Test
    public void test_DijkstraShortestPath_with_wall() {
        Square startSquare = grid.getSquareAtPosition(new Position(5, 5));
        Square destinationSquare = grid.getSquareAtPosition(new Position(5, 7));
        Dijkstra dijkstra = new Dijkstra(grid.getSquaresNotObstructed());

        Double distance = dijkstra.getShortestDistance(startSquare, destinationSquare);
        System.out.println("Distance: " + distance);
        assertTrue(dijkstra.getShortestDistance(startSquare, destinationSquare) == 4);
    }

}
