package be.kuleuven.swop.objectron.model.exception;

/**
 * Exception to indicate a square is occupied
 *
 * @author : Kasper Vervaecke
 *         Date: 01/03/13
 *         Time: 00:16
 */
public class SquareOccupiedException extends Exception {

    /**
     * Initialize this exception with a given message.
     *
     * @param message The message for this exception.
     * @post The new error message for this exception equals the given message
     * | new.getMessage == message
     */
    public SquareOccupiedException(String message) {
        super(message);
    }
}
