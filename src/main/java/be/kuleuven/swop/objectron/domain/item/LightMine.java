package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.item.effect.Effect;
import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.square.Square;

/**
 * @author : Peter Bosmans
 *         Date: 27/02/13
 *         Time: 20:56
 */
public class LightMine implements Item, Effect {
    private static final int NB_ACTIONS_BLINDED = 3;

    private static final String name = "Light Mine";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void activate(Turn currentTurn) {
        currentTurn.reduceRemainingActions(NB_ACTIONS_BLINDED);
    }

    @Override
    public void place(Square targetSquare) throws SquareOccupiedException {
        targetSquare.setActiveItem(this);
    }

    @Override
    public void throwMe(Square sourceSquare, Direction targetDirection, GameState state) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addToSquare(Square targetSquare) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String toString() {
        String result = "";
        result += "name: " + this.getName();

        return result;
    }
}
