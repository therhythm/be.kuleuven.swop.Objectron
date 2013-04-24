package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.effect.ActivateRequest;
import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.square.Square;

/**
 * @author : Peter Bosmans
 *         Date: 27/02/13
 *         Time: 20:56
 */
public class LightMine implements Item {
    private static final int NB_ACTIONS_BLINDED = 3;

    private static final String name = "Light Mine";

    @Override
    public String getName() {
        return name;
    }

    public void activate(ActivateRequest activateRequest) {
        activateRequest.getCurrentTurn().reduceRemainingActions(NB_ACTIONS_BLINDED);
    }

    @Override
    public boolean pickupAble() {
        return true;
    }

    @Override
    public void place(Square targetSquare) throws SquareOccupiedException {
        targetSquare.setActiveItem(this);
    }

    @Override
    public void throwMe(Square sourceSquare, Direction targetDirection, GameState state) {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        String result = "";
        result += "name: " + this.getName();

        return result;
    }
}
