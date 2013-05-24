package be.kuleuven.swop.objectron.domain;

import be.kuleuven.swop.objectron.domain.effect.Effect;
import be.kuleuven.swop.objectron.domain.exception.*;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.item.EffectActivation;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.item.deployer.ItemDeployCommand;
import be.kuleuven.swop.objectron.domain.movement.Movable;
import be.kuleuven.swop.objectron.domain.movement.MovementStrategy;
import be.kuleuven.swop.objectron.domain.movement.PlayerMovementStrategy;
import be.kuleuven.swop.objectron.domain.movement.teleport.PlayerTeleportStrategy;
import be.kuleuven.swop.objectron.domain.movement.teleport.TeleportStrategy;
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
    private TeleportStrategy teleportStrategy;
    private MovementStrategy movementStrategy;
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
        this.teleportStrategy = new PlayerTeleportStrategy();
        this.incapacitaded = false;
    }

    public void setIncapacitated(boolean incapacitaded) {
        this.incapacitaded = incapacitaded;
    }

    public boolean isIncapacitaded(){
        return this.incapacitaded;
    }

    public Square getCurrentSquare() {
        return currentSquare;
    }

    public abstract void pickupItem(int identifier) throws InventoryFullException;

    /**
     * Move the player to a new position.
     * @param newPosition
     *        The new square to move the player to.
     * @param manager
     *        The TurnManager to execute the move with.
     * @throws InvalidMoveException
     *         This is an invalid move.
     * @throws GameOverException
     *         The game is over.
     *         | manager.checkWin()
     * @throws SquareOccupiedException
     *         The square is occupied.
     *         | newPosition.isOccupied()
     * @throws NotEnoughActionsException
     *         The player has not enough actions remaining.
     *         | manager.getCurrentTurn().getActionsRemaining() == 0
     */
    public void move(Square newPosition, TurnManager manager) throws InvalidMoveException, GameOverException,
            SquareOccupiedException, NotEnoughActionsException {
        actionPerformed();
        this.movementStrategy = new PlayerMovementStrategy(manager);
        if(this.isIncapacitaded()){
            throw new InvalidMoveException();
        }
        try {
            enter(newPosition, manager);
        } catch (PlayerHitException | ForceFieldHitException | WallHitException e) {
            throw new InvalidMoveException();
        }
        teleportStrategy = new PlayerTeleportStrategy();
    }

    @Override
    public void enter(Square newPosition, TurnManager manager) throws InvalidMoveException, PlayerHitException,
            WallHitException, ForceFieldHitException, GameOverException, NotEnoughActionsException,
            SquareOccupiedException {
        lightTrail.expand(currentSquare);
        currentSquare.removeObstruction(this);
        newPosition.addObstruction(this);
        currentSquare = newPosition;
        newPosition.stepOn(this, manager);
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
     * @throws NotEnoughActionsException
     *         The player has not enough actions remaining.
     *         | manager.getCurrentTurn().getActionsRemaining() == 0
     * @throws GameOverException
     *         The game is over
     *         | manager.checkWin()
     */
    public void useItem(Item item, ItemDeployCommand deployer) throws SquareOccupiedException, NotEnoughActionsException,
            GameOverException {
        deployer.deploy(item);
        inventory.removeItem(item);
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
    public TeleportStrategy getTeleportStrategy() {
        return teleportStrategy;
    }

    @Override
    public MovementStrategy getMovementStrategy() {
        return movementStrategy;
    }

    @Override
    public void hit(MovementStrategy strategy) throws InvalidMoveException, PlayerHitException {
        strategy.hitPlayer(this);
    }

    @Override
    public void dirsupted() {
        EffectActivation activation = new EffectActivation(this);
        List<Item> inventoryCopy = new ArrayList<Item>();
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


}
