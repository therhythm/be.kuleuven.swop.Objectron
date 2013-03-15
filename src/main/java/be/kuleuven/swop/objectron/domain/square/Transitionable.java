package be.kuleuven.swop.objectron.domain.square;

/**
 * @author : Nik Torfs
 *         Date: 15/03/13
 *         Time: 12:15
 */
public interface Transitionable<T> {
    void transitionState(T state);
}
