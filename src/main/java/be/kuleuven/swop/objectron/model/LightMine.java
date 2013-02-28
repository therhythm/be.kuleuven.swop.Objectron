package be.kuleuven.swop.objectron.model;

/**
 * @author : Peter Bosmans
 *         Date: 27/02/13
 *         Time: 20:56
 */
public class LightMine implements Item {

    @Override
    public void use(Square square) throws SquareOccupiedException {
        if (canHaveAsSquare(square)) {
            square.setActiveItem(this);
        } else {
            throw new SquareOccupiedException("invalid square or square already has a light grenade");
        }
    }

    private boolean canHaveAsSquare(Square square) {
        return square != null && !square.hasActiveItem();
    }
}
