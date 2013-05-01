package be.kuleuven.swop.objectron.domain.movement;

import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.obstruction.LightTrail;
import be.kuleuven.swop.objectron.domain.obstruction.Player;
import be.kuleuven.swop.objectron.domain.obstruction.Wall;

/**
 * @author : Nik Torfs
 *         Date: 30/04/13
 *         Time: 23:22
 */
public class Movable {
    ObstructionStrategy obstructionStrategy;

    public Movable(ObstructionStrategy obstructionStrategy) {
        this.obstructionStrategy = obstructionStrategy;
    }

    public void hitWall(Wall wall, TurnManager manager) throws InvalidMoveException {
        obstructionStrategy.hitWall(wall, manager);
    }

    public void hitPlayer(Player player, TurnManager manager) throws InvalidMoveException {
        obstructionStrategy.hitPlayer(player, manager);
    }

    public void hitLightTrail(LightTrail lightTrail, TurnManager manager) throws InvalidMoveException {
        obstructionStrategy.hitLightTrail(lightTrail, manager);
    }
}
