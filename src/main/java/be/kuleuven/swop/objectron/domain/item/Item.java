package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.gamestate.Turn;

/**
 * @author : Nik Torfs
 *         Date: 21/02/13
 *         Time: 23:52
 */
public interface Item{

    String getName();

    void activate(Turn currentTurn);
}
