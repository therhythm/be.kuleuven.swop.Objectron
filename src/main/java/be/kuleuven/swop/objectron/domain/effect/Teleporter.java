package be.kuleuven.swop.objectron.domain.effect;

import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.movement.Movable;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.exception.ForceFieldHitException;
import be.kuleuven.swop.objectron.domain.exception.PlayerHitException;
import be.kuleuven.swop.objectron.domain.exception.WallHitException;

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

    @Override
    public void activate(Movable movable, TurnManager manager) {
        try {
            movable.getTeleportStrategy().teleport(movable,this, manager);
        } catch (InvalidMoveException | WallHitException | ForceFieldHitException | PlayerHitException e) {
            // teleportation not possible.. do nothing
        }
    }

    public void teleport(Movable movable, TurnManager manager) throws InvalidMoveException, PlayerHitException, WallHitException, ForceFieldHitException {
        movable.enter(destination.getLocation(), manager);
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

    public Teleporter getDestination() {
        return destination;
    }
}
