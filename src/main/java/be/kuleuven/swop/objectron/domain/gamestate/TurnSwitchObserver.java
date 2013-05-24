package be.kuleuven.swop.objectron.domain.gamestate;

import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.util.Observable;

import java.util.List;

/**
 * @author : Nik Torfs
 *         Date: 30/04/13
 *         Time: 00:21
 */
//todo consolidate this and TurnObserver, TurnManager is aggregate root?

public interface TurnSwitchObserver {

    /**
     * The turn has ended
     * @param observable an observable that is used to keep observing
     */
    void turnEnded(Observable<TurnSwitchObserver> observable);

    /**
     * Gives observers an update about the current turn
     * @param turn the current turn
     */
    void update(Turn turn);

    /**
     * Gives Observers an update that the actions have been reduced
     */
    void actionReduced();

    /**
     * Gives observes an update that the player has performed an action
     * @param turnManager The current turn manager
     */
    void actionHappened(TurnManager turnManager);
}
