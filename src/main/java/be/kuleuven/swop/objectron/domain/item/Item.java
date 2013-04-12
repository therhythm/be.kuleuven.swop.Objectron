package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.square.Square;

/**
 * @author : Nik Torfs
 *         Date: 21/02/13
 *         Time: 23:52
 */
public interface Item{

    String getName();
    boolean pickupAble();

    void activate(ActivateRequest activateRequest);

    void useItem(UseItemRequest useItemRequest) throws SquareOccupiedException;

    boolean isTeleporting();
}
