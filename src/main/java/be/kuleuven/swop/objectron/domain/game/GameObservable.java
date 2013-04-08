package be.kuleuven.swop.objectron.domain.game;

/**
 * @author : Nik Torfs
 *         Date: 15/03/13
 *         Time: 16:20
 */
public interface GameObservable {
    void attach(GameObserver observer);

    void detach(GameObserver observer);

    void notifyObservers();
}
