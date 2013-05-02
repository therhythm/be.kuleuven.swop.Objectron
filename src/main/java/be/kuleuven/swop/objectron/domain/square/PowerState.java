package be.kuleuven.swop.objectron.domain.square;

import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;

/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 28/04/13
 * Time: 15:22
 * To change this template use File | Settings | File Templates.
 */
public interface PowerState {
    void newTurn(Turn currentTurn);

    void stepOn(TurnManager turnManager);

    void powerFailure();
}
