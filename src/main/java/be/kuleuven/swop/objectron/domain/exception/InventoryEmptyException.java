package be.kuleuven.swop.objectron.domain.exception;

/**
 * Exception to indicate an empty inventory
 *
 * @author : Kasper Vervaecke
 *         Date: 25/02/2013
 *         Time: 14:36
 */
public class InventoryEmptyException extends Exception {

    /**
     * Initialize this exception with a given message.
     *
     * @param message The message for this exception.
     * @post The new error message for this exception equals the given message.
     * | new.getMessage == message
     */
    public InventoryEmptyException(String message) {
        super(message);
    }
}
