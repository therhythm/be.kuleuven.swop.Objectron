package be.kuleuven.swop.objectron.domain.effect;

import be.kuleuven.swop.objectron.domain.exception.GameOverException;
import be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException;
import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.movement.Movable;

/**
 * @author : Nik Torfs
 *         Date: 24/04/13
 *         Time: 23:14
 */
public interface Effect {

    /**
     * Activate the effect
     *
     * @param   movable The movable object that activates the effect
     * @param   manager The Turnmanager of the game
     * @throws  GameOverException
     *          If movable is Player and wins on this effect
     * @throws  NotEnoughActionsException
     *          The player has no more available actions
     *          | manager.getCurrentTurn().getActionsRemaining() == 0
     * @throws  SquareOccupiedException
     *          The square is occupied where the effect is active
     * @post    The effect has been executed
     */
    void activate(Movable movable, TurnManager manager) throws GameOverException, NotEnoughActionsException,
            SquareOccupiedException;

    void accept(EffectVisitor visitor);

}
