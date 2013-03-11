package be.kuleuven.swop.objectron.model;

import be.kuleuven.swop.objectron.model.item.Item;
import be.kuleuven.swop.objectron.model.item.NullItem;
import be.kuleuven.swop.objectron.viewmodel.SquareViewModel;

import java.util.*;


/**
 * @author : Nik Torfs
 *         Date: 22/02/13
 *         Time: 00:03
 */
public class Square {
    final int horizontalIndex;
    final int verticalIndex;

    Map<Direction, Square> neighbours = new HashMap<Direction, Square>();
    private List<Item> items = new ArrayList<Item>();
    private boolean isObstructed = false;
    private Item activeItem = new NullItem();

    public Square(int horizontalIndex, int verticalIndex) {
        this.horizontalIndex = horizontalIndex;
        this.verticalIndex = verticalIndex;
    }

    public void addNeighbour(Direction direction, Square neighbour) {
        neighbours.put(direction, neighbour);
    }

    public Square getNeighbour(Direction direction) {
        return neighbours.get(direction);
    }

    public boolean isObstructed() {
        return isObstructed;
    }

    public void setObstructed(boolean value) {
        isObstructed = value;
    }

    public void stepOn(Player player){
        setObstructed(true);
        activeItem.activate(player);
    }

    public List<Item> getAvailableItems() {
        return Collections.unmodifiableList(items);
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public int getHorizontalIndex() {
        return horizontalIndex;
    }

    public int getVerticalIndex() {
        return verticalIndex;
    }

    public Item pickUpItem(int selectionId) {
        Item selectedItem = items.get(selectionId);

        items.remove(selectedItem);
        return selectedItem;
    }

    public boolean hasActiveItem() {
        return !activeItem.isNull();
    }

    public void setActiveItem(Item activeItem) {
        this.activeItem = activeItem;
    }

    public SquareViewModel getSquareViewModel(){
        return new SquareViewModel(getHorizontalIndex(), getVerticalIndex());
    }

    public String toString(){
        String result = "";
        result += "Horizontal position: " + this.horizontalIndex + "\n";
        result += "Vertical position: " + this.verticalIndex + "\n";

        return result;
    }
}
