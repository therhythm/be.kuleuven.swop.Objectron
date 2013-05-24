package be.kuleuven.swop.objectron.domain;

import be.kuleuven.swop.objectron.domain.exception.ForceFieldHitException;
import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.exception.PlayerHitException;
import be.kuleuven.swop.objectron.domain.exception.WallHitException;
import be.kuleuven.swop.objectron.domain.movement.MovementStrategy;

/**
 * An interface of Obstructions.
 * @author : Nik Torfs
 *         Date: 03/05/13
 *         Time: 04:59
 */
public interface Obstruction {

    /**
     * Hit the obstruction when a Movable steps on the obstructed square.
     * @param strategy
     *        The strategy to hit the obstruction with.
     * @throws InvalidMoveException
     *         This is an invalid move.
     * @throws PlayerHitException
     *         A player is hit by an identity disc.
     * @throws WallHitException
     *         A wall is hit by an identity disc.
     * @throws ForceFieldHitException
     *         A force field is hit by an identity disc.
     */
    void hit(MovementStrategy strategy) throws InvalidMoveException, PlayerHitException, WallHitException,
            ForceFieldHitException;
}
