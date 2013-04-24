package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.square.Square;

import java.util.List;
import java.util.ListResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: Peter
 * Date: 11/04/13
 * Time: 14:49
 * To change this template use File | Settings | File Templates.
 */
public interface IdentityDiscBehavior {
    //todo remove method
    public void useItem(UseItemRequest useItemRequest,IdentityDisc identityDisc) throws SquareOccupiedException;
    public void throwMe(Square sourceSquare, Direction targetDirection, IdentityDisc context, GameState state); //TODO remove Gamestate
    public String getName();
}
