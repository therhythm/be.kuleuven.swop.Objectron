package be.kuleuven.swop.objectron.domain.effect;

import be.kuleuven.swop.objectron.domain.exception.GameOverException;
import be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException;
import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.movement.Movable;
import be.kuleuven.swop.objectron.domain.movement.Movement;

/**
 * @author : Nik Torfs
 *         Date: 24/04/13
 *         Time: 23:14
 */
public interface Effect {

    void accept(EffectVisitor visitor);

    void activate(Movement movement, TurnManager manager);
}
