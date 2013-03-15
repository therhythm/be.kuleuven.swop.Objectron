package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;

/**
 * @author : Peter Bosmans
 *         Date: 27/02/13
 *         Time: 20:56
 */
//TODO singleton by factory? no special state to be kept
public class LightMine implements Item {
    private static final int NB_ACTIONS_BLINDED = 3;
    private static final String name = "Light Mine";

    @Override
    public String getName() {
        return name;
    }

    public void activate(Player player){
        player.reduceRemainingActions(NB_ACTIONS_BLINDED);
    }
}
