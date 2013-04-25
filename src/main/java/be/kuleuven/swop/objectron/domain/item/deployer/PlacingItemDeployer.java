package be.kuleuven.swop.objectron.domain.item.deployer;

import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.square.Square;

/**
 * @author : Nik Torfs
 *         Date: 24/04/13
 *         Time: 22:51
 */
public class PlacingItemDeployer implements ItemDeployer {
    private final Square targetSquare;

    public PlacingItemDeployer(Square targetSquare) {
        this.targetSquare = targetSquare;
    }

    @Override
    public void deploy(Item item) throws SquareOccupiedException {
        item.place(targetSquare);
    }
}
