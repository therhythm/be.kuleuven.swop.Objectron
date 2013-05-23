package be.kuleuven.swop.objectron.domain.exception;

/**
 * @author : Nik Torfs
 *         Date: 22/05/13
 *         Time: 23:58
 */
public class TooManyPlayersException extends Throwable {
    public TooManyPlayersException(String message) {
        super(message);
    }
}
