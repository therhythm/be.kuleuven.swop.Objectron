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
        List<Item> returnItems = new ArrayList<Item>();
         for (Item item : items.values()){
            returnItems.add(item);
         }

        return returnItems;
    }

    @Override
    public Item retrieveItem(Integer identifier) {
        Item item = this.items.get(identifier);

        return item;

    }
     //deze methode evt private maken? Want in addItems wordt deze methode sowieso gebruikt om te controleren of de limiet al bereikt is.
    @Override
    public boolean isLimitReached() {
        if ( this.items.size()>=LIMIT)
            return true;
        else
        return false;
     }

    @Override
    public void addItem(Item itemToAdd) {
        if (isLimitReached())
            throw new IllegalStateException("limit reached");
        else
            this.items.put(5,itemToAdd);     //TODO nog manier implementeren voor die nummer
    }
}
