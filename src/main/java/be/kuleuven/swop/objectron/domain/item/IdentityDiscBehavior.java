package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.square.Square;

/**
 * Created with IntelliJ IDEA.
 * User: Peter
 * Date: 11/04/13
 * Time: 14:49
 * To change this template use File | Settings | File Templates.
 */
public interface IdentityDiscBehavior {
    public void throwMe(Square sourceSquare, Direction targetDirection, IdentityDisc context, GameState state); //TODO remove Gamestate

    public String getName();
}
