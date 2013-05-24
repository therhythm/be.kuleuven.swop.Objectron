package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.effect.Teleporter;
import be.kuleuven.swop.objectron.domain.exception.GameOverException;
import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.square.Square;

/**
 * A class of Flags involving a Player and a Square implementing Item.
 * User: Piet
 * Date: 13/05/13
 * Time: 21:26
 */
public class Flag implements Item {
    private static final int MAX_IN_BAG = 1;

    private final Player owner;
    private final Square base;

    /**
     * Initialize this Flag with a given Player and Square.
     * @param player
     *        The Player for this Flag.
     * @param base
     *        The Square for this Flag.
     */
    public Flag(Player player, Square base) {
        this.owner = player;
        this.base = base;
    }

    public Player getOwner() {
        return this.owner;
    }

    public void returnToBase() {
        base.addItem(this);
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
    public void throwMe(Square sourceSquare, Direction targetDirection, TurnManager turnManager) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void pickedUp() {

    }

    @Override
    public int getMaxInBag() {
        return MAX_IN_BAG;
    }

    @Override
    public void effectActivated(EffectActivation activation) {
            //todo this is not an effect...
            activation.dropMe(this);
        }
    }

