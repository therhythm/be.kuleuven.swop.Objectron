package be.kuleuven.swop.objectron.model;

/**
 * @author : Nik Torfs
 *         Date: 22/02/13
 *         Time: 00:06
 */
public class HumanPlayer implements Player {

    private Inventory inventory;
    private Item currentlySelectedItem;
    private int availableActions;
    private Square currentSquare;

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public boolean isInventoryFull() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Square getCurrentSquare() {
        return currentSquare;
    }

    @Override
    public void setCurrentSquare(Square currentSquare) {
        this.currentSquare = currentSquare;
    }

    @Override
    public void addToInventory(Item itemToAdd) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getAvailableActions() {
        return availableActions;
    }

    @Override
    public void setAvailableActions(int availableActions) {
        this.availableActions = availableActions;
    }

    @Override
    public Item getCurrentlySelectedItem() {
        return currentlySelectedItem;
    }

    public void setCurrentlySelectedItem(Item currentlySelectedItem) {
        this.currentlySelectedItem = currentlySelectedItem;
    }
}
