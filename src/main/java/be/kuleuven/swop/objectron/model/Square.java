package be.kuleuven.swop.objectron.model;

import java.util.*;


/**
 * @author : Nik Torfs
 *         Date: 22/02/13
 *         Time: 00:03
 */
public class Square {
    Map<Direction, Square> neighbours = new HashMap<Direction, Square>();
    private List<Item> items = new ArrayList<Item>();
    private boolean isObstructed = false;
    private Item activeItem = null;

    public void addNeighbour(Direction direction, Square neighbour){
        neighbours.put(direction, neighbour);
    }

    public Square getNeighbour(Direction direction){
        return neighbours.get(direction);
    }

    public boolean isObstructed(){
        return isObstructed;
    }

    public void setObstructed(boolean value){
        isObstructed = value;
    }

    //TODO win scenarios
    public void stepOn(Player player){
        setObstructed(true);
        if(hasActiveItem()) {
            player.blind();
        }
    }

    public List<Item> getAvailableItems() {
        return Collections.unmodifiableList(items);
    }

    public void addItem(Item item){
       if(canHaveAsItem(item))
           this.items.add(item);
        else
           throw new IllegalArgumentException("invalid item");
    }

    private boolean canHaveAsItem(Item item){
        return item != null;
    }

    public Item pickUpItem(int selectionId) {
        Item selectedItem = items.get(selectionId);

        items.remove(selectedItem);
        return selectedItem;
    }

    public boolean hasActiveItem() {
        return activeItem != null;
    }

    public void setActiveItem(Item activeItem) {
        this.activeItem = activeItem;
    }

    public Item getActiveItem() {
        return activeItem;
    }
}
