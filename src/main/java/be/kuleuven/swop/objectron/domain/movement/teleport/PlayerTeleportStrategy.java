package be.kuleuven.swop.objectron.domain.movement.teleport;

import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.item.effect.Teleporter;
import be.kuleuven.swop.objectron.domain.movement.Movable;

/**
 * @author : Nik Torfs
 *         Date: 03/05/13
 *         Time: 00:31
 */
public class PlayerTeleportStrategy implements TeleportStrategy{
    boolean isTeleporting = false;

    @Override
    public void teleport(Movable movable, Teleporter context) throws InvalidMoveException {
        if(!isTeleporting){
            context.teleport(movable);
            isTeleporting = true;
        }
    }
}
