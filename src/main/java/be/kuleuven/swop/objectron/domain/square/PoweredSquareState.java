package be.kuleuven.swop.objectron.domain.square;

import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.game.Turn;

/**
 * @author : Nik Torfs
 *         Date: 13/03/13
 *         Time: 03:00
 */
public class PoweredSquareState implements SquareState {

    @Override
    public void newTurn(Turn currentTurn, boolean currentSquare, Transitionable<SquareState> context) {
        // do nothing
    }

    @Override
    public void stepOn(Player player) {
        // do nothing
    }

    @Override
    public void powerFailure(Transitionable<SquareState> context) {
        context.transitionState(new UnpoweredSquareState());
    }
}
