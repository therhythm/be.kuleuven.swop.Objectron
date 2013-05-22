package be.kuleuven.swop.objectron.domain;

import be.kuleuven.swop.objectron.domain.exception.InventoryFullException;
import be.kuleuven.swop.objectron.domain.exception.TooManyItemsOfSameTypeException;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.square.Square;

/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 22/05/13
 * Time: 19:21
 * To change this template use File | Settings | File Templates.
 */
public class PlayerRace extends Player {
    public PlayerRace(String name, Square currentSquare) {
        super(name, currentSquare);
    }

    @Override
    public void pickupItem(int identifier) throws InventoryFullException {
        Item item = super.getCurrentSquare().pickUpItem(identifier);

        try {
            super.inventory.addItem(item);
        } catch (InventoryFullException ex) {
            super.getCurrentSquare().addItem(item);
            throw ex;
        } catch (TooManyItemsOfSameTypeException e) {
            super.getCurrentSquare().addItem(item);
        }
        super.actionPerformed();
    }
}
