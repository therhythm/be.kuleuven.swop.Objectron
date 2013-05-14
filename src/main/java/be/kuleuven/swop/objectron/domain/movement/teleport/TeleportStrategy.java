package be.kuleuven.swop.objectron.domain.movement.teleport;

import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.effect.Teleporter;
import be.kuleuven.swop.objectron.domain.movement.Movable;
import be.kuleuven.swop.objectron.domain.exception.ForceFieldHitException;
import be.kuleuven.swop.objectron.domain.exception.PlayerHitException;
import be.kuleuven.swop.objectron.domain.exception.WallHitException;

/**
 * @author : Nik Torfs
 *         Date: 03/05/13
 *         Time: 00:25
 */
public interface TeleportStrategy {
    void teleport(Movable movable, Teleporter context, TurnManager manager) throws InvalidMoveException, PlayerHitException, WallHitException, ForceFieldHitException;
}
