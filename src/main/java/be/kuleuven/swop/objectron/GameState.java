package be.kuleuven.swop.objectron;

import be.kuleuven.swop.objectron.gui.GameView;
import be.kuleuven.swop.objectron.model.Grid;
import be.kuleuven.swop.objectron.model.HumanPlayer;
import be.kuleuven.swop.objectron.model.Player;
import be.kuleuven.swop.objectron.model.listener.GameEventListener;
import be.kuleuven.swop.objectron.model.listener.GridEventListener;
import be.kuleuven.swop.objectron.model.listener.PlayerEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Nik Torfs
 *         Date: 26/02/13
 *         Time: 21:02
 */
public class GameState {

    private Grid gameGrid;
    private Player currentPlayer;
    private List<Player> players = new ArrayList<Player>();

    private List<GameEventListener> listeners = new ArrayList<GameEventListener>();

    public GameState(String player1Name, String player2Name, int horizontalTiles, int verticalTiles) {

        gameGrid = new Grid(horizontalTiles, verticalTiles);
        gameGrid.initializeGrid();
        Player p1 = new HumanPlayer(player1Name, null/*TODO after grid is generated*/);
        Player p2 = new HumanPlayer(player2Name, null/*TODO after grid is generated*/);
        currentPlayer = p1;
        players.add(p1);
        players.add(p2);
    }

    public void addGameEventListener(GameEventListener listener) {
        listeners.add(listener);
    }

    public void removeGameEventListener(GameEventListener listener){
        listeners.remove(listener);
    }

    // just delegating listener to grid TODO is this the right way?
    public void addGridEventListener(GridEventListener listener) {
        gameGrid.addGridEventListener(listener);
    }

    public void removeGridEventListener(GridEventListener listener){
        gameGrid.removeGridEventListener(listener);
    }

    // just delegating listener to player TODO is this the right way?
    public void addPlayerEventListener(PlayerEventListener listener) {
        for(Player p : players){
            p.addPlayerEventListener(listener);
        }
    }

    public void removePlayerEventListener(PlayerEventListener listener){
        for(Player p : players){
            p.removePlayerEventListener(listener);
        }
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }


    public Grid getGrid() {
        return gameGrid;
    }

    public void switchContext(){
        currentPlayer.endTurn();
        nextPlayer();
    }

    private void nextPlayer(){
        int index = players.indexOf(currentPlayer);
        index ++;
        if(index == players.size()){
            index = 0;
        }
        currentPlayer = players.get(index);
    }
}
