package be.kuleuven.swop.objectron.model;

import java.util.*;

/**
 * @author : Nik Torfs
 *         Date: 21/02/13
 *         Time: 23:51
 */
public class KeyValueInventory implements Inventory {
    private static final int LIMIT = 6;

    private List<Item> items = new ArrayList<Item>();


    @Override
    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    @Override
    public Item retrieveItem(int identifier) {
        return items.get(identifier);
    }

     //deze methode evt private maken? Want in addItems wordt deze methode sowieso gebruikt om te controleren of de limiet al bereikt is.
    @Override
    public boolean isLimitReached() {
        return this.items.size() >= LIMIT;
    }

    @Override
    public void addItem(Item itemToAdd) {
        if(!isLimitReached()) {
            items.add(itemToAdd);
        } else {
            throw new InventoryFullException("Inventory full");
        }
    }

    @Override
    public void removeItem(int identifier) {
        items.remove(identifier);
    }

    @Override
    public void removeItem(Item item) {
        items.remove(item);
    }
}
