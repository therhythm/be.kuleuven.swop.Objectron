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
    public static final int ACTIONS_EACH_TURN = 3;

    private Player currentPlayer;
    private Item currentItem;
    private int actionsRemaining;
    private boolean hasMoved;
    private Set<TurnObserver> observers = new HashSet<>();

    public Turn(Player player) {
        this.currentPlayer = player;
        //TODO what if there are more penalties than remaining actions
        this.actionsRemaining = ACTIONS_EACH_TURN - player.getRemainingPenalties();
        player.reduceRemainingPenalties(ACTIONS_EACH_TURN);
        this.hasMoved = false;
        this.currentItem = null;
    }

    public void extraTurn() { //todo could be replaced by adding penalties to the player instead
        this.actionsRemaining += (ACTIONS_EACH_TURN - currentPlayer.getRemainingPenalties());
    }

    public int getActionsRemaining() {
        return actionsRemaining;
    }

    public void setCurrentItem(Item item) {
        this.currentItem = item;
        notifyObservers();
    }

    public Item getCurrentItem() {
        return currentItem;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setMoved() {
        this.hasMoved = true;
        notifyObservers();
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void reduceRemainingActions(int amount) {
        if (actionsRemaining > amount) {
            actionsRemaining -= amount;
        } else {
            currentPlayer.addRemainingPenalties(amount - actionsRemaining);
            actionsRemaining = 0;
            hasMoved = true;
        }
        notifyObservers();
    }

    public void checkEnoughActions() throws NotEnoughActionsException {
        if (actionsRemaining == 0) {
            throw new NotEnoughActionsException("You can't do any actions anymore, end the turn!");
        }
    }

    public TurnViewModel getViewModel() {
        return new TurnViewModel(actionsRemaining, currentPlayer.getPlayerViewModel(), currentItem);
    }

    private void notifyObservers() {
        for(TurnObserver observer : observers){
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
