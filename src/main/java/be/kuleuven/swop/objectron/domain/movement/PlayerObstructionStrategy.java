package be.kuleuven.swop.objectron.domain.movement;

import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.obstruction.LightTrail;
import be.kuleuven.swop.objectron.domain.obstruction.Player;
import be.kuleuven.swop.objectron.domain.obstruction.Wall;

/**
 * @author : Nik Torfs
 *         Date: 30/04/13
 *         Time: 23:29
 */
public class PlayerObstructionStrategy implements ObstructionStrategy {
    @Override
    public void hitWall(Wall wall, TurnManager turnManager) throws InvalidMoveException {
        throw new InvalidMoveException();
    }

    @Override
    public void hitPlayer(Player player, TurnManager turnManager) throws InvalidMoveException {
        throw new InvalidMoveException();
    }

    @Override
    public void hitLightTrail(LightTrail lightTrail, TurnManager turnManager) throws InvalidMoveException {
        throw new InvalidMoveException();
    }
}
