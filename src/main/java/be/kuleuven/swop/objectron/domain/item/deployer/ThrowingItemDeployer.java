package be.kuleuven.swop.objectron.domain.item.deployer;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.exception.GameOverException;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.square.Square;

/**
 * @author : Nik Torfs
 *         Date: 24/04/13
 *         Time: 22:53
 */
public class ThrowingItemDeployer implements ItemDeployer {
    private final Square sourceSquare;
    private final Direction throwingDirection;
    private final TurnManager turnManager;

    public ThrowingItemDeployer(Square sourceSquare, Direction throwingDirection, TurnManager turnManager) {
        this.sourceSquare = sourceSquare;
        this.throwingDirection = throwingDirection;
        this.turnManager = turnManager;
    }

    @Override
    public void deploy(Item item) throws GameOverException {
        item.throwMe(sourceSquare, throwingDirection, turnManager);
    }
}
