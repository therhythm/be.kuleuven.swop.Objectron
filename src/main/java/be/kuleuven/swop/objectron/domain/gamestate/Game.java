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

    public Game(List<Player> players,
                Grid gameGrid) {
        this.gameGrid = gameGrid;
        this.players = players;

        initializeTurnmanager();
    }

    private void initializeTurnmanager() {
        turnManager = new TurnManager(players);
        turnManager.attach(this);
        turnManager.attach(gameGrid);
        turnManager.attach(gameGrid.getForceFieldArea());
    }


    public TurnManager getTurnManager() {
        return turnManager;
    }

    public Grid getGrid() {
        return gameGrid;
    }

    public List<Player> getPlayers() {
        return players;
    }

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
    public void actionHappened(TurnObserver observable, List<Player> players) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void actionHappened(Observable<TurnSwitchObserver> observable) {

    }
}
