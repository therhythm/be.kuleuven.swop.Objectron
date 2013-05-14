package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.GameOverException;
import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.square.Square;

/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 13/05/13
 * Time: 21:26
 * To change this template use File | Settings | File Templates.
 */
public class Flag implements Item {
    private static final int MAX_IN_BAG = 1;

    private final Player owner;
    private final Square base;

    public Flag(Player player, Square base) {
        this.owner = player;
        this.base = base;
    }

    public Player getOwner() {
        return this.owner;
    }

    @Override
    public String getName() {
        return "Flag";
    }

    @Override
    public void place(Square targetSquare) throws SquareOccupiedException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void throwMe(Square sourceSquare, Direction targetDirection, TurnManager turnManager) throws GameOverException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void pickedUp() {
    }

    @Override
    public int getMaxInBag() {
        return MAX_IN_BAG;
    }
}
