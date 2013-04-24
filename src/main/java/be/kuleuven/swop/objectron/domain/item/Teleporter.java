package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.effect.ActivateRequest;
import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.square.Square;

/**
 * @author : Kasper Vervaecke
 *         Date: 09/04/13
 *         Time: 16:32
 */
//TODO make effect asap
public class Teleporter implements Item {
    private Teleporter destination;
    private Square location;

    public Teleporter(Square location) {
        this.location = location;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public boolean pickupAble() {
        return false;
    }

    @Override
    public void activate(ActivateRequest activateRequest) {
        if (activateRequest.getCurrentPlayer() != null) {
            if (!activateRequest.getCurrentPlayer().isTeleporting() && !destination.getLocation().isObstructed()) {
                activateRequest.getCurrentPlayer().teleport(destination.getLocation());
            }
        }
    }

    @Override
    public void place(Square targetSquare) throws SquareOccupiedException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void throwMe(Square sourceSquare, Direction targetDirection, GameState state) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Teleporter getDestination() {
        return destination;
    }

    public void setDestination(Teleporter destination) {
        this.destination = destination;
    }

    public Square getLocation() {
        return location;
    }
}
