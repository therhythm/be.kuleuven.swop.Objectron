package be.kuleuven.swop.objectron.domain.gamestate;

import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.grid.GridFactory;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.item.forceField.ForceFieldArea;
import be.kuleuven.swop.objectron.domain.square.SquareObserver;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.domain.util.Observable;
import be.kuleuven.swop.objectron.domain.util.Position;
import be.kuleuven.swop.objectron.viewmodel.PlayerViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Nik Torfs
 *         Date: 26/02/13
 *         Time: 21:02
 */
public class GameState implements Observable<GameObserver>, SquareObserver, TurnSwitchObserver {
    private Grid gameGrid;
    private List<Player> players = new ArrayList<Player>();
    private List<GameObserver> observers = new ArrayList<>();
    private TurnManager turnManager;

    public GameState(String player1Name, String player2Name, Dimension dimension) throws GridTooSmallException {
        Position p1Pos = new Position(0, dimension.getHeight() - 1);
        Position p2Pos = new Position(dimension.getWidth() - 1, 0);
        this.gameGrid = GridFactory.normalGrid(dimension, p1Pos, p2Pos, this);

        players.add(new Player(player1Name, gameGrid.getSquareAtPosition(p1Pos)));
        players.add(new Player(player2Name, gameGrid.getSquareAtPosition(p2Pos)));

        turnManager = new TurnManager(players);
        turnManager.attach(this);
        turnManager.attach(gameGrid);
        turnManager.attach(gameGrid.getForceFieldArea());
    }

    public GameState(String player1Name, String player2Name, Dimension dimension,
                     Grid gameGrid) throws GridTooSmallException {
        this(player1Name,
                player2Name,
                new Position(0, dimension.getHeight() - 1),
                new Position(dimension.getWidth() - 1, 0),
                gameGrid);
    }

    public GameState(String player1Name, String player2Name, Position p1Pos, Position p2Pos,
                     Grid gameGrid) throws GridTooSmallException {
        this.gameGrid = gameGrid;

        players.add(new Player(player1Name, gameGrid.getSquareAtPosition(p1Pos)));
        players.add(new Player(player2Name, gameGrid.getSquareAtPosition(p2Pos)));

        turnManager = new TurnManager(players);
        turnManager.attach(this);
        turnManager.attach(gameGrid);
        turnManager.attach(getGrid().getForceFieldArea());
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
            observer.update(turnManager.getCurrentTurn().getViewModel(), playerVMs);
        }
    }

    public boolean checkWin() {
        Player currentPlayer = turnManager.getCurrentTurn().getCurrentPlayer();

        for (Player otherPlayer : players) {
            if (!otherPlayer.equals(currentPlayer) &&
                    otherPlayer.getInitialSquare().equals(currentPlayer.getCurrentSquare())) {
                return true;
            }
        }
        return false;
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
    public void lostPower(Position position) {
        for (GameObserver observer : observers) {
            observer.noPower(position);
        }
    }

    @Override
    public void regainedPower(Position position) {
        for (GameObserver observer : observers) {
            observer.regainedPower(position);
        }
    }

    @Override //todo itemviewmodel
    public void itemPlaced(Item item, Position position) {
        for (GameObserver observer : observers) {
            observer.itemPlaced(item, position);
        }
    }

    @Override
    public void turnEnded(Turn newTurn) {
        //todo UI message?
    }

    @Override
    public void update(Turn turn) {
        notifyObservers();
    }
}
