package be.kuleuven.swop.objectron.domain.square;

import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.item.Effect;

/**
 * @author : Nik Torfs
 *         Date: 13/03/13
 *         Time: 03:01
 */
public class UnpoweredSquare implements SquareState {
    private static final int NB_TURNS_WITHOUT_POWER = 2; //3-1 because one turn is the one we're in at this moment 
    
    private Effect effect;
    private int remainingTurns = NB_TURNS_WITHOUT_POWER;
        
    public UnpoweredSquare(){
                
    }
    
    @Override
    public void newTurn() {
        remainingTurns--;
        if(remainingTurns == 0){
            //transitionState
        }
    }

    @Override
    public void stepOn(Player player) {
        //apply effect
        
    }
}
