package be.kuleuven.swop.objectron.domain.gamestate;

import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.util.Observable;
import be.kuleuven.swop.objectron.viewmodel.PlayerViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Nik
 * Date: 5/17/13
 * Time: 4:25 PM
 */
public class Game implements TurnSwitchObserver, Observable<GameObserver> {
    private Grid gameGrid;
    private List<Player> players = new ArrayList<>();
    private List<GameObserver> observers = new ArrayList<>();
    private TurnManager turnManager;

    /**
     * Initializes a new Game with a given list of plaers and a given grid
     *
     * @param players The list of players who participate in the game
     * @param gameGrid The grid on which the game is going to be played
     *
     * @post The players of the new game are equal to the given list of players
     *       |this.players == players
     * @post The grid is equal to the given grid
     *       |this.gameGrid = gameGrid
     *
     */
    public Game(List<Player> players,
                Grid gameGrid) {
        this.gameGrid = gameGrid;
        this.players = players;

        initializeTurnmanager();
    }

    /**
     * Initializes a new TurnManager with the objects that the constructor received
     */
    private void initializeTurnmanager() {
        turnManager = new TurnManager(this, players);
        turnManager.attach(this);
        turnManager.attach(gameGrid);
        turnManager.attach(gameGrid.getForceFieldArea());
    }

    /**
     * Returns the Turnmanager of the game
     */
    public TurnManager getTurnManager() {
        return turnManager;
    }

    /**
     * Returns the grid of the game
     */
    public Grid getGrid() {
        return gameGrid;
    }

    /**
     * Returns the list of players of the game
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Notifies the observers with the changes in the game
     */
    public void notifyObservers() {
        List<PlayerViewModel> playerVMs = new ArrayList<>();
        for (Player p : players) {
            playerVMs.add(p.getPlayerViewModel());
        }
        for (GameObserver observer : observers) {
            observer.update(turnManager.getCurrentTurn().getViewModel(), playerVMs, getGrid().getViewModel());
        }
    }

    @Override
    public void attach(GameObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detach(GameObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void turnEnded(Observable<TurnSwitchObserver> observable) {
        //todo UI message?
    }

    @Override
    public void update(Turn turn) {
        notifyObservers();
    }

    @Override
    public void actionReduced() {
        //donothing
    }

    @Override
    public void actionHappened(TurnManager turnManager) {
        // do nothing
    }

    public void gameWon(Player currentPlayer) {
        for(GameObserver observer : observers){
            observer.gameWon(currentPlayer.getPlayerViewModel());
        }
    }
}
