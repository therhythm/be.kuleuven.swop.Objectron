package be.kuleuven.swop.objectron.domain.movement;

import be.kuleuven.swop.objectron.domain.LightTrail;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.Wall;
import be.kuleuven.swop.objectron.domain.exception.ForceFieldHitException;
import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.exception.PlayerHitException;
import be.kuleuven.swop.objectron.domain.exception.WallHitException;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.item.IdentityDiscBehavior;
import be.kuleuven.swop.objectron.domain.item.forceField.ForceField;

/**
 * @author : Nik Torfs
 *         Date: 03/05/13
 *         Time: 00:30
 */
//todo let Movable delegate this
public class IdentityDiscMovementStrategy implements MovementStrategy {
    private TurnManager turnManager;
    private IdentityDiscBehavior identityDiscBehavior;


    public IdentityDiscMovementStrategy(TurnManager turnManager, IdentityDiscBehavior identityDiscBehavior) {
        this.turnManager = turnManager;
        this.identityDiscBehavior = identityDiscBehavior;
    }

    @Override
    public void powerFailure(boolean hasLightMine) {
        identityDiscBehavior.moved();
    }

    @Override
    public void hitPlayer(Player player) throws PlayerHitException {
        if (player.equals(turnManager.getCurrentTurn().getCurrentPlayer())) {
            turnManager.endTurn();
        }
        turnManager.getCurrentTurn().extraTurn();
        throw new PlayerHitException();
    }

    @Override
    public void hitWall(Wall wall) throws InvalidMoveException, WallHitException {
        throw new WallHitException();
    }

    @Override
    public void hitLightTrail(LightTrail lightTrail) {
        // do nothing
    }

    @Override
    public void hitForceField(ForceField forceField) throws InvalidMoveException, ForceFieldHitException {
        throw new ForceFieldHitException();
    }

}
