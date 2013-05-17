package be.kuleuven.swop.objectron.domain.item.deployer;

import be.kuleuven.swop.objectron.domain.exception.GameOverException;
import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.item.Flag;
import be.kuleuven.swop.objectron.domain.item.Item;

/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 17/05/13
 * Time: 9:13
 * To change this template use File | Settings | File Templates.
 */
public class ReturnFlagToBaseDeployer implements ItemDeployer {
    @Override
    public void deploy(Item item) throws SquareOccupiedException, GameOverException {
        Flag flag = (Flag) item;
        flag.returnToBase();
    }
}
