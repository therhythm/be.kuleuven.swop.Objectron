package be.kuleuven.swop.objectron.domain.square;


import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Settings;
import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.item.ActivateRequest;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.item.Teleporter;
import be.kuleuven.swop.objectron.domain.util.Observable;
import be.kuleuven.swop.objectron.domain.util.Position;

import java.util.*;


/**
 * @author : Nik Torfs
 *         Date: 22/02/13
 *         Time: 00:03
 */
public class Square implements Observable<SquareObserver> {
    private final Position position;

    private List<SquareObserver> observers = new ArrayList<>();
    private SquareState state;
    private Map<Direction, Square> neighbours = new HashMap<Direction, Square>();
    private List<Item> items = new ArrayList<Item>();
    private boolean isObstructed = false;
    private Item activeItem;
    private int powerFailureChance = Settings.POWER_FAILURE_CHANCE;

    public Square(final Position position) {
        this.position = position;
        this.state = new PoweredSquareState();
    }

    public Square(final Position position, int powerFailureChance){
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

    public void stepOn(GameState gameState) {
        state.stepOn(gameState);

        setObstructed(true);
        if(hasActiveItem()){
            activeItem.activate(new ActivateRequest(gameState));
            activeItem = null;
        }
    }

    public List<Item> getAvailableItems() {
        return Collections.unmodifiableList(items);
    }

    public void addItem(Item item) {
        this.items.add(item);
        notifyItemPlaced(item);
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
        if (this.isObstructed())
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

        return position.toString() + "\n" + "isObstructed: " + this.isObstructed();
    }

    public void transitionState(SquareState newState) {
        this.state = newState;
    }

    public void receivePowerFailure(){
        state.powerFailure(this);
        notifyPowerFailure();
    }

    private boolean losingPower(){
        int r = (int) (Math.random() * 100);
        return r < powerFailureChance;
    }

    public void newTurn(Turn currentTurn){
        if(losingPower()){
            receivePowerFailure();
            for(Square neighbour : neighbours.values()){
                neighbour.receivePowerFailure();
            }
        }
        boolean currentSquare = currentTurn.getCurrentPlayer().getCurrentSquare().equals(this);
        state.newTurn(currentTurn, currentSquare, this);
    }

    @Override
    public void attach(SquareObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detach(SquareObserver observer) {
        observers.remove(observer);
    }

    public void notifyPowerFailure(){
        for(SquareObserver observer: observers){
            observer.lostPower(this.position);
        }
    }
    public void notifyPowered(){
        for(SquareObserver observer: observers){
            observer.regainedPower(this.position);
        }
    }

    public void notifyItemPlaced(Item item){
        for(SquareObserver observer: observers){
            observer.itemPlaced(item,  this.position);
        }
    }

    public Teleporter getTeleportItem(){
        for(Item item : this.getAvailableItems()){
            if(item instanceof Teleporter)
                return (Teleporter) item;
        }
        return null;
    }
}
