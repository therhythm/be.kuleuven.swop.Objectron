package be.kuleuven.swop.objectron.domain.movement;

import be.kuleuven.swop.objectron.domain.LightTrail;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.Wall;
import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.item.forceField.ForceField;
import be.kuleuven.swop.objectron.domain.exception.ForceFieldHitException;
import be.kuleuven.swop.objectron.domain.exception.PlayerHitException;
import be.kuleuven.swop.objectron.domain.exception.WallHitException;

/**
 * @author : Nik Torfs
 *         Date: 03/05/13
 *         Time: 00:30
 */
public interface MovementStrategy {
    void powerFailure(boolean hasLightMine);

    //todo move to somewhere else
    void hitPlayer(Player player) throws InvalidMoveException, PlayerHitException;

    void hitWall(Wall wall) throws InvalidMoveException, WallHitException;

    void hitLightTrail(LightTrail lightTrail) throws InvalidMoveException;

    void hitForceField(ForceField forceField) throws InvalidMoveException, ForceFieldHitException;
}
