package be.kuleuven.swop.objectron.domain.movement;

import be.kuleuven.swop.objectron.domain.LightTrail;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.Wall;
import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.item.IdentityDisc;
import be.kuleuven.swop.objectron.domain.item.IdentityDiscBehavior;
import be.kuleuven.swop.objectron.domain.item.forceField.ForceField;

/**
 * @author : Nik Torfs
 *         Date: 03/05/13
 *         Time: 00:30
 */
//todo let Movable delegate this
public class IdentityDiscMovementStrategy implements MovementStrategy{
    private TurnManager turnManager;
    private IdentityDiscBehavior identityDiscBehavior;
    private IdentityDisc identityDisc;

    @Override
    public void powerFailure(boolean hasLightMine) {
        identityDiscBehavior.moved(); //identityDiscBehavior range gets reduced by one
    }

    @Override
    public void hitPlayer(Player player) {
        if(player.equals(turnManager.getCurrentTurn().getCurrentPlayer())){
            turnManager.endTurn();
        }
        turnManager.getCurrentTurn().extraTurn();
        identityDisc.playerHit();    //todo fix with exception?
    }

    @Override
    public void hitWall(Wall wall) throws InvalidMoveException {
        throw new InvalidMoveException();
    }

    @Override
    public void hitLightTrail(LightTrail lightTrail) {
        // do nothing
    }

    @Override
    public void hitForceField(ForceField forceField) throws InvalidMoveException {
        //todo destroy identitydisc
    }

}
