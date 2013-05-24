package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.exception.*;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.movement.IdentityDiscMovementStrategy;
import be.kuleuven.swop.objectron.domain.movement.Movable;
import be.kuleuven.swop.objectron.domain.movement.MovementStrategy;
import be.kuleuven.swop.objectron.domain.movement.teleport.IdentityDiscTeleportStrategy;
import be.kuleuven.swop.objectron.domain.movement.teleport.TeleportStrategy;
import be.kuleuven.swop.objectron.domain.square.Square;

/**
 * Created with IntelliJ IDEA.
 * User: Peter
 * Date: 8/04/13
 * Time: 20:04
 * To change this template use File | Settings | File Templates.
 */
public class IdentityDisc implements Item, Movable {
    private static final int MAX_IN_BAG = Integer.MAX_VALUE; // Can't have annoying side effects. It would be
    // impossible for a game to have even this amount of items.

    private IdentityDiscBehavior identityDiscBehavior;
    private TeleportStrategy teleportStrategy;
    private MovementStrategy movementStrategy;

    public IdentityDisc(IdentityDiscBehavior identityDiscBehavior) {
        this.identityDiscBehavior = identityDiscBehavior;
        this.teleportStrategy = new IdentityDiscTeleportStrategy();

    }

    @Override
    public String getName() {
        return identityDiscBehavior.getName();
    }

    @Override
    public void place(Square targetSquare) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void throwMe(Square sourceSquare, Direction targetDirection, TurnManager turnManager) throws
            GameOverException, NotEnoughActionsException, SquareOccupiedException {
        if (!validDirection(targetDirection)) {
            throw new IllegalArgumentException("No diagonal direction allowed"); //todo domain exception (invariant!)
        }
        movementStrategy = new IdentityDiscMovementStrategy(turnManager, identityDiscBehavior);

        Square currentSquare = sourceSquare;
        Square neighbor = currentSquare.getNeighbour(targetDirection);

        boolean forceFieldHit = false;
        while (identityDiscBehavior.getRemainingRange() > 0) {
            if (neighbor == null)
                break;

            try {
                enter(neighbor,turnManager);
            } catch (InvalidMoveException e) {
                break;
            } catch (WallHitException e) {
                break;
            } catch (PlayerHitException e) {
                currentSquare = neighbor;
                break;
            } catch (ForceFieldHitException e) {
                forceFieldHit = true;
                break;
            }


            currentSquare = neighbor;
            neighbor =   currentSquare.getNeighbour(targetDirection);
            identityDiscBehavior.moved();
        }

        if (!forceFieldHit) {
            currentSquare.addItem(this);
        }
        identityDiscBehavior.reset();
    }


    @Override
    public void pickedUp() {
        //do nothing
    }

    @Override
    public int getMaxInBag() {
        return MAX_IN_BAG;
    }

    @Override
    public void effectActivated(EffectActivation activation) {
        // we dont do anything on activation
    }

    private boolean validDirection(Direction direction) {
        return direction != Direction.UP_LEFT && direction != Direction.UP_RIGHT && direction != Direction.DOWN_LEFT
                && direction != Direction.DOWN_RIGHT;
    }

    public String toString() {
        String result = "";
        result += "name: " + this.getName();

        return result;
    }

    @Override
    public TeleportStrategy getTeleportStrategy() {
        return this.teleportStrategy;
    }

    @Override
    public MovementStrategy getMovementStrategy() {
        return this.movementStrategy;
    }

    @Override
    public void enter(Square square, TurnManager manager) throws InvalidMoveException, PlayerHitException,
            WallHitException, ForceFieldHitException, GameOverException, NotEnoughActionsException,
            SquareOccupiedException {

        square.stepOn(this, manager);
    }

    @Override
    public void dirsupted() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void moved() {
        identityDiscBehavior.moved();
    }
}
