package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.square.Square;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 11/04/13
 * Time: 14:37
 * To change this template use File | Settings | File Templates.
 */
public class NormalIdentityDiscBehavior implements IdentityDiscBehavior {
    private final int maxRange = 4;

    @Override
    public void useItem(UseItemRequest useItemRequest, IdentityDisc identityDisc) throws SquareOccupiedException {
        Square currentSquare = useItemRequest.getSquare();

        Square neighbor = identityDisc.getNextSquare(currentSquare, useItemRequest.getDirection());

        for (int i = 0; i < maxRange; i++) {
            System.out.println(neighbor);
            if (neighbor == null)
                break;
            if (identityDisc.playerHit(neighbor, useItemRequest.getGameState())) {
                currentSquare = neighbor;
                break;

            } else if (!useItemRequest.getGrid().isWall(neighbor)) {
                currentSquare = neighbor;
                neighbor = identityDisc.getNextSquare(currentSquare, useItemRequest.getDirection());
            } else
                break;
        }
        currentSquare.addItem(identityDisc);
    }

    @Override
    public void throwMe(Square sourceSquare, Direction targetDirection, IdentityDisc context, GameState state) {
        Square currentSquare = sourceSquare;
        Square neighbor = context.getNextSquare(currentSquare, targetDirection);

        for (int i = 0; i < maxRange; i++) {
            if (neighbor == null)
                break;
            if (context.playerHit(neighbor, state)) {//todo find a way to hit players .. maybe make player an obstruction?? REMOVE player
                currentSquare = neighbor;
                break;

            } else if (!state.getGrid().isWall(neighbor)) { //todo let obstruction handle obstructions remove grid
                currentSquare = neighbor;
                neighbor = context.getNextSquare(currentSquare, targetDirection);
            } else
                break;
        }
        currentSquare.addItem(context);
    }

    @Override
    public String getName() {
        return "Uncharged Identity Disc";
    }
}
