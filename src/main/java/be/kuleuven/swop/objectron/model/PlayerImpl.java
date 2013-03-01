package be.kuleuven.swop.objectron.model;

import be.kuleuven.swop.objectron.model.item.Item;
import be.kuleuven.swop.objectron.model.item.NullItem;
import be.kuleuven.swop.objectron.viewmodel.PlayerViewModel;

import java.util.List;

/**
 * @author : Nik Torfs
 *         Date: 22/02/13
 *         Time: 00:06
 */
public class PlayerImpl implements Player {
    private static final int NB_ACTIONS_EACH_TURN = 3;
    private static final int NB_ACTIONS_BLINDED = 3;

    private String name;
    private Square currentSquare;
    private Item currentlySelectedItem = new NullItem();
    private int availableActions = NB_ACTIONS_EACH_TURN;
    private LightTrail lightTrail = new LightTrail();
    private Inventory inventory = new InventoryImpl();
    private boolean hasMoved;
    private int remainingActionsBlinded = 0;

    public PlayerImpl(String name, Square currentSquare) {
        this.name = name;
        this.currentSquare = currentSquare;
        currentSquare.setObstructed(true);
    }

    @Override
    public Square getCurrentSquare() {
        return currentSquare;
    }

    @Override
    public void addToInventory(Item itemToAdd) throws InventoryFullException, NotEnoughActionsException {
        checkEnoughActions();
        this.inventory.addItem(itemToAdd);
    }

    @Override
    public void move(Square newPosition) throws NotEnoughActionsException {
        checkEnoughActions();
        reduceAvailableActions();
        lightTrail.expand(currentSquare);
        currentSquare = newPosition;
        currentSquare.stepOn(this);
        hasMoved = true;
    }

    private void checkEnoughActions() throws NotEnoughActionsException {
        if (availableActions==0) {
            throw new NotEnoughActionsException("You can't do any actions anymore, end the turn!");
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getAvailableActions() {
        return availableActions;
    }

    @Override
    public Item getCurrentlySelectedItem() {
        return currentlySelectedItem;
    }

    @Override
    public List<Item> getInventoryItems() {
        return inventory.getItems();
    }

    @Override
    public Item getInventoryItem(int identifier) {
        return inventory.retrieveItem(identifier);
    }

    @Override
    public void setCurrentlySelectedItem(Item currentlySelectedItem) {
        this.currentlySelectedItem = currentlySelectedItem;
    }

    @Override
    public void useCurrentItem() throws SquareOccupiedException, NotEnoughActionsException {
        checkEnoughActions();
        currentlySelectedItem.use(currentSquare);
        inventory.removeItem(currentlySelectedItem);

        reduceAvailableActions();
    }

    @Override
    public void endTurn() {
        availableActions = NB_ACTIONS_EACH_TURN - remainingActionsBlinded;
        hasMoved = false;
    }

    @Override
    public boolean hasMoved() {
        return hasMoved;
    }

    private void reduceAvailableActions() {
        availableActions--;
        lightTrail.reduce();
    }

    @Override
    public void blind() {
        remainingActionsBlinded = NB_ACTIONS_BLINDED - availableActions;
        endTurn();
    }

    @Override
    public PlayerViewModel getPlayerViewModel() {
        //TODO positioning
        return new PlayerViewModel(getName(), 0 ,0 , getAvailableActions(), getCurrentlySelectedItem().getSpecification());
    }
}
