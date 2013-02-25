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
    void use(Square square);

    /**
     * Set the identifier for this item
     *
     * @param identifier: identifier for this item
     */
    void setIdentifier(int identifier);

    /**
     * Retrieve the identifier for this item
     *
     * @return the identifier for this item
     */
    int getIdentifier();
}
