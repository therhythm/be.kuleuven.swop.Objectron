package be.kuleuven.swop.objectron.domain.effect;

import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.movement.Movement;
import be.kuleuven.swop.objectron.domain.square.Square;

/**
 * @author : Kasper Vervaecke
 *         Date: 09/04/13
 *         Time: 16:32
 */
public class Teleporter implements Effect {
    private Teleporter destination;
    private Square location;

    /**
     * Initiates a teleporter on a given location
     * @param location the location of the new teleporter
     */
    public Teleporter(Square location) {
        this.location = location;
    }

    @Override
    public void accept(EffectVisitor visitor) {
        visitor.visitTeleporter();
    }

    @Override
    public void activate(Movement movement, TurnManager manager) {
        if(!destination.getLocation().isObstructed()){
            movement.teleport(destination.getLocation());
        }
    }

    /**
     * sets the destination of the teleporter
     * @param destination The destination teleporter
     */
    public void setDestination(Teleporter destination) {
        this.destination = destination;
    }

    /**
     * Return the location of the teleporter
     */
    public Square getLocation() {
        return location;
    }
}
