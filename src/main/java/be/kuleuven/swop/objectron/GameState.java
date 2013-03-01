package be.kuleuven.swop.objectron;

import be.kuleuven.swop.objectron.model.Grid;
import be.kuleuven.swop.objectron.model.Player;
import be.kuleuven.swop.objectron.model.PlayerImpl;

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

    public GameState(String player1Name, String player2Name, int horizontalTiles, int verticalTiles) {
        gameGrid = new Grid(horizontalTiles, verticalTiles);
        Player p1 = new PlayerImpl(player1Name, gameGrid.getSquareAtPosition(verticalTiles - 1, 0));
        Player p2 = new PlayerImpl(player2Name, gameGrid.getSquareAtPosition(0, horizontalTiles - 1));
        gameGrid.setupGrid();
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
    }
}
