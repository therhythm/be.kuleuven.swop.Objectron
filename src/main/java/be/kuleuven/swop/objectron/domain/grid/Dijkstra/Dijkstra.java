package be.kuleuven.swop.objectron.domain.grid.Dijkstra;

import be.kuleuven.swop.objectron.domain.Direction;
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
    private Square destinationSquare;
    private ArrayList<TableEntry> L;
    private ArrayList<Square> T;
    private ArrayList<DirectedEdge> edges;

    public Dijkstra(ArrayList<DirectedEdge> edges, Square start, Square destination, ArrayList<Square> T) {
        this.edges = edges;
        this.startSquare = start;
        this.destinationSquare = destination;

        this.T = T;
    }

    public Dijkstra(Square start, Square destination, ArrayList<Square> nodes) {
        constructEdges(nodes);
        this.startSquare = start;
        this.destinationSquare = destination;

        this.T = nodes;
    }


    private void constructEdges(ArrayList<Square> nodes) {

        edges = new ArrayList<DirectedEdge>();
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
        ArrayList<DirectedEdge> result = new ArrayList<DirectedEdge>();

        for (DirectedEdge edge : edges) {
            if (edge.getSquareSource().equals(squareFrom) && T.contains(edge.getSquareDestination()))
                result.add(edge);
        }
        return result;
    }

    public Double getShortestDistance() {

        initialisation();

        TableEntry relaxedEntry = relax();
        while (relaxedEntry.getSquare() != destinationSquare) {
            relaxedEntry = relax();
            //System.out.println("lengte T: " + T.size());
            checkChangedT();
        }
        return relaxedEntry.getDistance();


    }

    private void checkChangedT() {
        int teller = 0;
        for (TableEntry tableEntry : L) {
            if (tableEntry.getDistance() != Double.POSITIVE_INFINITY)
                teller++;
        }
       //
       // System.out.println("L entries not pos_Inf: " + teller);
    }

    private void initialisation() {
        L = new ArrayList<TableEntry>();
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
            //System.out.println(tableEntry);
            if ((tableEntry.distance < smallestEntry.getDistance())&&(T.contains(tableEntry.getSquare()))) {
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

    public String toStringEdges(){
        String result = "";
        for(DirectedEdge directEdge : edges) {
            result += directEdge.toString() + '\n' + '\n';
        }

        return result;

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

        public void setSquare(Square square) {
            this.square = square;
        }

        public Double getDistance() {
            return distance;
        }

        public void setDistance(Double distance) {
            this.distance = distance;
        }

        public String toString() {
            String result = "";
            result += square.getPosition().toString() + '\t' + "distance: " + distance;
            return result;

        }
    }
}
