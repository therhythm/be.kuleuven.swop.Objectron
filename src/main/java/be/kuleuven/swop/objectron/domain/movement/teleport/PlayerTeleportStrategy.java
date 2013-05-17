package be.kuleuven.swop.objectron.domain.movement.teleport;

import be.kuleuven.swop.objectron.domain.exception.*;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.item.effect.Teleporter;
import be.kuleuven.swop.objectron.domain.movement.Movable;

/**
 * @author : Nik Torfs
 *         Date: 03/05/13
 *         Time: 00:31
 */
//todo maybe put this in movable?
public class PlayerTeleportStrategy implements TeleportStrategy{
    boolean isTeleporting = false;

    @Override
    public void teleport(Movable movable, Teleporter context, TurnManager manager) throws InvalidMoveException, PlayerHitException, WallHitException, ForceFieldHitException, GameOverException, NotEnoughActionsException, SquareOccupiedException {
        if(!isTeleporting){
            isTeleporting = true;
            context.teleport(movable, manager);
        }
    }
}
