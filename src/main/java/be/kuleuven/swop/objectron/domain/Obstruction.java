package be.kuleuven.swop.objectron.domain;

import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.movement.Movement;

/**
 * @author : Nik Torfs
 *         Date: 03/05/13
 *         Time: 04:59
 */
public interface Obstruction {
    void hit(Movement movement) throws InvalidMoveException;
}
