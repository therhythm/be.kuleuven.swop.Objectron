package be.kuleuven.swop.objectron;

import be.kuleuven.swop.objectron.domain.Grid;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Nik Torfs
 *         Date: 26/02/13
 *         Time: 21:02
 */
public class GameStateImpl implements GameState {
    private Grid gameGrid;
    private Player currentPlayer;
    private List<Player> players = new ArrayList<Player>();

    public GameStateImpl(String player1Name, String player2Name, int horizontalTiles, int verticalTiles) throws GridTooSmallException{
        gameGrid = new Grid(horizontalTiles, verticalTiles);
        Player p1 = new Player(player1Name, gameGrid.getSquareAtPosition(verticalTiles - 1, 0));
        Player p2 = new Player(player2Name, gameGrid.getSquareAtPosition(0, horizontalTiles - 1));
        gameGrid.buildGrid(p1.getCurrentSquare(), p2.getCurrentSquare());
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
    }
}
