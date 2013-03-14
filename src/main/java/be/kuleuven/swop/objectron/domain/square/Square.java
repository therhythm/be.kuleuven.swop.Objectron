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
    private static final int POWER_FAILURE_CHANCE = 5;

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
            player.addEffect(activeItem.getEffect());
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
    //TODO properder programmeren indien mogelijk, evt implementeren op square niveau?
    public boolean isValidPosition(Direction direction) {
        if(this ==null)
            return false;
        if ( this.isObstructed())
            return false;

        //diagonaal check
        if(direction == Direction.UP_LEFT)
            if(this.getNeighbour(Direction.RIGHT).isObstructed() && this.getNeighbour(Direction.DOWN).isObstructed())
                return false;

        if(direction == Direction.DOWN_LEFT)
            if(this.getNeighbour(Direction.RIGHT).isObstructed() && this.getNeighbour(Direction.UP).isObstructed())
                return false;

        if(direction == Direction.UP_RIGHT)
            if(this.getNeighbour(Direction.LEFT).isObstructed() && this.getNeighbour(Direction.DOWN).isObstructed())
                return false;

        if(direction == Direction.DOWN_RIGHT)
            if(this.getNeighbour(Direction.LEFT).isObstructed() && this.getNeighbour(Direction.UP).isObstructed())
                return false;

        return true;

    }

    public String toString(){
        String result = "";
        result += "Horizontal position: " + this.horizontalIndex + "\n";
        result += "Vertical position: " + this.verticalIndex + "\n";

        return result;
    }

    public void transitionState(SquareState newState) {
        this.state = newState;
    }

    public void receivePowerFailure(){
        state.powerFailure(this);
    }

    private void newTurn(Player player){ //TODO this should be notified (and public)... Observer?
        if(hasPowerFailure()){
            receivePowerFailure();
            for(Square neighbour : neighbours.values()){
                neighbour.receivePowerFailure();
            }
        }
        state.newTurn(player, this);
    }

    private boolean hasPowerFailure(){
        int r = (int) (Math.random() * 100);
        return r <= POWER_FAILURE_CHANCE;
    }
}
