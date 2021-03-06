package be.kuleuven.swop.objectron.domain.gamestate;

import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.util.Observable;
import be.kuleuven.swop.objectron.viewmodel.TurnViewModel;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Nik
 * Date: 4/8/13
 * Time: 1:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class Turn implements Observable<TurnObserver> {
    public static final int ACTIONS_EACH_TURN = 4;

    private Player currentPlayer;
    private Item currentItem;
    private int actionsRemaining;
    private boolean hasMoved;
    private Set<TurnObserver> observers = new HashSet<>();

    /**
     * Initialize a new Turn with a given player
     *
     * @param player The player who is playing this turn
     * @post  The turn is initiated with the given player
     *        |this.currentPlayer == player
     */
    public Turn(Player player) {
        this.currentPlayer = player;
        //TODO what if there are more penalties than remaining actions
        this.actionsRemaining = ACTIONS_EACH_TURN - player.getRemainingPenalties();
        player.reduceRemainingPenalties(ACTIONS_EACH_TURN);
        this.hasMoved = false;
        this.currentItem = null;
    }

    /**
     * Gives an extra turn to the player
     */
    public void extraTurn() { //todo could be replaced by adding penalties to the player instead
        this.actionsRemaining += (ACTIONS_EACH_TURN - currentPlayer.getRemainingPenalties());
    }

    /**
     * Returns the remaining actions for this turn
     */
    public int getActionsRemaining() {
        return actionsRemaining;
    }

    /**
     * Sets a given item as the selected item
     * @param item The item to elect
     */
    public void setCurrentItem(Item item) {
        this.currentItem = item;
        notifyObservers();
    }

    /**
     * Returns the current selected item
     */
    public Item getCurrentItem() {
        return currentItem;
    }

    /**
     * Returns the player of this turn
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Sets the moved flag
     */
    public void setMoved() {
        this.hasMoved = true;
        notifyObservers();
    }

    /**
     * Returns if the player has moved this turn or not
     */
    public boolean hasMoved() {
        return hasMoved;
    }

    /**
     * Reduces 1 Action
     */
    public void reduceAction(){
       actionsRemaining -= 1;
       notifyActionsReduced();
       notifyObservers();
    }

    /**
     * Adds a certain amount of penalties
     * @param amount the amount of actions that the player of this turn is penalised
     */
    public void addPenalty(int amount) {
        if (actionsRemaining > amount) {
            actionsRemaining -= amount;
        } else {
            currentPlayer.addRemainingPenalties(amount - actionsRemaining);
            actionsRemaining = 0;
            hasMoved = true;
        }
        notifyPenaltyAdded();
        notifyObservers();
    }

    /**
     * Notifies the observers that a player has performed an action
     */
    private void notifyActionsReduced() {
        for (TurnObserver observer : observers) {
            observer.actionReduced();
        }
    }

    /**
     * Notifies the obervers that there has been added a penalty
     */
    private void notifyPenaltyAdded() {
        for(TurnObserver observer : observers){
            observer.penaltyAdded();
        }
    }

    /**
     * Checks if there are enough actions left
     * @throws NotEnoughActionsException
     *         There aren't enough actions left
     *         |this.actionsRemaining == 0
     */
    public void checkEnoughActions() throws NotEnoughActionsException {
        if (actionsRemaining == 0) {
            throw new NotEnoughActionsException("You can't do any actions anymore, end the turn!");
        }
    }

    /**
     * Returns the view model of this turn
     */
    public TurnViewModel getViewModel() {
        return new TurnViewModel(actionsRemaining, currentPlayer.getPlayerViewModel(), currentItem);
    }

    /**
     * Notifies the obeservers that something has changed
     */
    private void notifyObservers() {
        for (TurnObserver observer : observers) {
            observer.update(this);
        }
    }

    @Override
    public void attach(TurnObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void detach(TurnObserver observer) {
        this.observers.remove(observer);
    }

    @Override
    public String toString() {
        String result = "";
        result += currentPlayer + "\n";
        result += currentItem + "\n";
        result += "remaining actions: " + getActionsRemaining() + "\n";
        result += "has moved: " + hasMoved;

        return result;
    }
}
