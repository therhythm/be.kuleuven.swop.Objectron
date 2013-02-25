package be.kuleuven.swop.objectron.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : Nik Torfs
 *         Date: 21/02/13
 *         Time: 23:51
 */
public class KeyValueInventory implements Inventory {

    private static final int LIMIT = 6;

    //TODO integer as a key or strings?
    private Map<Integer, Item> items = new HashMap<Integer, Item>();


    @Override
    public List<Item> getItems() {
        return new ArrayList<Item>(items.values());
    }

    @Override
    public Item retrieveItem(Integer identifier) {
        return items.get(identifier);
    }

    @Override
    public boolean isLimitReached() {
        if(items.size() < LIMIT) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void addItem(Item itemToAdd) {
        if(!isLimitReached()) {
            items.put(itemToAdd.getIdentifier(), itemToAdd);
        } else {
            throw new InventoryFullException("Inventory full");
        }
    }

    @Override
    public void removeItem(int identifier) {
        items.remove(identifier);
    }
}
