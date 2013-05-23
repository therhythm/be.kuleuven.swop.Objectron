package be.kuleuven.swop.objectron.domain.gamestate;

import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.util.Observable;

import java.util.*;

/**
 * @author : Nik Torfs
 *         Date: 30/04/13
 *         Time: 00:16
 */
public class TurnManager implements Observable<TurnSwitchObserver>, TurnObserver {
    private Turn currentTurn;
    private List<Player> players;
    private Set<TurnSwitchObserver> observers = new HashSet<>();


    public TurnManager(List<Player> players) {
        this.currentTurn = new Turn(players.get(0));
        this.players = players;
        this.currentTurn.attach(this);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Turn getCurrentTurn() {
        return currentTurn;
    }

    public void endTurn() {
        int index = players.indexOf(currentTurn.getCurrentPlayer());
        index = (index + 1) % players.size();

        Turn newTurn = new Turn(players.get(index));
        newTurn.attach(this);

        checkMoved();

        currentTurn = newTurn;
        notifyObservers();
        update(currentTurn);
    }

    private void checkMoved() {
        if (!currentTurn.hasMoved())
            this.players.remove(currentTurn.getCurrentPlayer());
    }

    public boolean checkWin() {
        return getPlayers().size() <= 1;
    }

    private void notifyObservers() {
        Set<TurnSwitchObserver> copyOfSet = new HashSet<>(observers);
        for (TurnSwitchObserver observer: copyOfSet) {
            observer.turnEnded(this);
        }
    }

    @Override
    public void attach(TurnSwitchObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detach(TurnSwitchObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void update(Turn turn) {
        for (TurnSwitchObserver observer : observers) {
            observer.update(turn);
        }
    }

    @Override
    public void penaltyAdded() {
        for (TurnSwitchObserver observer : observers) {
            observer.actionReduced();
        }
    }

    @Override
    public void actionReduced() {
        Set<TurnSwitchObserver> copyOfSet = new HashSet<>(observers);
        for (TurnSwitchObserver observer : copyOfSet) {
           // observer.actionReduced(this, players);
            observer.actionHappened(this,players);
            observer.actionHappened(this);
        }
    }

    @Override
    public void killPlayer(Player player) {
        this.players.remove(player);
    }
}
