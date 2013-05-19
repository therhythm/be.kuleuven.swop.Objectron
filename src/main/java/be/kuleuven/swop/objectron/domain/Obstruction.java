package be.kuleuven.swop.objectron.domain;

import be.kuleuven.swop.objectron.domain.exception.ForceFieldHitException;
import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.exception.PlayerHitException;
import be.kuleuven.swop.objectron.domain.exception.WallHitException;
import be.kuleuven.swop.objectron.domain.movement.MovementStrategy;

/**
 * @author : Nik Torfs
 *         Date: 03/05/13
 *         Time: 04:59
 */
public interface Obstruction {
    void hit(MovementStrategy strategy) throws InvalidMoveException, PlayerHitException, WallHitException,
            ForceFieldHitException;
}
