package be.kuleuven.swop.objectron.model.item;

import be.kuleuven.swop.objectron.model.Player;
import be.kuleuven.swop.objectron.model.Square;
import be.kuleuven.swop.objectron.model.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.util.Nullable;

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

    String getName();

    /**
     * activate the item
     */
    void activate(Player player);
}
