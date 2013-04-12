package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
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
    public void useItem(UseItemRequest useItemRequest, IdentityDisc identityDisc) throws SquareOccupiedException {
        Square currentSquare = useItemRequest.getSquare();
        Square neighbor = currentSquare.getNeighbour(useItemRequest.getDirection());
        while (neighbor != null && (!useItemRequest.getGrid().isWall(neighbor))) {
            currentSquare = neighbor;
            System.out.println(neighbor);
            if (identityDisc.playerHit(useItemRequest, neighbor))
                break;

            neighbor = neighbor.getNeighbour(useItemRequest.getDirection());


        }
        System.out.println("currentSquare = " + currentSquare);
        currentSquare.addItem(identityDisc);
    }

    @Override
    public String getName() {
        return "Charged Identity Disc";
    }
}
