package be.kuleuven.swop.objectron.domain.square;

import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.movement.MovementStrategy;

/**
 * @author : Nik Torfs
 *         Date: 13/03/13
 *         Time: 03:00
 */
public class PoweredState implements PowerState {

    private Square context;

    public PoweredState(Square context) {
        this.context = context;
    }

    @Override
    public void newTurn(Turn currentTurn) {
        // do nothing
    }

    @Override
    public void stepOn(MovementStrategy movementStrategy) {
        // do nothing
    }

    @Override
    public void powerFailure() {
        context.transitionState(new UnpoweredState(context));
    }
}
