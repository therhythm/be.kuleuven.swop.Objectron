package be.kuleuven.swop.objectron.domain.square;


import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Player;

import be.kuleuven.swop.objectron.domain.Settings;
import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.util.Position;
import be.kuleuven.swop.objectron.viewmodel.SquareViewModel;

import java.util.*;


/**
 * @author : Nik Torfs
 *         Date: 22/02/13
 *         Time: 00:03
 */
public class Square implements Transitionable<SquareState> {
    private final Position position;

    private SquareState state;
    private Map<Direction, Square> neighbours = new HashMap<Direction, Square>();
    private List<Item> items = new ArrayList<Item>();
    private boolean isObstructed = false;
    private Item activeItem;

    public Square(final Position position) {
        this.position = position;
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
        state.stepOn(player);

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

    public Position getPosition(){
        return this.position;
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

    public boolean isValidPosition(Direction direction) {
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
        return position.toString();
    }

    @Override
    public void transitionState(SquareState newState) {
        this.state = newState;
    }

    public void receivePowerFailure(){
        state.powerFailure(this);
    }

    private boolean losingPower(){
        int r = (int) (Math.random() * 100);
        return r <= Settings.POWER_FAILURE_CHANCE;
    }

    public void newTurn(Player player){
        if(losingPower()){
            receivePowerFailure();
            for(Square neighbour : neighbours.values()){
                neighbour.receivePowerFailure();
            }
        }
        boolean currentSquare = player.getCurrentSquare().equals(this);
        state.newTurn(player, currentSquare, this);
    }
}
