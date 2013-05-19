package be.kuleuven.swop.objectron.domain.movement.teleport;

import be.kuleuven.swop.objectron.domain.effect.Teleporter;
import be.kuleuven.swop.objectron.domain.exception.*;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.movement.Movable;

import java.util.HashSet;
import java.util.Set;

/**
 * @author : Nik Torfs
 *         Date: 03/05/13
 *         Time: 00:32
 */
public class IdentityDiscTeleportStrategy implements TeleportStrategy {
    private Set<Teleporter> cycle = new HashSet<>();
    private boolean isTeleporting;

    @Override
    public void teleport(Movable movable, Teleporter context, TurnManager manager) throws InvalidMoveException,
            PlayerHitException, WallHitException, ForceFieldHitException, GameOverException,
            NotEnoughActionsException, SquareOccupiedException {

        if (cycle.contains(context) || cycle.contains(context.getDestination())) {
            return;
        }

        cycle.add(context);
        context.teleport(movable, manager);

    }
}
