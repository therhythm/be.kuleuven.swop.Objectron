package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;

/**
 * Created with IntelliJ IDEA.
 * User: Peter
 * Date: 11/04/13
 * Time: 14:49
 * To change this template use File | Settings | File Templates.
 */
public interface IdentityDiscType {
    public void useItem(UseItemRequest useItemRequest,IdentityDisc identityDisc) throws SquareOccupiedException;
    public String getName();
}
