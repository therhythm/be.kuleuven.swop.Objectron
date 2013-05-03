package be.kuleuven.swop.objectron.domain.item.effect;

import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.movement.Movable;
import be.kuleuven.swop.objectron.domain.square.Square;

/**
 * @author : Kasper Vervaecke
 *         Date: 09/04/13
 *         Time: 16:32
 */
public class Teleporter implements Effect {
    private Teleporter destination;
    private Square location;

    public Teleporter(Square location) {
        this.location = location;
    }

    //todo change activate signature... but lightmine needs a fix first
    public void activate(Movable movable) {
        try {
            movable.getTeleportStrategy().teleport(movable,this);
        } catch (InvalidMoveException e) {
            //TODO check exception..
        }
    }

    public void teleport(Movable movable) throws InvalidMoveException {
        movable.enter(destination.getLocation());
    }

    @Override
    public void activate(Turn currentTurn) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void accept(EffectVisitor visitor) {
        visitor.visitTeleporter();
    }

    public void setDestination(Teleporter destination) {
        this.destination = destination;
    }

    public Square getLocation() {
        return location;
    }
}
