package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;

/**
 * @author : Nik Torfs
 *         Date: 24/04/13
 *         Time: 22:49
 */
public interface ItemDeployer {
    void deploy(Item item) throws SquareOccupiedException; //TODO remove exception asap
}
