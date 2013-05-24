package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.square.Square;

/**
 * @author : Nik Torfs
 *         Date: 23/05/13
 *         Time: 03:24
 */
public class EffectActivation {
    private Player victim;

    public EffectActivation(Player victim){
        this.victim = victim;
    }

    public void dropMe(Item item){
        victim.randomlyDrop(item);
    }


}
