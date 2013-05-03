package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.square.Square;

/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 11/04/13
 * Time: 14:37
 * To change this template use File | Settings | File Templates.
 */
public class NormalIdentityDiscBehavior implements IdentityDiscBehavior {
    private static final int MAX_RANGE = 4;
    private int remainingRange;

    public NormalIdentityDiscBehavior() {
        this.remainingRange = MAX_RANGE;
    }

    @Override
    public int getRemainingRange() {
        return remainingRange;
    }

    @Override
    public void moved() {
        remainingRange--;
    }

    @Override
    public String getName() {
        return "Uncharged Identity Disc";
    }

    @Override
    public void reset() {
        remainingRange = MAX_RANGE;
    }
}
