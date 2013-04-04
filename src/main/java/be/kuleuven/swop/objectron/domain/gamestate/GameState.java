package be.kuleuven.swop.objectron.domain.gamestate;

import be.kuleuven.swop.objectron.domain.grid.GridFactory;
import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.domain.util.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Nik Torfs
 *         Date: 26/02/13
 *         Time: 21:02
 */
public class GameState implements GameObservable {
    private Grid gameGrid;
    private Player currentPlayer;
    private List<Player> players = new ArrayList<Player>();
    private List<GameObserver> observers = new ArrayList<>();
    private Item currentItem = null;

    public GameState(String player1Name, String player2Name, Dimension dimension) throws GridTooSmallException{

        Position p1Pos = new Position(0, dimension.getHeight() -1);
        Position p2Pos = new Position(dimension.getWidth()-1, 0);
        this.gameGrid = GridFactory.normalGrid(dimension, p1Pos, p2Pos);

        Player p1 = new Player(player1Name, gameGrid.getSquareAtPosition(p1Pos));
        Player p2 = new Player(player2Name, gameGrid.getSquareAtPosition(p2Pos));

        currentPlayer = p1;
        players.add(p1);
        players.add(p2);
    }

    public GameState(String player1Name, String player2Name, Dimension dimension, Grid gameGrid) throws GridTooSmallException{
        Position p1Pos = new Position(0, dimension.getHeight() -1);
        Position p2Pos = new Position(dimension.getWidth()-1, 0);

        this.gameGrid = gameGrid;

        Player p1 = new Player(player1Name, gameGrid.getSquareAtPosition(p1Pos));
        Player p2 = new Player(player2Name, gameGrid.getSquareAtPosition(p2Pos));

        currentPlayer = p1;
        players.add(p1);
        players.add(p2);
    }

    public GameState(String player1Name, String player2Name, Position p1Pos, Position p2Pos, Grid gameGrid) throws GridTooSmallException{
        this.gameGrid = gameGrid;

        Player p1 = new Player(player1Name, gameGrid.getSquareAtPosition(p1Pos));
        Player p2 = new Player(player2Name, gameGrid.getSquareAtPosition(p2Pos));

        currentPlayer = p1;
        players.add(p1);
        players.add(p2);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Grid getGrid() {
        return gameGrid;
    }

    public void nextPlayer() {
        int index = players.indexOf(currentPlayer);
        index = (index + 1) % players.size();
        currentPlayer = players.get(index);

        currentItem = null;
        notifyObservers();
    }

    public void notifyObservers(){
        for(GameObserver observer : observers){
            observer.playerUpdated(currentPlayer.getPlayerViewModel());
        }
    }

    public void attach(GameObserver observer) {
        observers.add(observer);
    }

    public void detach(GameObserver observer) {
        observers.remove(observer);
    }

    public Item getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(Item item) {
        currentItem = item;
        notifyObservers();
    }
}
