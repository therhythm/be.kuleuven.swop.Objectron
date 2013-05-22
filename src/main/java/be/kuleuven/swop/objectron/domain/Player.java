package be.kuleuven.swop.objectron.domain;

import be.kuleuven.swop.objectron.domain.exception.*;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.item.deployer.ItemDeployCommand;
import be.kuleuven.swop.objectron.domain.movement.Movable;
import be.kuleuven.swop.objectron.domain.movement.MovementStrategy;
import be.kuleuven.swop.objectron.domain.movement.PlayerMovementStrategy;
import be.kuleuven.swop.objectron.domain.movement.teleport.PlayerTeleportStrategy;
import be.kuleuven.swop.objectron.domain.movement.teleport.TeleportStrategy;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.viewmodel.PlayerViewModel;

import java.util.List;

/**
 * @author : Nik Torfs
 *         Date: 22/02/13
 *         Time: 00:06
 */
public class Player implements Movable, Obstruction {
    private String name;
    private Square currentSquare;
    private LightTrail lightTrail = new LightTrail();
    private Inventory inventory = new Inventory();
    private int remainingPenalties;
    private TeleportStrategy teleportStrategy;
    private MovementStrategy movementStrategy;

    public Player(String name, Square currentSquare) {
        this.name = name;
        this.currentSquare = currentSquare;
        currentSquare.addObstruction(this);
        this.teleportStrategy = new PlayerTeleportStrategy();
    }

    public Square getCurrentSquare() {
        return currentSquare;
    }

    public void pickupItem(int identifier) throws InventoryFullException {
        Item item = currentSquare.pickUpItem(identifier);

        try {
            this.inventory.addItem(item);
        } catch (InventoryFullException ex) {
            currentSquare.addItem(item);
            throw ex;
        } catch (TooManyItemsOfSameTypeException e) {
            currentSquare.addItem(item);
        }
        actionPerformed();
    }

    public void move(Square newPosition, TurnManager manager) throws InvalidMoveException, GameOverException,
            SquareOccupiedException, NotEnoughActionsException {
        actionPerformed();
        this.movementStrategy = new PlayerMovementStrategy(manager);
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

    public void useItem(Item item, ItemDeployCommand deployer) throws SquareOccupiedException, NotEnoughActionsException,
            GameOverException {

        deployer.deploy(item);

        inventory.removeItem(item);

        actionPerformed();
    }

    private void actionPerformed() {
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
}
