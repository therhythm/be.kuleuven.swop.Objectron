package be.kuleuven.swop.objectron.domain.gamestate;

import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.square.SquareObserver;
import be.kuleuven.swop.objectron.domain.util.Observable;
import be.kuleuven.swop.objectron.domain.util.Position;
import be.kuleuven.swop.objectron.viewmodel.PlayerViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Nik
 * Date: 5/17/13
 * Time: 4:25 PM
 */
public abstract class Game implements SquareObserver, TurnSwitchObserver, Observable<GameObserver> {
    private Grid gameGrid;
    private List<Player> players = new ArrayList<Player>();
    private List<GameObserver> observers = new ArrayList<>();
    private TurnManager turnManager;

    public Game(List<String> playerNames,
                Grid gameGrid) {
        this.gameGrid = gameGrid;

        // TODO fix dependencies in gamebuilder
        List<Square> playerPositions = gameGrid.getPlayerPositions();
        for (int i = 0; i < playerNames.size(); i++) {
            players.add(new Player(playerNames.get(i), playerPositions.get(i)));
        }


        initialize(players);
        initializeTurnmanager();
    }

    protected abstract void initialize(List<Player> players);

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

    @Override //todo itemviewmodel
    public void itemPlaced(Item item, Position position) {
        for (GameObserver observer : observers) {
            observer.itemPlaced(item, position);
        }
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
    public void actionHappened(Observable<TurnSwitchObserver> observable) {
        //donothing
    }
}
