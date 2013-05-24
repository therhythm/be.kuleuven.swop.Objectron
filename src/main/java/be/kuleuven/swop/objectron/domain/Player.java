package be.kuleuven.swop.objectron.domain;

import be.kuleuven.swop.objectron.domain.exception.*;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.item.EffectActivation;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.item.deployer.ItemDeployCommand;
import be.kuleuven.swop.objectron.domain.movement.*;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.viewmodel.PlayerViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A class of Players implementing Movable and Obstruction.
 * @author : Nik Torfs
 *         Date: 22/02/13
 *         Time: 00:06
 */
public abstract class Player implements Movable, Obstruction {
    private String name;
    private Square currentSquare;
    private LightTrail lightTrail = new LightTrail();
    protected Inventory inventory = new Inventory();
    private int remainingPenalties;
    private boolean incapacitaded;

    /**
     * Initialize this Player with a given name and Square.
     * @param name
     *        The name of this player.
     * @param currentSquare
     *        The square of this player.
     * @post  This Player is initialized with the given name and Square.
     *        | new.this.getName() == name
     *        | new.this.getCurrentSquare() = currentSquare
     */
    public Player(String name, Square currentSquare) {
        this.name = name;
        this.currentSquare = currentSquare;
        currentSquare.addObstruction(this);
        this.incapacitaded = false;
    }

    public void setIncapacitated(boolean incapacitaded) {
        this.incapacitaded = incapacitaded;
    }

    public boolean isIncapacitated(){
        return this.incapacitaded;
    }

    public Square getCurrentSquare() {
        return currentSquare;
    }

    public abstract void pickupItem(int identifier) throws InventoryFullException;

    /**
     * Move the player to a new position.
     * @param direction
     *        the direction to move in
     * @param manager
     *        The TurnManager to execute the move with.
     * @throws InvalidMoveException
     *         This is an invalid move.
     */
    public void move(Direction direction, TurnManager manager) throws InvalidMoveException {

        actionPerformed();
        if(this.isIncapacitated()){
            throw new InvalidMoveException();
        }

        Movement movement = new Movement(this, direction, currentSquare, new NormalMovementRangeStrategy(1), manager);
        movement.move();
        lightTrail.expand(currentSquare);
        currentSquare.removeObstruction(this);

        currentSquare = movement.getCurrentSquare();
        currentSquare.addObstruction(this);
    }

    public String getName() {
        return this.name;
    }

    public List<Item> getInventoryItems() {
        return inventory.getAllItems();
    }

    public Item getInventoryItem(int identifier) {
        return inventory.retrieveItem(identifier);
    }

    /**
     * Use a given item.
     * @param item
     *        The item to use.
     * @param deployer
     *        The ItemDeployCommand to use the item with.
     * @throws SquareOccupiedException
     *         The square is occupied.
     *         | newPosition.isOccupied()
    */
    public void useItem(Item item, ItemDeployCommand deployer) throws SquareOccupiedException{

        deployer.deploy(item);

        removeItem(item);

        actionPerformed();
    }

    protected void actionPerformed() {
        lightTrail.reduce();
    }

    public PlayerViewModel getPlayerViewModel() {
        return new PlayerViewModel(getName(),
                currentSquare.getPosition(),
                lightTrail.getLightTrailViewModel());
    }

    public int getRemainingPenalties() {
        return remainingPenalties;
    }

    public void addRemainingPenalties(int remainingPenalties) {
        this.remainingPenalties += remainingPenalties;
    }

    public void reduceRemainingPenalties(int playerActionsEachTurn) {
        this.remainingPenalties -= playerActionsEachTurn;
        if (this.remainingPenalties < 0) {
            this.remainingPenalties = 0;
        }
    }

    @Override
    public void hit(Movement movement) throws InvalidMoveException {
        movement.hitPlayer(this);
    }

    @Override
    public void disrupted() {
        EffectActivation activation = new EffectActivation(this);
        List<Item> inventoryCopy = new ArrayList<>();
        inventoryCopy.addAll(inventory.getAllItems());
        for (Item item : inventoryCopy) {
            item.effectActivated(activation);
        }
    }

    /**
     * Randomly drop a given item.
     * @param item
     *        The item to drop.
     */
    public void randomlyDrop(Item item) {
        inventory.removeItem(item);

        Direction[] directions = Direction.values();
        Random random = new Random();
        int randomDirection = random.nextInt(directions.length);
        Square randomSquare = getCurrentSquare().getNeighbour(directions[randomDirection]);
        while (randomSquare == null || randomSquare.isObstructed()) {
            randomDirection = random.nextInt(directions.length);
            randomSquare = getCurrentSquare().getNeighbour(directions[randomDirection]);
        }

        randomSquare.addItem(item);

    }


    public void removeItem(Item item){
        inventory.removeItem(item);
    }
}
