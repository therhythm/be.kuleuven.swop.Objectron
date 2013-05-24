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

    /**
     * Initializes a new DirectedEdge with a give source, destination and distance
     *
     * @param squareSource The source square
     * @param squareDestination the destination square
     * @param distance the distance
     * @post the source is equal to the given squareSource
     *       |this.squareSource.equals(squareSource)
     * @post the destination is equal to the given destination
     *       |this.squareDestination.equals(squareDestination)
     * @post the distance is equal to the given distance
     *       |this.distance == distance
     *
     */
    public DirectedEdge(Square squareSource, Square squareDestination, int distance) {
        this.squareSource = squareSource;
        this.squareDestination = squareDestination;
        this.distance = distance;
    }

    /**
     * Returns the source square for this edge
     */
    public Square getSquareSource() {
        return squareSource;
    }

    /**
     * Returns the destination square for this edge
     */
    public Square getSquareDestination() {
        return squareDestination;
    }

    /**
     * Returns the distance for this edge
     */
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
