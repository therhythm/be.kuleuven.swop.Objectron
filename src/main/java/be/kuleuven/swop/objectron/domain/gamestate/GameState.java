package be.kuleuven.swop.objectron.domain.gamestate;

import be.kuleuven.swop.objectron.domain.grid.GridFactory;
import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.item.Item;
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
public class GameState implements Observable<GameObserver>, SquareObserver {
    private Grid gameGrid;
    private List<Player> players = new ArrayList<Player>();
    private List<GameObserver> observers = new ArrayList<>();
    private Turn currentTurn;

    public GameState(String player1Name, String player2Name, Dimension dimension) throws GridTooSmallException{
        Position p1Pos = new Position(0, dimension.getHeight() -1);
        Position p2Pos = new Position(dimension.getWidth()-1, 0);
        this.gameGrid = GridFactory.normalGrid(dimension, p1Pos, p2Pos, this);

        Player p1 = new Player(player1Name, gameGrid.getSquareAtPosition(p1Pos));
        Player p2 = new Player(player2Name, gameGrid.getSquareAtPosition(p2Pos));

        currentTurn = new Turn(p1);
        players.add(p1);
        players.add(p2);
    }

    public GameState(String player1Name, String player2Name, Dimension dimension, Grid gameGrid) throws GridTooSmallException{
        this(player1Name,
                player2Name,
                new Position(0, dimension.getHeight() -1),
                new Position(dimension.getWidth()-1, 0),
                gameGrid);
    }

    public GameState(String player1Name, String player2Name, Position p1Pos, Position p2Pos, Grid gameGrid) throws GridTooSmallException{
        this.gameGrid = gameGrid;

        Player p1 = new Player(player1Name, gameGrid.getSquareAtPosition(p1Pos));
        Player p2 = new Player(player2Name, gameGrid.getSquareAtPosition(p2Pos));

        currentTurn = new Turn(p1);
        players.add(p1);
        players.add(p2);
    }

    public Player getCurrentPlayer() {
        return currentTurn.getCurrentPlayer(); //TODO delegate or return turn object?
    }

    public Grid getGrid() {
        return gameGrid;
    }

    public void endTurn(){
        int index = players.indexOf(currentTurn.getCurrentPlayer());
        index = (index + 1) % players.size();

        currentTurn = new Turn(players.get(index));
        gameGrid.newTurn(currentTurn);

        notifyObservers();
    }

    public void notifyObservers(){
        List<PlayerViewModel> playerVMs = new ArrayList<>();
        for(Player p : players){
            playerVMs.add(p.getPlayerViewModel());
        }
        for(GameObserver observer : observers){
            observer.update(currentTurn.getViewModel(), playerVMs);
        }
    }

    public void attach(GameObserver observer) {
        observers.add(observer);
    }

    public void detach(GameObserver observer) {
        observers.remove(observer);
    }

    public Turn getCurrentTurn(){
        return this.currentTurn;
    }

    public Item getCurrentItem() {
        return currentTurn.getCurrentItem(); //TODO delegate or return Turn object  ?
    }

    public void setCurrentItem(Item item) {
        currentTurn.setCurrentItem(item);
        notifyObservers();
    }

    public boolean checkWin(){
        Player currentPlayer = currentTurn.getCurrentPlayer();

        for(Player otherPlayer : players){
            if(!otherPlayer.equals(currentPlayer) &&
                otherPlayer.getInitialSquare().equals(currentPlayer.getCurrentSquare())){
                return true;
            }
        }
        return false;
    }

    @Override
    public void lostPower(Position position) {
        for(GameObserver observer : observers){
            observer.noPower(position);
        }
    }

    @Override
    public void regainedPower(Position position) {
        for(GameObserver observer : observers){
            observer.regainedPower(position);
        }
    }
}
