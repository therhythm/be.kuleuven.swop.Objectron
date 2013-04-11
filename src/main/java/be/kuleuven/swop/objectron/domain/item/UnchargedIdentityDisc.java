package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.square.Square;

/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 11/04/13
 * Time: 14:37
 * To change this template use File | Settings | File Templates.
 */
public class UnchargedIdentityDisc implements IdentityDiscType {
    private final int maxRange = 4;
    @Override
        public void useItem(UseItemRequest useItemRequest,IdentityDisc identityDisc) throws SquareOccupiedException {
            Square currentSquare = useItemRequest.getSquare();
            Square neighbor = currentSquare.getNeighbour(useItemRequest.getDirection());
            for (int i = 0; i < maxRange; i++) {
                System.out.println(neighbor);
                if (neighbor == null)
                    break;
                if (identityDisc.playerHit(useItemRequest, neighbor)) {
                    currentSquare = neighbor;
                    break;

                } else if (!useItemRequest.getGrid().isWall(neighbor)) {
                    currentSquare = neighbor;
                    neighbor = neighbor.getNeighbour(useItemRequest.getDirection());
                } else
                    break;
            }
            currentSquare.addItem(identityDisc);
    }

    @Override
    public String getName() {
        return "uncharged identity disc";
    }
}
