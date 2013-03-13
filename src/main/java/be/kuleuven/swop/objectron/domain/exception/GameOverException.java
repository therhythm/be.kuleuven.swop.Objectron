package be.kuleuven.swop.objectron.domain.exception;

/**
 * Created with IntelliJ IDEA.
 * User: thomas
 * Date: 28/02/13
 * Time: 15:15
 * To change this template use File | Settings | File Templates.
 */
public class GameOverException extends Exception {
    /**
     * Initialize this exception with a given message.
     *
     * @param message The message for this exception.
     * @post The new error message for this exception equals the given message.
     * | new.getMessage == message
     */
    public GameOverException(String message) {
        super(message);
    }
}
