package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.exception.GameOverException;
import be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException;
import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.square.Square;

/**
 * An interface for Items.
 * @author : Nik Torfs
 *         Date: 21/02/13
 *         Time: 23:52
 */
public interface Item {

    String getName();

    /**
     * Place this item on a given Square.
     * @param targetSquare
     *        The Square to place this Item on.
     * @throws SquareOccupiedException
     *         The Square is occupied.
     *         | targetSquare.isObstructed()
     */
    void place(Square targetSquare) throws SquareOccupiedException;

    /**
     * Throw this Item.
     * @param sourceSquare
     *        The Square this Item leaves from.
     * @param targetDirection
     *        The Direction this Item is thrown in.
     * @param turnManager
     *        The TurnManager to execute the throw with.
0
     * @throws SquareOccupiedException
     *         The Square is occupied.
     *         | sourceSquare.isObstructed()
     */
    void throwMe(Square sourceSquare, Direction targetDirection, TurnManager turnManager) throws SquareOccupiedException;

    /**
     * The Item is picked up.
     */
    void pickedUp();

    int getMaxInBag();

    /**
     * The effect is activated on this Item.
     * @param activation
     *        The EffectActivation to activate on this Item.
     */
    void effectActivated(EffectActivation activation);
}
