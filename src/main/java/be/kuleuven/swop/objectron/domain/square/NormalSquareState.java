package be.kuleuven.swop.objectron.domain.square;

import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;

/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 28/04/13
 * Time: 15:12
 * To change this template use File | Settings | File Templates.
 */
public class NormalSquareState implements SquareState {
    private SquareStatePower squareStatePower;

    public NormalSquareState(SquareStatePower powerSquareState){
        this.squareStatePower = powerSquareState;
    }
    @Override
    public void newTurn(Turn currentTurn, boolean currentSquare, Square context) {
        squareStatePower.newTurn(currentTurn, currentSquare, context);
    }

    @Override
    public void stepOn(GameState gameState) throws InvalidMoveException {
        squareStatePower.stepOn(gameState);
    }

    @Override
    public void powerFailure(Square context) {
        squareStatePower.powerFailure(context);
    }

    @Override
    public void setSquareStatePower(SquareStatePower squareStatePower) {
        this.squareStatePower = squareStatePower;
    }

    @Override
    public SquareStatePower getSquareStatePower() {
        return this.squareStatePower;
    }
}
