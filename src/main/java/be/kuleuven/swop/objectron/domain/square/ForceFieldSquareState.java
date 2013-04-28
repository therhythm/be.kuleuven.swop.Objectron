package be.kuleuven.swop.objectron.domain.square;

import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;

/**
 * @author : Nik Torfs
 *         Date: 13/03/13
 *         Time: 03:01
 */
public class ForceFieldSquareState implements SquareState {
    private SquareStatePower squareStatePower;

    public ForceFieldSquareState() {

    }

    public ForceFieldSquareState(SquareStatePower squareStatePower) {
        this.squareStatePower = squareStatePower;
    }

    @Override
    public void newTurn(Turn currentTurn, boolean currentSquare, Square context) {
        squareStatePower.newTurn(currentTurn,currentSquare,context);
    }

    @Override
    public void stepOn(GameState gameState) throws InvalidMoveException {
        throw new InvalidMoveException();
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
