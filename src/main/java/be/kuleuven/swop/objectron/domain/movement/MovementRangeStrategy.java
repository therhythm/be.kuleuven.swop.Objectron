package be.kuleuven.swop.objectron.domain.movement;

/**
 * An interface for MovementRangeStrategies.
 * @author : Nik Torfs
 *         Date: 24/05/13
 *         Time: 13:17
 */
public interface MovementRangeStrategy {

    /**
     * There is a next move.
     * @return there is next move.
     */
    boolean hasNext();

    /**
     * The Movable has moved.
     */
    void moved();


}
