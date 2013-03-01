package be.kuleuven.swop.objectron.model;

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
    private Item currentlySelectedItem = null;
    private int availableActions = NB_ACTIONS_EACH_TURN;
    private LightTrail lightTrail;
    private Inventory inventory;
    private boolean hasMoved;
    private boolean isBlinded;
    private int remainingActionsBlinded;

    public PlayerImpl(String name, Square currentSquare) {
        this.name = name;
        this.currentSquare = currentSquare;
        currentSquare.setObstructed(true);
        lightTrail = new LightTrail();
        inventory = new InventoryImpl();
        hasMoved = false;
        isBlinded = false;
        remainingActionsBlinded = 0;
    }

    @Override
    public Square getCurrentSquare() {
        return currentSquare;
    }

    @Override
    public void addToInventory(Item itemToAdd) throws InventoryFullException, NotEnoughActionsException {
        checkEnoughActions();
        this.inventory.addItem(itemToAdd);
        reduceAvailableActions();
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
        isBlinded = true;
        remainingActionsBlinded = NB_ACTIONS_BLINDED - availableActions;
        endTurn();
    }

    @Override
    public boolean isBlinded() {
        return isBlinded;
    }
}
