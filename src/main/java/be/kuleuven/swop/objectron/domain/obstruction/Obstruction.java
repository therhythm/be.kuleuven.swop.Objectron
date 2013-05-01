package be.kuleuven.swop.objectron.domain.obstruction;

import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.movement.Movable;
import be.kuleuven.swop.objectron.domain.square.Square;

/**
 * @author : Nik Torfs
 *         Date: 30/04/13
 *         Time: 23:20
 */
public interface Obstruction {
    void hit(Movable movable, TurnManager manager) throws InvalidMoveException;
}
