package be.kuleuven.swop.objectron.domain.square;

import be.kuleuven.swop.objectron.domain.Player;

/**
 * @author : Nik Torfs
 *         Date: 13/03/13
 *         Time: 03:00
 */
public class PoweredSquareState implements SquareState {

    @Override
    public void newTurn(Player player, Square context) {
        // do nothing
    }

    @Override
    public void stepOn(Player player) {
        // do nothing
    }

    @Override
    public void powerFailure(Square context) {
        context.transitionState(new UnpoweredSquareState());
    }
}
