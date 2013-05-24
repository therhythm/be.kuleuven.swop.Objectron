package be.kuleuven.swop.objectron.domain.gamestate;

import be.kuleuven.swop.objectron.domain.Player;

/**
 * @author : Nik Torfs
 *         Date: 29/04/13
 *         Time: 22:48
 */
public interface TurnObserver {

    void update(Turn turn);

    void penaltyAdded();

    void actionReduced();

    void killPlayer(Player player);
}
