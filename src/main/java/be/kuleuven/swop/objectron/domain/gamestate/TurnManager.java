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
    private Game game;
    private Turn currentTurn;
    private List<Player> players;
    private Set<TurnSwitchObserver> observers = new HashSet<>();

        /**
         * Initializes a new TurnManager with a given list of players
         *
         * @param game the game of the turnmanager
         * @param players The list of players
         * @post  The list of players is equal to the given list
         *        |this.players == players
         */
    public TurnManager(Game game, List<Player> players) {
        this.currentTurn = new Turn(players.get(0));
        this.players = players;
        this.currentTurn.attach(this);
        this.game = game;
    }

    /**
     * Returns the list of players of the turn manager
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Returns the current turn
     */
    public Turn getCurrentTurn() {
        return currentTurn;
    }

    /**
     * Ends the current turn, and starts a new turn with the next player
     */
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

    /**
     * Checks if the player has moved and removes him if he hasn't moved
     */
    private void checkMoved() {
        if (!currentTurn.hasMoved() && !currentTurn.getCurrentPlayer().isIncapacitated())
            this.players.remove(currentTurn.getCurrentPlayer());
    }

    /**
     * Checks if there is 1 player left.
     */
    public boolean checkWin() {
        return getPlayers().size() <= 1;
    }

    /**
     * notify observers that the turn has ended
     */
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
            observer.actionHappened(this);
        }
    }

    /**
     * Kills a player by removing him from the list
     */
    public void killPlayer(Player player) {
        this.players.remove(player);
    }

    public void gameWon() {
        game.gameWon(currentTurn.getCurrentPlayer());
    }
}
