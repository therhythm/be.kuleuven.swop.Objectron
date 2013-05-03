package be.kuleuven.swop.objectron.domain.movement.teleport;

import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.item.effect.Teleporter;
import be.kuleuven.swop.objectron.domain.movement.Movable;

import java.util.HashSet;
import java.util.Set;

/**
 * @author : Nik Torfs
 *         Date: 03/05/13
 *         Time: 00:32
 */
public class IdentityDiscTeleportStrategy implements TeleportStrategy{
    Set<Teleporter> cycle = new HashSet<>();

    @Override
    public void teleport(Movable movable, Teleporter context) throws InvalidMoveException {
        // if cycle already contains teleporter don't teleport
        if(cycle.contains(context))
            return;

        cycle.add(context);
        context.teleport(movable);
    }
}
