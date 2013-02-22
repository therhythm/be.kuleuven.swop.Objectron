package be.kuleuven.swop.objectron.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : Nik Torfs
 *         Date: 21/02/13
 *         Time: 23:51
 */
public class KeyValueInventory implements Inventory {

    //TODO integer as a key or strings?
    private Map<Integer, Item> items = new HashMap<Integer, Item>();


    @Override
    public List<Item> getItems() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Item retrieveItem(Integer identifier) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isLimitReached() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addItem(Item itemToAdd) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
