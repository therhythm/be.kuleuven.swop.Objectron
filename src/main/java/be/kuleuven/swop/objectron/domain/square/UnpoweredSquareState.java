package be.kuleuven.swop.objectron.domain.square;

import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.item.Effect;
import be.kuleuven.swop.objectron.domain.item.ReduceAvailableActionsEffect;

/**
 * @author : Nik Torfs
 *         Date: 13/03/13
 *         Time: 03:01
 */
public class UnpoweredSquareState implements SquareState {
    private static final int NB_TURNS_WITHOUT_POWER = 3;

    private int remainingTurns = NB_TURNS_WITHOUT_POWER;

    @Override
    public void newTurn(Player player, Square sq) {
        remainingTurns--;
        if(remainingTurns == 0){
            sq.transitionState(new PoweredSquareState());
        }else if(player.getCurrentSquare().equals(sq)){
            player.addEffect(new ReduceAvailableActionsEffect(1));
        }
    }

    @Override
    public void stepOn(Player player, Square sq) {
        if(sq.hasActiveItem()){
            player.addEffect(new ReduceAvailableActionsEffect(1));
        }else{
            player.endTurn(); //TODO ending turn should also change gamestate
        }
    }

    @Override
    public void powerFailure(Square context) {
        remainingTurns = NB_TURNS_WITHOUT_POWER;
    }
}
