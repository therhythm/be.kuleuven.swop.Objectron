package be.kuleuven.swop.objectron.domain.square;

import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;

/**
 * @author : Nik Torfs
 *         Date: 13/03/13
 *         Time: 02:57
 */
public interface SquareState {
    void newTurn(Turn currentTurn, boolean currentSquare, Transitionable<SquareState> context);

    void stepOn(GameState gameState);

    void powerFailure(Transitionable<SquareState> context);
}
