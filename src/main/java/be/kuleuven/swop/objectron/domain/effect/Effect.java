package be.kuleuven.swop.objectron.domain.effect;

import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.movement.Movable;

/**
 * @author : Nik Torfs
 *         Date: 24/04/13
 *         Time: 23:14
 */
public interface Effect {

    void activate(Movable movable, TurnManager manager);

    void accept(EffectVisitor visitor);
}
