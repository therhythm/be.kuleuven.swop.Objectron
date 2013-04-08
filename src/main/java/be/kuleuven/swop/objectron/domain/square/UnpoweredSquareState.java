package be.kuleuven.swop.objectron.domain.square;

import be.kuleuven.swop.objectron.domain.Settings;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;

/**
 * @author : Nik Torfs
 *         Date: 13/03/13
 *         Time: 03:01
 */
public class UnpoweredSquareState implements SquareState {

    private int remainingTurns = Settings.SQUARE_TURNS_WITHOUT_POWER;

    @Override
    public void newTurn(Turn currentTurn, boolean currentSquare, Transitionable<SquareState> context) {
        if(currentSquare){
            currentTurn.reduceRemainingActions(Settings.SQUARE_ACTIONS_TO_REDUCE);
        }
        remainingTurns--;
        if(remainingTurns == 0){
            context.transitionState(new PoweredSquareState());
        }
    }

    @Override
    public void stepOn(GameState gameState) {
        if(gameState.getCurrentTurn().getCurrentPlayer().getCurrentSquare().hasActiveItem()){
           gameState.getCurrentTurn().reduceRemainingActions(Settings.SQUARE_ACTIONS_TO_REDUCE);
        }else{
           gameState.endTurn();
        }
    }

    @Override
    public void powerFailure(Transitionable<SquareState> context) {
        remainingTurns = Settings.SQUARE_TURNS_WITHOUT_POWER;
    }
}
