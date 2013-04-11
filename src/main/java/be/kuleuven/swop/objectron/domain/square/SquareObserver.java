package be.kuleuven.swop.objectron.domain.square;

import be.kuleuven.swop.objectron.domain.util.Position;

/**
 * Created with IntelliJ IDEA.
 * User: Nik
 * Date: 4/11/13
 * Time: 12:29 PM
 * To change this template use File | Settings | File Templates.
 */
public interface SquareObserver {
    public void lostPower(Position position);
    public void regainedPower(Position position);
}
