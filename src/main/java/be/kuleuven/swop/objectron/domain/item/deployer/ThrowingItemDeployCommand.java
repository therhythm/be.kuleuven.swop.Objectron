package be.kuleuven.swop.objectron.domain.item.deployer;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.exception.GameOverException;
import be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException;
import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.square.Square;

/**
 * A class of ThrowingItemDeployCommands involving a Square, a Direction and a TurnManager,
 * implementing ItemDeployCommand
 * @author : Nik Torfs
 *         Date: 24/04/13
 *         Time: 22:53
 */
public class ThrowingItemDeployCommand implements ItemDeployCommand {
    private final Square sourceSquare;
    private final Direction throwingDirection;
    private final TurnManager turnManager;

    /**
     * Initialize this ThrowingItemDeployCommand with a given Square, Direction and TurnManager.
     * @param sourceSquare
     *        The Square for this ThrowingItemDeployCommand.
     * @param throwingDirection
     *        The Direction for this ThrowingItemDeployCommand.
     * @param turnManager
     *        The TurnManager for this ThrowingItemDeployCommand.
     */
    public ThrowingItemDeployCommand(Square sourceSquare, Direction throwingDirection, TurnManager turnManager) {
        this.sourceSquare = sourceSquare;
        this.throwingDirection = throwingDirection;
        this.turnManager = turnManager;
    }

    @Override
    public void deploy(Item item) throws GameOverException, SquareOccupiedException, NotEnoughActionsException {
        item.throwMe(sourceSquare, throwingDirection, turnManager);
    }
}
