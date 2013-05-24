package be.kuleuven.swop.objectron.domain.grid.Dijkstra;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.exception.SquareUnreachableException;
import be.kuleuven.swop.objectron.domain.square.Square;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 1/05/13
 * Time: 14:22
 * To change this template use File | Settings | File Templates.
 */
public class Dijkstra {
    private Square startSquare;
    private ArrayList<TableEntry> L;
    private ArrayList<Square> T;
    private ArrayList<DirectedEdge> edges;
    private ArrayList<Square> nodes;


    /**
     * Initializes a new Dijkstra Object with the given nodes and construcs the edges
     * @param nodes a list of squares
     * @post The list of nodes is equal to the given list of nodes
     *       |this.nodes.equals(nodes)
     */
    public Dijkstra(ArrayList<Square> nodes) {
        constructEdges(nodes);

        this.nodes = nodes;
    }


    /**
     * Construcs the edges with a given list of nodes
     * @param nodes The list of nodes to construct the edges with
     */
    private void constructEdges(ArrayList<Square> nodes) {

        edges = new ArrayList<>();
        for (Square square : nodes) {
            for (Direction direction : Direction.values()) {
                Square neighbor = square.getNeighbour(direction);
                if (neighbor != null)
                    if (nodes.contains(neighbor) && !containsEdge(square, neighbor))
                        edges.add(new DirectedEdge(square, neighbor, 1));
            }
        }
    }

    /**
     * Checks if the give squares already are the destination of an edge
     * @param start The start square
     * @param destination The destination square
     */
    private boolean containsEdge(Square start, Square destination) {
        for (DirectedEdge directEdge : edges) {
            if (directEdge.getSquareSource().equals(start) && directEdge.getSquareDestination().equals(destination))
                return true;

        }
        return false;
    }

    /**
     * Returns a list of Directed edges which the given square is the source of
     */
    private ArrayList<DirectedEdge> getSubsetEdges(Square squareFrom) {
        ArrayList<DirectedEdge> result = new ArrayList<>();

        for (DirectedEdge edge : edges) {
            if (edge.getSquareSource().equals(squareFrom) && T.contains(edge.getSquareDestination()))
                result.add(edge);
        }
        return result;
    }

    /**
     * Gets the shortest distance from Square A to Square B
     *
     * @param start Square A
     * @param destination Square B
     * @return The shortest distance
     * @throws SquareUnreachableException
     *          You cannot reach Square B from Sqaure A because the route is obstructed in all possible ways
     */
    public Double getShortestDistance(Square start, Square destination) throws SquareUnreachableException {
        this.startSquare = start;

        initialisation();

        TableEntry relaxedEntry = relax();

        while (!relaxedEntry.getSquare().equals(destination)) {
            relaxedEntry = relax();

            if(relaxedEntry.getSquare() == null){
                throw new SquareUnreachableException("square is unreachable");
            }
        }
        return relaxedEntry.getDistance();
    }

    /**
     * Initialises objects to be able to calculate the shortest distance
     */
    private void initialisation() {
        this.T = new ArrayList<>();
        this.T.addAll(nodes);
        L = new ArrayList<>();
        L.add(new TableEntry(startSquare, 0.0));
        for (Square square : T) {
            L.add(new TableEntry(square, Double.POSITIVE_INFINITY));
        }
    }

    /**
     * Look for the next shortest distance and return the TableEntry
     */
    private TableEntry relax() {
        TableEntry nextEntry = getSquareSmallestDistance();
        T.remove(nextEntry.getSquare());
        ArrayList<DirectedEdge> subset = getSubsetEdges(nextEntry.getSquare());
        for (DirectedEdge directedEdge : subset) {
            double tempDistance = nextEntry.getDistance() + directedEdge.getDistance();
            if (tempDistance < getDistance(directedEdge.getSquareDestination()))
                changeDistanceTableEntry(directedEdge.getSquareDestination(), tempDistance);
        }
        return nextEntry;
    }

    /**
     * chanes the table entry for a given square
     * @param square The square to change the entry for
     * @param distance The new distance for the entry
     */
    private void changeDistanceTableEntry(Square square, Double distance) {
        for (TableEntry tableEntry : L) {
            if (tableEntry.getSquare().equals(square))
                tableEntry.setDistance(distance);
        }
    }

    /**
     * Gets the Table Entry with the smallest distance
     */
    private TableEntry getSquareSmallestDistance() {
        TableEntry smallestEntry = new TableEntry(null, Double.POSITIVE_INFINITY);
        for (TableEntry tableEntry : L) {
            boolean isDistanceSmaller = tableEntry.distance < smallestEntry.getDistance();
            boolean existInT = T.contains(tableEntry.getSquare());
            if (isDistanceSmaller && existInT) {
                smallestEntry = tableEntry;
            }

        }
        return smallestEntry;
    }

    /**
     * Gets the distance from a corresponding Table Entry for a given Square
     * @param square the square to find the distance for
     * @return the distance
     */
    private Double getDistance(Square square) {
        for (TableEntry tableEntry : L) {
            if (tableEntry.getSquare().equals(square))
                return tableEntry.getDistance();
        }
        return Double.POSITIVE_INFINITY;
    }

    private class TableEntry {
        private Square square;
        private Double distance;

        private TableEntry(Square square, Double distance) {
            this.square = square;
            this.distance = distance;
        }

        public Square getSquare() {
            return square;
        }

        public Double getDistance() {
            return distance;
        }

        public void setDistance(Double distance) {
            this.distance = distance;
        }
    }
}
