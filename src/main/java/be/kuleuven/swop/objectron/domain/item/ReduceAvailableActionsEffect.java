package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.Player;

/**
 * @author : Nik Torfs
 *         Date: 10/03/13
 *         Time: 22:25
 */
public class ReduceAvailableActionsEffect implements Effect {
    private int amountToReduce;

    public ReduceAvailableActionsEffect(int amountToReduce) {
        this.amountToReduce = amountToReduce;
    }

    @Override
    public void activate(Player player) {
        int amountToReduceWith = Math.min(player.getAvailableActions(), amountToReduce);
        player.reduceRemainingActions(amountToReduceWith);
        amountToReduce -= amountToReduceWith;

        if (amountToReduce == 0) {
            player.removeEffect(this);
        }
    }
}
