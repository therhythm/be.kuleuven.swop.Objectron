package be.kuleuven.swop.objectron.domain.movement;

import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.movement.teleport.TeleportStrategy;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.exception.ForceFieldHitException;
import be.kuleuven.swop.objectron.domain.exception.PlayerHitException;
import be.kuleuven.swop.objectron.domain.exception.WallHitException;

/**
 * @author : Nik Torfs
 *         Date: 03/05/13
 *         Time: 00:24
 */
public interface Movable {
    TeleportStrategy getTeleportStrategy();
    MovementStrategy getMovementStrategy();

    void enter(Square square, TurnManager manager) throws InvalidMoveException, PlayerHitException, WallHitException, ForceFieldHitException;
}
