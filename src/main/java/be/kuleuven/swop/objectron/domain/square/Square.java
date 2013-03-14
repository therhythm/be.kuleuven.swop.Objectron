package be.kuleuven.swop.objectron.domain.square;


import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Player;

import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.viewmodel.SquareViewModel;

import java.util.*;


/**
 * @author : Nik Torfs
 *         Date: 22/02/13
 *         Time: 00:03
 */
public class Square {
    private final int horizontalIndex;
    private final int verticalIndex;

    private SquareState state;
    private Map<Direction, Square> neighbours = new HashMap<Direction, Square>();
    private List<Item> items = new ArrayList<Item>();
    private boolean isObstructed = false;
    private Item activeItem;

    public Square(int horizontalIndex, int verticalIndex) {
        this.horizontalIndex = horizontalIndex;
        this.verticalIndex = verticalIndex;

        this.state = new PoweredSquareState();
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

    public void stepOn(Player player) {
        state.stepOn(player, this);

        setObstructed(true);
        if(hasActiveItem()){
            activeItem.activate(player);
            activeItem = null;
        }
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
        return activeItem != null;
    }

    public void setActiveItem(Item activeItem) throws SquareOccupiedException {
        if(hasActiveItem()){
            throw new SquareOccupiedException("The square already has an active item");
        }
        this.activeItem = activeItem;
    }

    public SquareViewModel getSquareViewModel() {
        return new SquareViewModel(getHorizontalIndex(), getVerticalIndex());
    }

    public String toString(){
        String result = "";
        result += "Horizontal position: " + this.horizontalIndex + "\n";
        result += "Vertical position: " + this.verticalIndex + "\n";

        return result;
    }
}
