package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.square.Square;

/**
 * Created with IntelliJ IDEA.
 * User: Peter
 * Date: 11/04/13
 * Time: 14:37
 * To change this template use File | Settings | File Templates.
 */
public class ChargedIdentityDiscBehavior implements IdentityDiscBehavior {
    @Override
    public int getRemainingRange() {
        return 1;
    }

    @Override
    public void moved() {
        // do nothing the range doesn't get reduced here
    }

    @Override
    public String getName() {
        return "Charged Identity Disc";
    }

    @Override
    public void reset() {
        // do nothing
    }
}
