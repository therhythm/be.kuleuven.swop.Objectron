package be.kuleuven.swop.objectron.domain.square;

import be.kuleuven.swop.objectron.domain.Player;

/**
 * @author : Nik Torfs
 *         Date: 13/03/13
 *         Time: 03:00
 */
public class PoweredSquareState implements SquareState {

    @Override
    public void newTurn(Player player, Square sq) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void stepOn(Player player, Square sq) {
        if(sq.hasActiveItem()){

        }
    }

    @Override
    public void powerFailure() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
