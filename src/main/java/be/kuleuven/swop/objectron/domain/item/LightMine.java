package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.effect.Effect;
import be.kuleuven.swop.objectron.domain.effect.EffectVisitor;
import be.kuleuven.swop.objectron.domain.movement.Movable;
import be.kuleuven.swop.objectron.domain.square.Square;

/**
 * @author : Peter Bosmans
 *         Date: 27/02/13
 *         Time: 20:56
 */
public class LightMine implements Item, Effect {
    private static final int MAX_IN_BAG = Integer.MAX_VALUE;
    private static final int NB_ACTIONS_BLINDED = 3;

    private static final String name = "Light Mine";
    private boolean isActive = false;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void activate(Movable movable, TurnManager manager) {
        if(isActive){
            manager.getCurrentTurn().addPenalty(NB_ACTIONS_BLINDED);
            isActive = false;
        }
    }

    @Override
    public void accept(EffectVisitor visitor) {
        visitor.visitLightMine();
    }

    @Override
    public void place(Square targetSquare){
        targetSquare.addEffect(this);
        isActive = true;
    }

    @Override
    public void throwMe(Square sourceSquare, Direction targetDirection, TurnManager turnManager) {
        throw new UnsupportedOperationException();
    }

    public boolean isActive(){
        return isActive;
    }

    @Override
    public void pickedUp() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getMaxInBag() {
        return MAX_IN_BAG;
    }


    public String toString() {
        String result = "";
        result += "name: " + this.getName();

        return result;
    }
}
