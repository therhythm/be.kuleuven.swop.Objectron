package be.kuleuven.swop.objectron.domain.item.forceField;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.item.effect.Effect;
import be.kuleuven.swop.objectron.domain.item.effect.EffectVisitor;
import be.kuleuven.swop.objectron.domain.square.Square;

/**
 * Created with IntelliJ IDEA.
 * User: Peter
 * Date: 28/04/13
 * Time: 9:50
 * To change this template use File | Settings | File Templates.
 */
//todo is this an effect or obstruction>?
public class ForceField implements Item, Effect {
    private final String name = "Force Field";
    private ForceFieldArea forcefieldArea;

    public ForceField(ForceFieldArea forcefieldArea) {
        this.forcefieldArea = forcefieldArea;
    }

    @Override
    public void activate(Turn currentTurn) {
        // forcefieldArea.placeForceField(this,currentTurn);
    }

    @Override
    public void accept(EffectVisitor visitor) {
        visitor.visitForceField();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void place(Square targetSquare){
        forcefieldArea.placeForceField(this, targetSquare);
    }

    @Override
    public void throwMe(Square sourceSquare, Direction targetDirection, TurnManager turnManager) {
        throw new UnsupportedOperationException();
    }


    @Override
    public void addToSquare(Square targetSquare) {
        if (targetSquare == null)
            forcefieldArea.pickUpForceField(this, targetSquare);
        else
            forcefieldArea.placeForceField(this, targetSquare);
    }
}
