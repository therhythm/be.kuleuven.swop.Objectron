package be.kuleuven.swop.objectron.domain.gamestate;

import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.gamestate.gamemode.GameMode;
import be.kuleuven.swop.objectron.domain.gamestate.gamemode.RaceMode;
import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.grid.GridFactory;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.item.effect.RaceFinish;
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
    private GameMode gamemode;

    public GameState(List<String> playerNames, Dimension dimension) throws GridTooSmallException {
        List<Position> positions = new ArrayList<Position>();

        positions.add(new Position(0, dimension.getHeight() - 1));
        positions.add(new Position(dimension.getWidth() - 1, 0));
        positions.add(new Position(dimension.getWidth() - 1, dimension.getHeight() - 1));
        positions.add(new Position(0, 0));


        this.gameGrid = GridFactory.normalGrid(dimension, positions.subList(0, playerNames.size()), this);

        for (int i = 0; i < playerNames.size(); i++) {
            players.add(new Player(playerNames.get(i), gameGrid.getSquareAtPosition(positions.get(i))));
        }

        gamemode = new RaceMode();
        gamemode.initialize(players);

        initializeTurnmanager();
    }

    //Todo remove constructor above if this is working and properly implemented
    public GameState(List<String> playerNames, Dimension dimension, GameMode gameMode) throws GridTooSmallException {
        List<Position> positions = new ArrayList<Position>();

        positions.add(new Position(0, dimension.getHeight() - 1));
        positions.add(new Position(dimension.getWidth() - 1, 0));
        positions.add(new Position(dimension.getWidth() - 1, dimension.getHeight() - 1));
        positions.add(new Position(0, 0));


        this.gameGrid = GridFactory.normalGrid(dimension, positions.subList(0, playerNames.size()), this);

        for (int i = 0; i < playerNames.size(); i++) {
            players.add(new Player(playerNames.get(i), gameGrid.getSquareAtPosition(positions.get(i))));
        }

        this.gamemode = gameMode;
        gamemode.initialize(players);

        initializeTurnmanager();
    }

    //Todo deze cunstructor mag weg als constructor erboven overal werkt
  /*  public GameState(String player1Name, String player2Name, Dimension dimension) throws GridTooSmallException {
        Position p1Pos = new Position(0, dimension.getHeight() - 1);
        Position p2Pos = new Position(dimension.getWidth() - 1, 0);

        List<Position> positions = new ArrayList<Position>();

        positions.add(p1Pos);
        positions.add(p2Pos);

        this.gameGrid = GridFactory.normalGrid(dimension, positions, this);

        players.add(new Player(player1Name, gameGrid.getSquareAtPosition(p1Pos)));
        players.add(new Player(player2Name, gameGrid.getSquareAtPosition(p2Pos)));

        for (Player player : players) {
            gameGrid.getSquareAtPosition(player.getCurrentSquare().getPosition()).addEffect(new RaceFinish(player));
        }

        initializeTurnmanager();
    }*/

    private void initializeTurnmanager() {
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

    //Todo mag weg als constructor onder deze werkt en alle testen zijn aangepast aan die constructor
    public GameState(String player1Name, String player2Name, Position p1Pos, Position p2Pos,
                     Grid gameGrid) throws GridTooSmallException {
        this.gameGrid = gameGrid;

        players.add(new Player(player1Name, gameGrid.getSquareAtPosition(p1Pos)));
        players.add(new Player(player2Name, gameGrid.getSquareAtPosition(p2Pos)));

        for (Player player : players) {
            gameGrid.getSquareAtPosition(player.getCurrentSquare().getPosition()).addEffect(new RaceFinish(player));
        }
        this.gamemode = new RaceMode();
        gamemode.initialize(players);

        turnManager = new TurnManager(players);
        turnManager.attach(this);
        turnManager.attach(gameGrid);
        turnManager.attach(getGrid().getForceFieldArea());
    }

    public GameState(List<String> playerNames, List<Position> positions,
                     Grid gameGrid, GameMode gameMode) throws GridTooSmallException {
        this.gameGrid = gameGrid;
        this.gamemode = gameMode;



        for (int i = 0; i < playerNames.size(); i++) {
            players.add(new Player(playerNames.get(i), gameGrid.getSquareAtPosition(positions.get(i))));
        }

        gamemode.initialize(players);

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
          /*
        for (Player otherPlayer : players) {
            if (!otherPlayer.equals(currentPlayer) &&
                    otherPlayer.getInitialSquare().equals(currentPlayer.getCurrentSquare())) {
                return true;
            }
        }          */
        return false;
    }

    public void endAction() {
        gameGrid.endAction();
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

    @Override
    public void actionReduced() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
