package be.kuleuven.swop.objectron.domain.gamestate;

/**
 * @author : Nik Torfs
 *         Date: 30/04/13
 *         Time: 00:21
 */
//todo consolidate this and TurnObserver, TurnManager is aggregate root?

public interface TurnSwitchObserver {

    void turnEnded(Turn newTurn);

    void update(Turn turn);

    void actionReduced();

    void actionHappened();
}
