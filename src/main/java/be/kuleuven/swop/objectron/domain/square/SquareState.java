package be.kuleuven.swop.objectron.domain.square;

import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;

/**
 * @author : Nik Torfs
 *         Date: 13/03/13
 *         Time: 02:57
 */
public interface SquareState {
    void newTurn(Turn currentTurn, boolean currentSquare, Square context);

    void stepOn(TurnManager turnManager);

    void powerFailure(Square context);
}
