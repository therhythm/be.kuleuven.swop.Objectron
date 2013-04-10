package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.square.Square;

import java.util.List;

import com.sun.xml.internal.org.jvnet.fastinfoset.stax.LowLevelFastInfosetStreamWriter;

/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 9/04/13
 * Time: 17:57
 * To change this template use File | Settings | File Templates.
 */
public class UseItemRequest {
    private Square square;
    private Direction direction;
    private Grid grid;
    private List<Player> players;

    public UseItemRequest(Square square) {
        this.square = square;
        this.direction = null;
        this.grid = null;
        this.players = null;
    }

    public UseItemRequest(Square square, Direction direction, Grid grid, List<Player> players) {
        this.square = square;
        this.direction = direction;
        this.grid = grid;
        this.players = players;
    }

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Grid getGrid() {
        return grid;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
