package be.kuleuven.swop.objectron.domain;

import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.movement.Movement;

/**
 * An interface of Obstructions.
 * @author : Nik Torfs
 *         Date: 03/05/13
 *         Time: 04:59
 */
public interface Obstruction {

    /**
     * Hit the obstruction when a Movable steps on the obstructed square.
     * @param movement
     *        The movement that hits the obstruction
    **/
    void hit(Movement movement) throws InvalidMoveException;
}
