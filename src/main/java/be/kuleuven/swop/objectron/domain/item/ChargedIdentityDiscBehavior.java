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
 * User: Peter
 * Date: 11/04/13
 * Time: 14:37
 * To change this template use File | Settings | File Templates.
 */
public class ChargedIdentityDiscBehavior implements IdentityDiscBehavior {


    @Override
    public void useItem(UseItemRequest useItemRequest, IdentityDisc identityDisc) throws SquareOccupiedException {
        Square currentSquare = useItemRequest.getSquare();
        Square neighbor = identityDisc.getNextSquare(currentSquare,useItemRequest.getDirection());
        while (neighbor != null && (!useItemRequest.getGrid().isWall(neighbor))) {
            currentSquare = neighbor;

            if (identityDisc.playerHit(neighbor, useItemRequest.getGameState()))
                break;

            neighbor = identityDisc.getNextSquare(neighbor,useItemRequest.getDirection());


        }
        System.out.println("currentSquare = " + currentSquare);
        currentSquare.addItem(identityDisc);
    }

    @Override
    public void throwMe(Square sourceSquare, Direction targetDirection, IdentityDisc context, GameState state) {
        Square currentSquare = sourceSquare;
        Square neighbour = context.getNextSquare(sourceSquare, targetDirection);//todo check if needed
        while(neighbour != null && (!state.getGrid().isWall(neighbour))){//todo no isWall check. obstruction should handle this
            currentSquare = neighbour;
            if(context.playerHit(neighbour, state)){//todo other way of checking player hits
                break;
            }

            neighbour = context.getNextSquare(neighbour, targetDirection);
        }
        currentSquare.addItem(context);
    }

    @Override
    public String getName() {
        return "Charged Identity Disc";
    }
}
