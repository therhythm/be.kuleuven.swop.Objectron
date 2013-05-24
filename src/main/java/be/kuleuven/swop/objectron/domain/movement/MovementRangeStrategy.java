package be.kuleuven.swop.objectron.domain.movement;

/**
 * @author : Nik Torfs
 *         Date: 24/05/13
 *         Time: 13:17
 */
public interface MovementRangeStrategy {

    boolean hasNext();

    void moved();


}
