package be.kuleuven.swop.objectron.domain.item.deployer;

import be.kuleuven.swop.objectron.domain.exception.GameOverException;
import be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException;
import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.item.Item;

/**
 * @author : Nik Torfs
 *         Date: 24/04/13
 *         Time: 22:49
 */
public interface ItemDeployCommand {

    void deploy(Item item) throws SquareOccupiedException, GameOverException, NotEnoughActionsException;
}
