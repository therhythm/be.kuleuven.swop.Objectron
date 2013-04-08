package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.Settings;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;

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

    public void activate(Turn currentTurn){

        currentTurn.reduceRemainingActions(Settings.LIGHTMINE_NB_ACTIONS_BLINDED);
    }
}
