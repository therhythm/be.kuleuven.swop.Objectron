package be.kuleuven.swop.objectron.domain.square;

import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.Settings;

/**
 * @author : Nik Torfs
 *         Date: 13/03/13
 *         Time: 03:01
 */
public class UnpoweredSquareState implements SquareState {

    private int remainingTurns = Settings.SQUARE_TURNS_WITHOUT_POWER;

    @Override
    public void newTurn(Player player, boolean currentSquare, Transitionable<SquareState> context) {
        if(currentSquare){
            player.reduceRemainingActions(Settings.SQUARE_ACTIONS_TO_REDUCE);
        }
        remainingTurns--;
        if(remainingTurns == 0){
            context.transitionState(new PoweredSquareState());
        }
    }

    @Override
    public void stepOn(Player player) {
        if(player.getCurrentSquare().hasActiveItem()){
            player.reduceRemainingActions(Settings.SQUARE_ACTIONS_TO_REDUCE);
        }else{
            //player.endTurn(); //TODO ending turn should also change gamestate
        }
    }

    @Override
    public void powerFailure(Transitionable<SquareState> context) {
        remainingTurns = Settings.SQUARE_TURNS_WITHOUT_POWER;
    }
}
