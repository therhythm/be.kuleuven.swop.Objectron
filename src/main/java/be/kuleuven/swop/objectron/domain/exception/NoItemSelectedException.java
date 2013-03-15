package be.kuleuven.swop.objectron.domain.exception;

/**
 * Created with IntelliJ IDEA.
 * User: thomas
 * Date: 15/03/13
 * Time: 14:52
 * To change this template use File | Settings | File Templates.
 */
public class NoItemSelectedException extends Exception {
    /**
     * Initialize this exception with a given message.
     *
     * @param message The message for this exception.
     * @post The new error message for this exception equals the given message.
     * | new.getMessage == message
     */
    public NoItemSelectedException(String message) {
        super(message);
    }
}
