package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.square.Square;

/**
 * @author : Kasper Vervaecke
 *         Date: 09/04/13
 *         Time: 16:32
 */
public class Teleporter implements Item {
    private static final String name = "Teleporter";

    private Square destination;
    private Square location;

    public Teleporter(Square location) {
        this.location = location;
    }

    @Override
    public String getName() {
        return name;
    }

    public Square getDestination() {
        return destination;
    }

    public void setDestination(Square destination) {
        this.destination = destination;
    }

    public Square getLocation() {
        return location;
    }

    public void activate(Player player) {
        //TODO implement
    }
}
