package be.kuleuven.swop.objectron.domain.movement;

/**
 * A class of NormalMovementRangeStrategies involving a range, implementing MovementRangeStrategy.
 * @author : Nik Torfs
 *         Date: 24/05/13
 *         Time: 13:19
 */
public class NormalMovementRangeStrategy implements MovementRangeStrategy {

    private int range;

    /**
     * Initialize this NormalMovementRangeStrategy with a given range.
     * @param range
     *        The range for this NormalMovementRangeStrategy.
     */
    public NormalMovementRangeStrategy(int range) {
        this.range = range;
    }

    @Override
    public boolean hasNext() {
        return range > 0;
    }

    @Override
    public void moved() {
        range--;
    }
}
