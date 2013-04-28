package be.kuleuven.swop.objectron.domain.square;

import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;

/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 28/04/13
 * Time: 15:22
 * To change this template use File | Settings | File Templates.
 */
public interface SquareStatePower {
    void newTurn(Turn currentTurn, boolean currentSquare, Square context);

    void stepOn(GameState gameState) throws InvalidMoveException;

    void powerFailure(Square context);
}
