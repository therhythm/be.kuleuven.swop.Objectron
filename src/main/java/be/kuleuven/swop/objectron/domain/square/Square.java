package be.kuleuven.swop.objectron.domain.square;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Obstruction;
import be.kuleuven.swop.objectron.domain.effect.Effect;
import be.kuleuven.swop.objectron.domain.exception.*;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.movement.Movable;
import be.kuleuven.swop.objectron.domain.movement.Movement;
import be.kuleuven.swop.objectron.domain.util.Observable;
import be.kuleuven.swop.objectron.domain.util.Position;
import be.kuleuven.swop.objectron.viewmodel.SquareViewModel;

import java.util.*;

/**
 * A class of Squares involving a Position.
 * @author : Nik Torfs
 *         Date: 22/02/13
 *         Time: 00:03
 */
public class Square {

    private final Position position;

    private Map<Direction, Square> neighbours = new HashMap<>();
    private List<Item> items = new ArrayList<>();
    private List<Effect> effects = new ArrayList<>();
    private Set<Obstruction> obstructions = new HashSet<>();

    /**
     * Initialize this Square with a given position.
     * @param position
     *        The Position to initialize this Square with.
     */
    public Square(final Position position) {
        this.position = position;
    }

    public void addNeighbour(Direction direction, Square neighbour) {
        neighbours.put(direction, neighbour);
    }

    public Square getNeighbour(Direction direction) {
        return neighbours.get(direction);
    }

    public boolean isObstructed() {
        return !obstructions.isEmpty();
    }

    /**
     * A Movable steps on this Square with a TurnManager.
     * @param movement
     *        The movement that steps on this square.
     * @param manager
     *        The TurnManager to execute the step with.
     * @throws InvalidMoveException
     *         This is an invalid move.
     */
    public void stepOn(Movement movement, TurnManager manager) throws InvalidMoveException{
        for (Obstruction obstruction : obstructions) {
            obstruction.hit(movement);
        }

        List<Effect> copyOf = new ArrayList<>(effects);
        for (Effect effect : copyOf) {
            effect.activate(movement, manager);
        }
    }

    public List<Item> getAvailableItems() {
        return Collections.unmodifiableList(items);
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public void addEffect(Effect effect) {
        this.effects.add(effect);
    }

    public Position getPosition() {
        return this.position;
    }

    /**
     * Pick up an Item from this Square.
     * @param selectionId
     *        The ID of the Item to pick up.
     * @return the Item to pick up.
     */
    public Item pickUpItem(int selectionId) {
        Item selectedItem = items.get(selectionId);

        items.remove(selectedItem);
        return selectedItem;
    }

    /**
     * This Square is a valid position to move to.
     * @param direction
     *        The direction a Movable wants to move in.
     * @return this Square is a valid position.
     */
    public boolean isValidPosition(Direction direction) {
        if (this.isObstructed())
            return false;

        //diagonaal check
        if (direction == Direction.UP_LEFT)
            if (this.getNeighbour(Direction.RIGHT).isObstructed() && this.getNeighbour(Direction.DOWN).isObstructed())
                return false;

        if (direction == Direction.DOWN_LEFT)
            if (this.getNeighbour(Direction.RIGHT).isObstructed() && this.getNeighbour(Direction.UP).isObstructed())
                return false;

        if (direction == Direction.UP_RIGHT)
            if (this.getNeighbour(Direction.LEFT).isObstructed() && this.getNeighbour(Direction.DOWN).isObstructed())
                return false;

        if (direction == Direction.DOWN_RIGHT)
            if (this.getNeighbour(Direction.LEFT).isObstructed() && this.getNeighbour(Direction.UP).isObstructed())
                return false;

        return true;

    }

    public String toString() {
        return position.toString() + "\n" + "isObstructed: " + this.isObstructed();
    }

    public List<Effect> getEffects() {
        return Collections.unmodifiableList(effects);
    }

    public void removeEffect(Effect e){
        effects.remove(e);
    }

    public void addObstruction(Obstruction obstruction) {
        this.obstructions.add(obstruction);
    }

    public void removeObstruction(Obstruction obstruction) {
        this.obstructions.remove(obstruction);
    }

    /**
     * Return lists of Effects, Obstructions and Items on this Square.
     * @return lists of Effects, Obstructions and Items.
     */
    public SquareViewModel getViewModel(){
        List<Class<?>> effects = new ArrayList<>();
        for(Effect e: getEffects()){
            effects.add(e.getClass());
        }

        List<Class<?>> obstructionList = new ArrayList<>();
        for(Obstruction o: obstructions){
            obstructionList.add(o.getClass());
        }

        List<Class<?>> itemList = new ArrayList<>();
        for(Item i: items){
            itemList.add(i.getClass());
        }
        return new SquareViewModel(this.position, effects, obstructionList, itemList);
    }
}
