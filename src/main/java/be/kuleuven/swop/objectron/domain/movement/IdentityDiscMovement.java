package be.kuleuven.swop.objectron.domain.movement;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.LightTrail;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.item.IdentityDisc;
import be.kuleuven.swop.objectron.domain.item.forceField.ForceField;
import be.kuleuven.swop.objectron.domain.square.Square;

/**
 * @author : Nik Torfs
 *         Date: 24/05/13
 *         Time: 14:49
 */
public class IdentityDiscMovement extends Movement {

    public IdentityDiscMovement(IdentityDisc movable, Direction direction, Square currentSquare, MovementRangeStrategy
            movementRangeStrategy, TurnManager manager) {
        super(movable, direction, currentSquare, movementRangeStrategy, manager);
    }

    @Override
    public void hitPlayer(Player player) throws InvalidMoveException {
        if (player.equals(manager.getCurrentTurn().getCurrentPlayer())) {
            manager.endTurn();
        }
        manager.getCurrentTurn().extraTurn();
        player.disrupted();

        currentSquare = player.getCurrentSquare();
        throw new InvalidMoveException();
    }


    @Override
    public void hitForceField(ForceField forceField) throws InvalidMoveException {
        //this can only be constructed with an identitydisc..
        IdentityDisc disc = (IdentityDisc)getMovable();
        disc.destroy();
        super.hitForceField(forceField);
    }

    @Override
    public void hitLightTrail(LightTrail lightTrail) throws InvalidMoveException {
        // do nothing
    }

    @Override
    public void powerFailure(boolean hasLightMine) {
        movementRangeStrategy.moved();
    }
}
