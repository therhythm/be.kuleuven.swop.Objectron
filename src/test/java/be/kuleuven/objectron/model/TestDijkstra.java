package be.kuleuven.objectron.model;

import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.exception.SquareUnreachableException;
import be.kuleuven.swop.objectron.domain.grid.Dijkstra.Dijkstra;
import be.kuleuven.swop.objectron.domain.grid.GeneratedGridBuilder;
import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.grid.GridBuilder;
import be.kuleuven.swop.objectron.domain.grid.GridObjectMother;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.domain.util.Position;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 1/05/13
 * Time: 16:00
 * To change this template use File | Settings | File Templates.
 */
public class TestDijkstra {
    private Grid grid;

    @Before
    public void setUp() throws GridTooSmallException {
        List<Position> positions = new ArrayList<>();
        Position p1Pos = new Position(1, 8);
        Position p2Pos = new Position(3, 8);
        positions.add(p1Pos);
        positions.add(p2Pos);

        Dimension dimension = new Dimension(10, 10);

        ArrayList<Position> wallPositions = new ArrayList<>();
        wallPositions.add(new Position(6, 6));
        wallPositions.add(new Position(5, 6));
        wallPositions.add(new Position(4, 6));

        GridBuilder builder = new GeneratedGridBuilder(dimension, 2);
        builder.setStartingPositions(positions);
        grid = GridObjectMother.gridWithSpecifiedWallsPowerFailuresItems(builder, wallPositions);
    }

    @Test
    public void test_DijkstraShortestPath_straight_line() throws SquareUnreachableException {
        Square startSquare = grid.getSquareAtPosition(new Position(0, 0));
        Square destinationSquare = grid.getSquareAtPosition(new Position(0, 5));
        Dijkstra dijkstra = new Dijkstra(grid.getSquaresNotObstructed());

        Double distance = dijkstra.getShortestDistance(startSquare, destinationSquare);
        System.out.println("Distance: " + distance);
        assertTrue(distance == 5);
    }

    @Test
    public void test_DijkstraShortestPath_not_straight_1() throws SquareUnreachableException {
        Square startSquare = grid.getSquareAtPosition(new Position(0, 0));
        Square destinationSquare = grid.getSquareAtPosition(new Position(1, 5));
        Dijkstra dijkstra = new Dijkstra(grid.getSquaresNotObstructed());

        Double distance = dijkstra.getShortestDistance(startSquare, destinationSquare);
        System.out.println("Distance: " + distance);
        assertTrue(distance == 5);
    }

    @Test
    public void test_DijkstraShortestPath_not_straight_2() throws SquareUnreachableException {
        Square startSquare = grid.getSquareAtPosition(new Position(0, 0));
        Square destinationSquare = grid.getSquareAtPosition(new Position(2, 6));
        Dijkstra dijkstra = new Dijkstra(grid.getSquaresNotObstructed());

        Double distance = dijkstra.getShortestDistance(startSquare, destinationSquare);
        System.out.println("Distance: " + distance);
        assertTrue(distance == 6);
    }

    @Test
    public void test_DijkstraShortestPath_not_straight_3() throws SquareUnreachableException {
        Square startSquare = grid.getSquareAtPosition(new Position(5, 5));
        Square destinationSquare = grid.getSquareAtPosition(new Position(2, 3));
        Dijkstra dijkstra = new Dijkstra(grid.getSquaresNotObstructed());

        Double distance = dijkstra.getShortestDistance(startSquare, destinationSquare);
        System.out.println("Distance: " + distance);
        assertTrue(distance == 3);
    }

    @Test
    public void test_DijkstraShortestPath_with_wall() throws SquareUnreachableException {
        Square startSquare = grid.getSquareAtPosition(new Position(5, 5));
        Square destinationSquare = grid.getSquareAtPosition(new Position(5, 7));
        Dijkstra dijkstra = new Dijkstra(grid.getSquaresNotObstructed());

        Double distance = dijkstra.getShortestDistance(startSquare, destinationSquare);
        System.out.println("Distance: " + distance);
        assertTrue(dijkstra.getShortestDistance(startSquare, destinationSquare) == 4);
    }

}
