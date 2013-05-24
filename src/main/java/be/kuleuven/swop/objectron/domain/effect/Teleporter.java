package be.kuleuven.swop.objectron.domain.effect;

import be.kuleuven.swop.objectron.domain.exception.*;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
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

    /**
     * Initiates a teleporter on a given location
     * @param location the location of the new teleporter
     */
    public Teleporter(Square location) {
        this.location = location;
    }

    @Override
    public void activate(Movable movable, TurnManager manager) throws GameOverException, SquareOccupiedException,
            NotEnoughActionsException {
        if(!destination.getLocation().isObstructed()){

            try {
            movable.getTeleportStrategy().teleport(movable, this, manager);
            movable.dirsupted();
        } catch (InvalidMoveException | WallHitException | ForceFieldHitException | PlayerHitException e) {
            // teleportation not possible.. do nothing
           
        }
    }
    }

    /**
     * Teleports a movable
     * @param movable the movable to be teleported
     * @param manager the turnmanager
     * @throws InvalidMoveException
     *         The place where the movable wants to teleport isn't valid
     * @throws PlayerHitException
     *         You hit a player on the place you want to teleport
     * @throws WallHitException
     *         You hit a wall where you want to teleport
     * @throws ForceFieldHitException
     *         You hit a forcefield where you want to teleport
     * @throws GameOverException
     *         You win or lose the game because of the teleport
     * @throws SquareOccupiedException
     *         The Square is occupied
     * @throws NotEnoughActionsException
     *         the movable hasn't have enough actions left
     */
    public void teleport(Movable movable, TurnManager manager) throws InvalidMoveException, PlayerHitException,
            WallHitException, ForceFieldHitException, GameOverException, SquareOccupiedException,
            NotEnoughActionsException {
        movable.enter(destination.getLocation(), manager);
    }

    @Override
    public void accept(EffectVisitor visitor) {
        visitor.visitTeleporter();
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

    /**
     * Return the destination of the teleporter
     */
    public Teleporter getDestination() {
        return destination;
    }


}
