package be.kuleuven.swop.objectron.domain.movement.teleport;

import be.kuleuven.swop.objectron.domain.item.effect.Teleporter;
import be.kuleuven.swop.objectron.domain.movement.Movable;

/**
 * @author : Nik Torfs
 *         Date: 03/05/13
 *         Time: 00:25
 */
public interface TeleportStrategy {
    void teleport(Movable movable, Teleporter context);
}
