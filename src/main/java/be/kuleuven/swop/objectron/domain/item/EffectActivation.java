package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.square.Square;

/**
 * A class of EffectActivations involving a Player.
 * @author : Nik Torfs
 *         Date: 23/05/13
 *         Time: 03:24
 */
public class EffectActivation {
    private Player victim;

    /**
     * Initialize this EffectActivation with a given Player.
     * @param victim
     *        The Player for this EffectActivation.
     */
    public EffectActivation(Player victim){
        this.victim = victim;
    }

    /**
     * Drop an Item.
     * @param item
     *        The Item to drop.
     */
    public void dropMe(Item item){
        victim.randomlyDrop(item);
    }


}
