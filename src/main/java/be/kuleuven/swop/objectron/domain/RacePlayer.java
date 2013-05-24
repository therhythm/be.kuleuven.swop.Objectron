package be.kuleuven.swop.objectron.domain;

import be.kuleuven.swop.objectron.domain.exception.InventoryFullException;
import be.kuleuven.swop.objectron.domain.exception.TooManyItemsOfSameTypeException;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.square.Square;

/**
 * A class of RacePlayers, a special kind of Player.
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 22/05/13
 * Time: 19:21
 */
public class RacePlayer extends Player {

    /**
     * Initialize this RacePlayer with a given name and Square.
     * @param name
     *        The name of this player.
     * @param currentSquare
     *        The square of this player.
     * @post  This RacePlayer is initialized with the given name and Square.
     *        | new.this.getName() == name
     *        | new.this.getCurrentSquare() = currentSquare
     */
    public RacePlayer(String name, Square currentSquare) {
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
