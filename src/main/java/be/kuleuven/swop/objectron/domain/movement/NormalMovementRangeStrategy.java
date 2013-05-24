package be.kuleuven.swop.objectron.domain.movement;

/**
 * @author : Nik Torfs
 *         Date: 24/05/13
 *         Time: 13:19
 */
public class NormalMovementRangeStrategy implements MovementRangeStrategy {

    private int range;

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
