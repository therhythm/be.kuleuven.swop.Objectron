package be.kuleuven.swop.objectron.model.item;

import be.kuleuven.swop.objectron.model.Player;
import be.kuleuven.swop.objectron.model.Square;
import be.kuleuven.swop.objectron.model.exception.SquareOccupiedException;

/**
 * @author : Peter Bosmans
 *         Date: 27/02/13
 *         Time: 20:56
 */
public class LightMine implements Item {
    private static final int NB_ACTIONS_BLINDED = 3;
    private static final String name = "Light Mine";

    private Effect effect;

    public LightMine(){
        effect = new ReduceAvailableActionsEffect(NB_ACTIONS_BLINDED);
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
    public String getName() {
        return name;
    }

    @Override
    public void activate(Player player) {
        player.addEffect(effect);
    }

    private boolean canHaveAsSquare(Square square) {
        return square != null && !square.hasActiveItem();
    }

    @Override
    public boolean isNull() {
        return false;
    }
}
