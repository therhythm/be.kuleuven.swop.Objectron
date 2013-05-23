package be.kuleuven.swop.objectron.domain.square;


import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Obstruction;
import be.kuleuven.swop.objectron.domain.effect.Effect;
import be.kuleuven.swop.objectron.domain.exception.*;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.movement.Movable;
import be.kuleuven.swop.objectron.domain.util.Observable;
import be.kuleuven.swop.objectron.domain.util.Position;
import be.kuleuven.swop.objectron.viewmodel.SquareViewModel;

import java.util.*;


/**
 * @author : Nik Torfs
 *         Date: 22/02/13
 *         Time: 00:03
 */
public class Square implements Observable<SquareObserver> {

    private final Position position;

    private Set<SquareObserver> observers = new HashSet<>();
    private Map<Direction, Square> neighbours = new HashMap<>();
    private List<Item> items = new ArrayList<>();
    private List<Effect> effects = new ArrayList<>();
    private Set<Obstruction> obstructions = new HashSet<>();


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

    public void stepOn(Movable movable, TurnManager manager) throws InvalidMoveException, PlayerHitException,
            WallHitException, ForceFieldHitException, GameOverException, SquareOccupiedException,
            NotEnoughActionsException {
        for (Obstruction obstruction : obstructions) {
            obstruction.hit(movable.getMovementStrategy());
        }

        List<Effect> copyOf = new ArrayList<>(effects);
        for (Effect effect : copyOf) {
            effect.activate(movable, manager);
        }
    }

    public List<Item> getAvailableItems() {
        return Collections.unmodifiableList(items);
    }

    public void addItem(Item item) {
        this.items.add(item);
        notifyItemPlaced(item);
    }

    public void addEffect(Effect effect) {
        this.effects.add(effect);
    }

    public Position getPosition() {
        return this.position;
    }

    public Item pickUpItem(int selectionId) {
        Item selectedItem = items.get(selectionId);

        items.remove(selectedItem);
        return selectedItem;
    }

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

    @Override
    public void attach(SquareObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detach(SquareObserver observer) {
        observers.remove(observer);
    }

    public void notifyItemPlaced(Item item) {
        for (SquareObserver observer : observers) {
            observer.itemPlaced(item, this.position);
        }
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
