package be.kuleuven.swop.objectron.domain.square;

import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;

/**
 * @author : Nik Torfs
 *         Date: 13/03/13
 *         Time: 03:00
 */
public class PoweredSquareState implements SquareState {

    @Override
    public void newTurn(Turn currentTurn, boolean currentSquare, Square context) {
        // do nothing
    }

    @Override
    public void stepOn(TurnManager turnManager) {
        // do nothing
    }

    @Override
    public void powerFailure(Square context) {
        context.transitionState(new UnpoweredSquareState());
    }
}
