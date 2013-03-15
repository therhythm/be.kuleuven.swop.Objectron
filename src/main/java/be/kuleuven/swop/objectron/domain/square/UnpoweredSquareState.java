package be.kuleuven.swop.objectron.domain.square;

import be.kuleuven.swop.objectron.domain.Player;

/**
 * @author : Nik Torfs
 *         Date: 13/03/13
 *         Time: 03:01
 */
public class UnpoweredSquareState implements SquareState {
    private static final int NB_TURNS_WITHOUT_POWER = 3;
    private static final int ACTIONS_TO_REDUCE = 1;

    private int remainingTurns = NB_TURNS_WITHOUT_POWER;

    @Override
    public void newTurn(Player player, boolean currentSquare, Transitionable<SquareState> context) {
        if(currentSquare){
            player.reduceRemainingActions(ACTIONS_TO_REDUCE);
        }
        remainingTurns--;
        if(remainingTurns == 0){
            context.transitionState(new PoweredSquareState());
        }
    }

    @Override
    public void stepOn(Player player) {
        if(player.getCurrentSquare().hasActiveItem()){
            player.reduceRemainingActions(ACTIONS_TO_REDUCE);
        }else{
            //player.endTurn(); //TODO ending turn should also change gamestate
        }
    }

    @Override
    public void powerFailure(Transitionable<SquareState> context) {
        remainingTurns = NB_TURNS_WITHOUT_POWER;
    }
}
