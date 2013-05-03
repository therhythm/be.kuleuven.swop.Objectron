package be.kuleuven.swop.objectron.domain.square;

import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;

/**
 * @author : Nik Torfs
 *         Date: 13/03/13
 *         Time: 03:01
 */
public class UnpoweredSquareState implements SquareState {

    private static final int ACTIONS_TO_REDUCE = 1;


    private int remainingTurns = 0;
    private int remainingActions = 0;

    @Override
    public void newTurn(Turn currentTurn, boolean currentSquare, Square context) {
        if (currentSquare) {
            currentTurn.reduceRemainingActions(ACTIONS_TO_REDUCE);
        }
        remainingTurns--;
        checkTransition(context);
    }

    @Override
    public void stepOn(GameState gameState) {
        if (gameState.getCurrentTurn().getCurrentPlayer().getCurrentSquare().hasActiveItem()) {
            gameState.getCurrentTurn().reduceRemainingActions(ACTIONS_TO_REDUCE);
        } else {
            gameState.endAction();
            gameState.endTurn();
        }
    }

    @Override
    public void powerFailure(Square context, int turns, int actions) {
        remainingTurns += turns;
        remainingActions += actions;
    }

    @Override
    public void endAction(Square context){
        remainingActions --;
        checkTransition(context);
    }

    private void checkTransition(Square context){
        if (remainingTurns <= 0 && remainingActions <= 0) {
            context.transitionState(new PoweredSquareState());
            context.notifyPowered();
        }
    }
}
