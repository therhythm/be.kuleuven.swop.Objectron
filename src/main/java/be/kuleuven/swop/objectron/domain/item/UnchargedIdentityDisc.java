package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.movement.MovementRangeStrategy;
import be.kuleuven.swop.objectron.domain.movement.NormalMovementRangeStrategy;

/**
 * @author : Nik Torfs
 *         Date: 24/05/13
 *         Time: 15:23
 */
public class UnchargedIdentityDisc extends IdentityDisc{
    public static final int MAX_RANGE = 4;

    @Override
    public String getName() {
        return "Uncharged Identity Disc";
    }

    @Override
    protected MovementRangeStrategy getMovementRangeStrategy() {
        return new NormalMovementRangeStrategy(MAX_RANGE);
    }
}
