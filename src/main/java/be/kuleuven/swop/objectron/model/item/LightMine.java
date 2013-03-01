package be.kuleuven.swop.objectron.model.item;

import be.kuleuven.swop.objectron.model.Square;
import be.kuleuven.swop.objectron.model.exception.SquareOccupiedException;

/**
 * @author : Peter Bosmans
 *         Date: 27/02/13
 *         Time: 20:56
 */
public class LightMine implements Item {

    private static ItemSpecification itemSpecification;

    static {
        itemSpecification = new ItemSpecification("Light Mine", "A mine that blinds the player that steps on it.");
    }

    @Override
    public void use(Square square) throws SquareOccupiedException {
        if (canHaveAsSquare(square)) {
            square.setActiveItem(this);
        } else {
            throw new SquareOccupiedException("invalid square or square already has a light grenade");
        }
    }

    @Override
    public ItemSpecification getSpecification() {
        return itemSpecification;
    }

    private boolean canHaveAsSquare(Square square) {
        return square != null && !square.hasActiveItem();
    }
}
