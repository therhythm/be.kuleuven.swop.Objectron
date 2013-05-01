package be.kuleuven.swop.objectron.domain.square;


import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.item.effect.Effect;
import be.kuleuven.swop.objectron.domain.item.effect.Teleporter;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.util.Observable;
import be.kuleuven.swop.objectron.domain.util.Position;

import java.util.*;


/**
 * @author : Nik Torfs
 *         Date: 22/02/13
 *         Time: 00:03
 */
public class Square implements Observable<SquareObserver> {
    public static final int POWER_FAILURE_CHANCE = 5;
    private final Position position;

    private Set<SquareObserver> observers = new HashSet<>();
    private PowerState state;
    private Map<Direction, Square> neighbours = new HashMap<>();
    private List<Item> items = new ArrayList<>();
    private List<Effect> effects = new ArrayList<>();
    private boolean isObstructed = false;
    private int powerFailureChance = POWER_FAILURE_CHANCE;

    public Square(final Position position) {
        this.position = position;
        this.state = new PoweredState(this);
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
        return isObstructed;
    }

    public void setObstructed(boolean value) {
        isObstructed = value;
    }

    public void stepOn(TurnManager turnManager) {
        state.stepOn(turnManager);

        setObstructed(true);
        //todo add turnmanager .getplayer to obstruction

        for(Effect effect : effects){
            effect.activate(turnManager.getCurrentTurn());
        }
    }

    public List<Item> getAvailableItems() {
        return Collections.unmodifiableList(items);
    }

    public void addItem(Item item) {
        this.items.add(item);
        notifyItemPlaced(item);
    }

    public void addEffect(Effect effect){
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

    public void transitionState(PowerState newState) {
        this.state = newState;
    }

    public void receivePowerFailure() {
        state.powerFailure();
        notifyPowerFailure();
    }

    private boolean losingPower() {
        int r = (int) (Math.random() * 100);
        return r < powerFailureChance;
    }

    public void newTurn(Turn currentTurn) {   //todo observer
        if (losingPower()) {
            receivePowerFailure();
            for (Square neighbour : neighbours.values()) {
                neighbour.receivePowerFailure();
            }
        }
        state.newTurn(currentTurn);
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
}
