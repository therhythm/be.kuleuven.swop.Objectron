package be.kuleuven.swop.objectron.model.listener;

import be.kuleuven.swop.objectron.model.Square;

/**
 * @author : Nik Torfs
 *         Date: 26/02/13
 *         Time: 22:51
 */
public interface GameEventListener {

    void playerChanged(String newPlayer);

    void gameEnding(String winner, String loser);

}
