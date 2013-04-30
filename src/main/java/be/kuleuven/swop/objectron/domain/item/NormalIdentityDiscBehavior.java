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

    @Override
    public void throwMe(Square sourceSquare, Direction targetDirection, IdentityDisc context, TurnManager turnManager) {
        Square currentSquare = sourceSquare;
        Square neighbor = context.getNextSquare(currentSquare, targetDirection);

        for (int i = 0; i < MAX_RANGE; i++) {
            if (neighbor == null)
                break;
            if (context.playerHit(neighbor, turnManager)) {//todo find a way to hit players .. maybe make player an obstruction?? REMOVE player
                currentSquare = neighbor;
                break;

            } else /*!state.getGrid().isWall(neighbor)*/ { //todo let obstruction handle obstructions remove grid
                currentSquare = neighbor;
                neighbor = context.getNextSquare(currentSquare, targetDirection);
            }
        }
        currentSquare.addItem(context);
    }

    @Override
    public String getName() {
        return "Uncharged Identity Disc";
    }
}
