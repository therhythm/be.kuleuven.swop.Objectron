package be.kuleuven.swop.objectron.domain.gamestate;

/**
 * @author : Nik Torfs
 *         Date: 29/04/13
 *         Time: 22:48
 */
public interface TurnObserver {

    void update(Turn turn);

    void penaltyAdded();

    void actionReduced();
}
