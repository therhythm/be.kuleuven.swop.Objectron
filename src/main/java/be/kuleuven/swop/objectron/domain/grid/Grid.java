package be.kuleuven.swop.objectron.domain.grid;


import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.Wall;
import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.domain.util.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Nik Torfs
 *         Date: 22/02/13
 *         Time: 07:04
 */
public class Grid {
    private Square[][] squares;
    private Dimension dimension;
    private List<Wall> walls;

    public Grid(Square[][] squares,List<Wall> walls, Dimension dimension){
        this.squares = squares;
        this.walls = walls;
        this.dimension = dimension;
    }

    public Square makeMove(Direction direction, Square currentSquare) throws InvalidMoveException, NotEnoughActionsException {
        Square neighbour = currentSquare.getNeighbour(direction);

        if(neighbour==null)
            throw new InvalidMoveException();
        if (!neighbour.isValidPosition(direction)) {
            throw new InvalidMoveException();
        }

        return neighbour;
    }

    public Square getSquareAtPosition(Position position) {
        if (!isValidPosition(position)) {
            throw new IllegalArgumentException("Not a valid square index");
        }

        return squares[position.getVIndex()][position.getHIndex()];
    }


    private boolean isValidPosition(Position pos) {
        return pos.getHIndex() > -1
                && pos.getHIndex() < dimension.getWidth()
                && pos.getVIndex() > -1
                && pos.getVIndex() < dimension.getHeight();
    }

    public List<List<Position>> getWalls() {
        List<List<Position>> wallViewModels = new ArrayList<List<Position>>();
        for (Wall w : this.walls) {
            wallViewModels.add(w.getWallViewModel());
        }
        return wallViewModels;
    }

    public Dimension getDimension(){
        return this.dimension;
    }

    public void newTurn(Player player) {
        for (Square[] square : squares) {
            for (Square sq : square) {
                sq.newTurn(player);
            }
        }
    }
}