package be.kuleuven.swop.objectron.domain.movement;

import be.kuleuven.swop.objectron.domain.LightTrail;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.Wall;
import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.item.forceField.ForceField;
import be.kuleuven.swop.objectron.domain.square.UnpoweredState;

/**
 * @author : Nik Torfs
 *         Date: 03/05/13
 *         Time: 00:30
 */
public class PlayerMovementStrategy implements MovementStrategy {
    public static final int POWERFAILURE_ACTION_REDUCTION = 1;
    TurnManager turnManager;

    public PlayerMovementStrategy(TurnManager turnManager) {
        this.turnManager = turnManager;
    }

    @Override
    public void powerFailure(boolean hasLightMine) {
        if(hasLightMine){
            turnManager.getCurrentTurn().reduceRemainingActions(POWERFAILURE_ACTION_REDUCTION);
        } else{
            turnManager.endTurn();
        }
    }

    @Override
    public void hitPlayer(Player player) throws InvalidMoveException {
        if(!player.equals(turnManager.getCurrentTurn().getCurrentPlayer())){
            throw new InvalidMoveException();
        }
    }

    @Override
    public void hitWall(Wall wall) throws InvalidMoveException {
        throw new InvalidMoveException();
    }

    @Override
    public void hitLightTrail(LightTrail lightTrail) throws InvalidMoveException {
        throw new InvalidMoveException();
    }

    @Override
    public void hitForceField(ForceField forceField) throws InvalidMoveException {
        throw new InvalidMoveException();
    }
}
