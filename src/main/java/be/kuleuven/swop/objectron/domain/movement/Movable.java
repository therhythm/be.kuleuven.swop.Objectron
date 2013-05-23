package be.kuleuven.swop.objectron.domain.movement;

import be.kuleuven.swop.objectron.domain.exception.*;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
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

    void enter(Square square, TurnManager manager) throws InvalidMoveException, PlayerHitException, WallHitException,
            ForceFieldHitException, GameOverException, NotEnoughActionsException, SquareOccupiedException;
    void effectActivation();
}
