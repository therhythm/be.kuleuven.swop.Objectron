package be.kuleuven.swop.objectron.domain.gamestate;

import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.exception.InvalidFileException;
import be.kuleuven.swop.objectron.domain.grid.FileGridBuilder;
import be.kuleuven.swop.objectron.domain.grid.GeneratedGridBuilder;
import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.grid.GridBuilder;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.util.Dimension;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Nik Torfs
 *         Date: 20/05/13
 *         Time: 02:32
 */
public abstract class GameBuilder {
    private boolean withItems = false;
    private boolean withWalls = false;
    private GridBuilder builder;

    protected List<String> playerNames;
    protected Dimension dimension;

    public GameBuilder(List<String> playerNames, Dimension dimension) throws GridTooSmallException {
        this.playerNames = playerNames;
        this.dimension = dimension;
        this.builder = new GeneratedGridBuilder(dimension, playerNames.size());
    }

    public void withBuilder(GridBuilder builder){
        this.builder = builder;
    }

    public void withFile(String file) throws InvalidFileException {
        builder = new FileGridBuilder(file);
    }

    public void withItems() {
        withItems = true;
    }

    public void withWalls() {
        withWalls = true;
    }

    public Game buildGame() {
        if(withWalls){
            builder.buildWalls();
        }
        if(withItems){
            builder.buildItems();
        }

        Grid grid = builder.buildGrid();

        List<Player> players = initializePlayers(grid.getPlayerPositions());

        return new Game(players, grid);
    }

    protected List<Player> initializePlayers(List<Square> playerPositions){
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < playerNames.size(); i++) {
            players.add(new Player(playerNames.get(i), playerPositions.get(i)));
        }

        return players;
    }
}
