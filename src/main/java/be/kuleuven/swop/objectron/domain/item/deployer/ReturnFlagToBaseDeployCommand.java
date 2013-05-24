package be.kuleuven.swop.objectron.domain.item.deployer;

import be.kuleuven.swop.objectron.domain.exception.GameOverException;
import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.item.Flag;
import be.kuleuven.swop.objectron.domain.item.Item;

/**
 * A class of ReturnFlagToBaseDeployCommands implementing ItemDeployCommand.
 * User: Piet
 * Date: 17/05/13
 * Time: 9:13
 */
public class ReturnFlagToBaseDeployCommand implements ItemDeployCommand {
    @Override
    public void deploy(Item item) throws SquareOccupiedException, GameOverException {
        Flag flag = (Flag) item;
        flag.returnToBase();
    }
}
