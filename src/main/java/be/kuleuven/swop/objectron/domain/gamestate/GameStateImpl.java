package be.kuleuven.swop.objectron.domain.gamestate;

import be.kuleuven.swop.objectron.domain.Grid;
import be.kuleuven.swop.objectron.domain.GridFactory;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * @author : Nik Torfs
 *         Date: 26/02/13
 *         Time: 21:02
 */
public class GameStateImpl implements GameState {
    private Grid gameGrid;
    private Player currentPlayer;
    private List<Player> players = new ArrayList<Player>();
    private List<GameObserver> observers = new ArrayList<>();
    private Item currentItem = null;

    public GameStateImpl(String player1Name, String player2Name, int horizontalTiles, int verticalTiles) throws GridTooSmallException{

        //gameGrid = new Grid(horizontalTiles, verticalTiles);
        int horizontalPositionPlayer1  = 0;
        int verticalPositionPlayer1 = verticalTiles - 1;
        int horizontalPositionPlayer2  =horizontalTiles - 1;
        int verticalPositionPlayer2 = 0;


        GridFactory gridFactory = new GridFactory(horizontalTiles,verticalTiles,horizontalPositionPlayer1,verticalPositionPlayer1,horizontalPositionPlayer2,verticalPositionPlayer2);
        this.gameGrid = gridFactory.getGameGrid();
        Player p1 = new Player(player1Name, gameGrid.getSquareAtPosition(verticalPositionPlayer1, horizontalPositionPlayer1));
        Player p2 = new Player(player2Name, gameGrid.getSquareAtPosition(verticalPositionPlayer2, horizontalPositionPlayer2));

       // gameGrid.buildGrid(p1.getCurrentSquare(), p2.getCurrentSquare());
        currentPlayer = p1;
        players.add(p1);
        players.add(p2);
    }

    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public Grid getGrid() {
        return gameGrid;
    }

    @Override
    public void nextPlayer() {
        int index = players.indexOf(currentPlayer);
        index = (index + 1) % players.size();
        currentPlayer = players.get(index);

        currentItem = null;
        notifyObservers();
    }

    @Override
    public void notifyObservers(){
        for(GameObserver observer : observers){
            observer.playerUpdated(currentPlayer.getPlayerViewModel());
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
    public Item getCurrentItem() {
        return currentItem;
    }

    @Override
    public void setCurrentItem(Item item) {
        currentItem = item;
        notifyObservers();
    }
}
