package be.kuleuven.swop.objectron.domain.item.deployer;

import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.square.Square;

/**
 * A class of PlacingItemDeployCommands involving a Square, implementing ItemDeployCommand.
 * @author : Nik Torfs
 *         Date: 24/04/13
 *         Time: 22:51
 */
public class PlacingItemDeployCommand implements ItemDeployCommand {
    private final Square targetSquare;

    /**
     * Initialize this PlacingItemDeployCommand with a given Square.
     * @param targetSquare
     *        The Square for this PlacingItemDeployCommand.
     */
    public PlacingItemDeployCommand(Square targetSquare) {
        this.targetSquare = targetSquare;
    }

    @Override
    public void deploy(Item item) throws SquareOccupiedException {
        item.place(targetSquare);
    }
}
