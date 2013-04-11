package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.Settings;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;

/**
 * @author : Peter Bosmans
 *         Date: 27/02/13
 *         Time: 20:56
 */
//TODO singleton by factory? no special state to be kept
public class LightMine implements Item {
    private static final String name = "Light Mine";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean pickupAble() {
        return true;
    }

    public void activate(Player player){

        player.reduceRemainingActions(Settings.LIGHTMINE_NB_ACTIONS_BLINDED);
    }
}
