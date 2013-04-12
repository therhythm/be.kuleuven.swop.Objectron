package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.square.Square;

import java.util.List;

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
    private GameState gameState;

    public UseItemRequest(Square square) {
        this.square = square;
        this.direction = null;
        this.gameState=null;
    }

    public UseItemRequest(Square square, Direction direction, GameState gamestate) {
        this.square = square;
        this.direction = direction;
        this.gameState = gamestate;
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
        return gameState.getGrid();
    }

    public List<Player> getPlayers() {
        return gameState.getPlayers();
    }

    public GameState getGameState(){
        return this.gameState;
    }
}
