package be.kuleuven.swop.objectron.domain.grid.Dijkstra;

import be.kuleuven.swop.objectron.domain.square.Square;

/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 1/05/13
 * Time: 14:31
 * To change this template use File | Settings | File Templates.
 */
public class DirectedEdge {
    private Square squareSource;
    private Square squareDestination;
    private int distance;

    public DirectedEdge(Square squareSource, Square squareDestination, int distance) {
        this.squareSource = squareSource;
        this.squareDestination = squareDestination;
        this.distance = distance;
    }

    public Square getSquareSource() {
        return squareSource;
    }

    public Square getSquareDestination() {
        return squareDestination;
    }

    public int getDistance() {
        return distance;
    }

    public String toString() {
        String result = "square 1: ";
        result += '\t' + squareSource.getPosition().toString() + '\n';

        result += "square 2: ";
        result += '\t' + squareDestination.getPosition().toString() + '\n';
        result += "distance: " + distance;
        return result;
    }
}
