package be.kuleuven.swop.objectron.domain.movement;

import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.movement.teleport.TeleportStrategy;
import be.kuleuven.swop.objectron.domain.square.Square;

/**
 * @author : Nik Torfs
 *         Date: 03/05/13
 *         Time: 00:24
 */
public interface Movable {
    TeleportStrategy getTeleportStrategy();
    MovementStrategy getMovementStrategy();

    void enter(Square square) throws InvalidMoveException;
}
