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

    private Effect effect;

    public LightMine() {
        effect = new ReduceAvailableActionsEffect(NB_ACTIONS_BLINDED);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Effect getEffect(){
        return effect;
    }
}
