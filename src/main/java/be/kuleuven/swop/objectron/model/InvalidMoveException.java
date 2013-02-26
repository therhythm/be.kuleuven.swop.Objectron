package be.kuleuven.swop.objectron.model;

/**
 * @author : Nik Torfs
 *         Date: 26/02/13
 *         Time: 01:19
 */
public class InvalidMoveException extends Exception {
    public InvalidMoveException() {
        super();
    }

    public InvalidMoveException(String message) {
        super(message);
    }

    public InvalidMoveException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidMoveException(Throwable cause) {
        super(cause);
    }
}
