package be.kuleuven.swop.objectron.model.item;

import be.kuleuven.swop.objectron.util.Nullable;
import be.kuleuven.swop.objectron.model.Square;
import be.kuleuven.swop.objectron.model.exception.SquareOccupiedException;

/**
 * @author : Nik Torfs
 *         Date: 21/02/13
 *         Time: 23:52
 */
public interface Item extends Nullable {

    /**
     * Use the item on the specified square
     *
     * @param square: square where the item is used
     */
    void use(Square square) throws SquareOccupiedException;

    ItemSpecification getSpecification();

    /**
     * activate the item TODO
     */
    void activate();
}
