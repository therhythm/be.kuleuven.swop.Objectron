package be.kuleuven.swop.objectron.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author : Nik Torfs
 *         Date: 22/02/13
 *         Time: 00:03
 */
public class Square {


    private List<Item> items;



    public List<Item> getAvailableItems() {
        return Collections.unmodifiableList(items);
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addItem(Item item){
       if(canHaveAsItem(item))
           this.items.add(item);
        else
           throw new IllegalArgumentException("invalid item");
    }

    private boolean canHaveAsItem(Item item){
        if(item == null)
            return false;
        return true;
    }

    public void removeItem(Item item){
        if(!this.items.contains(item))
            throw new IllegalStateException("item to remove doesn't exist");
        this.items.remove(item);
    }


}
