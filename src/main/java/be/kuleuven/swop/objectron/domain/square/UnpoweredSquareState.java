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
    private static final int NB_TURNS_WITHOUT_POWER = 2; //3-1 because one turn is the one we're in at this moment 

    private int remainingTurns = NB_TURNS_WITHOUT_POWER;
        
    public UnpoweredSquareState(){
                
    }
    
    @Override
    public void newTurn(Player player, Square sq) {
        if(player.getCurrentSquare().equals(sq)){
            player.addEffect(new ReduceAvailableActionsEffect(1));
        }

        remainingTurns--;
        if(remainingTurns == 0){
            sq.transitionState(new PoweredSquareState());
        }
    }

    @Override
    public void stepOn(Player player, Square sq) {
        if(sq.hasActiveItem()){
            player.addEffect(new ReduceAvailableActionsEffect(1));
        }else{
            player.endTurn(); //TODO
        }
    }

    @Override
    public void powerFailure() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
