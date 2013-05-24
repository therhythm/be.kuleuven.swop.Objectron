package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.movement.InfiniteMovementRangeStrategy;
import be.kuleuven.swop.objectron.domain.movement.MovementRangeStrategy;

/**
 * @author : Nik Torfs
 *         Date: 24/05/13
 *         Time: 15:21
 */
public class ChargedIdentityDisc extends IdentityDisc{

    @Override
    public String getName() {
        return "Charged Identity Disc";
    }

    @Override
    protected MovementRangeStrategy getMovementRangeStrategy() {
        return new InfiniteMovementRangeStrategy();
    }
}
