package be.kuleuven.swop.objectron.domain.square;


import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Obstruction;
import be.kuleuven.swop.objectron.domain.effect.Effect;
import be.kuleuven.swop.objectron.domain.exception.*;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.item.effect.PrimaryPowerFailure;
import be.kuleuven.swop.objectron.domain.movement.Movable;
import be.kuleuven.swop.objectron.domain.util.Observable;
import be.kuleuven.swop.objectron.domain.util.Position;

import java.util.*;


/**
 * @author : Nik Torfs
 *         Date: 22/02/13
 *         Time: 00:03
 */
public class Square implements Observable<SquareObserver> {
    public static final int POWER_FAILURE_CHANCE = 1;

    PowerFailure powerFailure;
    private final Position position;

    private Set<SquareObserver> observers = new HashSet<>();
   // private PowerState state;
    private Map<Direction, Square> neighbours = new HashMap<>();
    private List<Item> items = new ArrayList<>();
    private List<Effect> effects = new ArrayList<>();
    private int powerFailureChance = POWER_FAILURE_CHANCE;
    private Set<Obstruction> obstructions = new HashSet<>();


    public Square(final Position position) {
        this.position = position;
    }

    public Square(final Position position, int powerFailureChance) {
        this(position);
        this.powerFailureChance = powerFailureChance;
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
      //  state.stepOn(movable.getMovementStrategy());

        for (Effect effect : effects) {
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

    private boolean losingPower() {
        int r = (int) (Math.random() * 100);
        return r < powerFailureChance;
    }

    public void newTurn(Turn currentTurn) {   //todo observer
        if (losingPower()) {
            new PrimaryPowerFailure(this);
        }
    }

    @Override
    public void attach(SquareObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detach(SquareObserver observer) {
        observers.remove(observer);
    }

    public void notifyPowerFailure() {
        for (SquareObserver observer : observers) {
            observer.lostPower(this.position);
        }
    }

    public void notifyPowered() {
        powerFailure = null;
        for (SquareObserver observer : observers) {
            observer.regainedPower(this.position);
        }
    }

    public void notifyItemPlaced(Item item) {
        for (SquareObserver observer : observers) {
            observer.itemPlaced(item, this.position);
        }
    }

    public List<Effect> getEffects() {
        return Collections.unmodifiableList(effects);
    }

    public void addObstruction(Obstruction obstruction) {
        this.obstructions.add(obstruction);
    }

    public void removeObstruction(Obstruction obstruction) {
        this.obstructions.remove(obstruction);
    }
}
