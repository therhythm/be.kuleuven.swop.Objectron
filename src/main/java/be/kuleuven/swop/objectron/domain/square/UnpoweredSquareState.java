package be.kuleuven.swop.objectron.domain.square;

import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;

/**
 * @author : Nik Torfs
 *         Date: 13/03/13
 *         Time: 03:01
 */
public class UnpoweredSquareState implements SquareState {
    public static final int TURNS_WITHOUT_POWER = 3;
    private static final int ACTIONS_TO_REDUCE = 1;


    private int remainingTurns = TURNS_WITHOUT_POWER;

    @Override
    public void newTurn(Turn currentTurn, boolean currentSquare, Square context) {
        if (currentSquare) {
            currentTurn.reduceRemainingActions(ACTIONS_TO_REDUCE);
        }
        remainingTurns--;
        if (remainingTurns == 0) {
            context.transitionState(new PoweredSquareState());
            context.notifyPowered();
        }
    }

    @Override
    public void stepOn(TurnManager turnManager) {
        if (turnManager.getCurrentTurn().getCurrentPlayer().getCurrentSquare().hasActiveItem()) {  //todo this should be done another way
            turnManager.getCurrentTurn().reduceRemainingActions(ACTIONS_TO_REDUCE);
        } else {
            turnManager.endTurn();
        }
    }

    @Override
    public void powerFailure(Square context) {
        remainingTurns = TURNS_WITHOUT_POWER;
    }
}
