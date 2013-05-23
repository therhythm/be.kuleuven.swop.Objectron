package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.effect.Effect;

/**
 * @author : Nik Torfs
 *         Date: 23/05/13
 *         Time: 03:24
 */
public class EffectActivation {
    private Effect activator;
    private Player victim;

    public EffectActivation(Effect activator, Player victim){
        this.activator = activator;
        this.victim = victim;
    }

    public void dropMe(Item item){
        victim.randomlyDrop(item);
    }

    public Effect getActivator() {
        return activator;
    }
}
