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


    public Dijkstra(ArrayList<Square> nodes) {
        constructEdges(nodes);

        this.nodes = nodes;
    }


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

    private boolean containsEdge(Square start, Square destination) {
        for (DirectedEdge directEdge : edges) {
            if (directEdge.getSquareSource().equals(start) && directEdge.getSquareDestination().equals(destination))
                return true;

        }
        return false;
    }

    private ArrayList<DirectedEdge> getSubsetEdges(Square squareFrom) {
        ArrayList<DirectedEdge> result = new ArrayList<>();

        for (DirectedEdge edge : edges) {
            if (edge.getSquareSource().equals(squareFrom) && T.contains(edge.getSquareDestination()))
                result.add(edge);
        }
        return result;
    }

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

    private void initialisation() {
        this.T = new ArrayList<>();
        this.T.addAll(nodes);
        L = new ArrayList<>();
        L.add(new TableEntry(startSquare, 0.0));
        for (Square square : T) {
            L.add(new TableEntry(square, Double.POSITIVE_INFINITY));
        }
    }

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

    private void changeDistanceTableEntry(Square square, Double distance) {
        for (TableEntry tableEntry : L) {
            if (tableEntry.getSquare().equals(square))
                tableEntry.setDistance(distance);
        }
    }

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
