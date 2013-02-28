package be.kuleuven.swop.objectron.model;

/**
 * @author : Nik Torfs
 *         Date: 21/02/13
 *         Time: 23:52
 */
public interface Item {

    /**
     * Use the item on the specified square
     *
     * @param square: square where the item is used
     */
    void use(Square square) throws SquareOccupiedException;
}
