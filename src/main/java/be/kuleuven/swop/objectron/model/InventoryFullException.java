package be.kuleuven.swop.objectron.model;

/**
 * Exception to indicate a full inventory
 *
 * @author : Kasper Vervaecke
 *         Date: 25/02/13
 *         Time: 19:00
 */
public class InventoryFullException extends Exception {

    /**
     * Initialize this exception with a given message.
     *
     * @param message The message for this exception.
     * @post The new error message for this exception equals the given message.
     * | new.getMessage == message
     */
    public InventoryFullException(String message) {
        super(message);
    }
}
