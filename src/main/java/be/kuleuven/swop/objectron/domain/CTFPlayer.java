package be.kuleuven.swop.objectron.domain;

import be.kuleuven.swop.objectron.domain.exception.InventoryFullException;
import be.kuleuven.swop.objectron.domain.exception.TooManyItemsOfSameTypeException;
import be.kuleuven.swop.objectron.domain.item.Flag;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.square.Square;

/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 22/05/13
 * Time: 19:19
 * To change this template use File | Settings | File Templates.
 */
public class CTFPlayer extends Player {

    public CTFPlayer(String name, Square currentSquare) {
        super(name, currentSquare);
    }

    @Override
    public void pickupItem(int identifier) throws InventoryFullException {
        Item item = super.getCurrentSquare().pickUpItem(identifier);

        if (item instanceof Flag) {
            Flag flag = (Flag) item;
            if (flag.getOwner().equals(this)) {
                flag.returnToBase();
                return;
            }
        }
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
