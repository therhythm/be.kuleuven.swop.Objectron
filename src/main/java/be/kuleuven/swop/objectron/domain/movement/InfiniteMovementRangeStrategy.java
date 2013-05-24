package be.kuleuven.swop.objectron.domain.movement;

/**
 * A class of InfiniteMovementRangeStrategies implementing MovementRangeStrategy.
 * @author : Nik Torfs
 *         Date: 24/05/13
 *         Time: 13:20
 */
public class InfiniteMovementRangeStrategy implements MovementRangeStrategy {

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public void moved() {
        // do nothing
    }
}
