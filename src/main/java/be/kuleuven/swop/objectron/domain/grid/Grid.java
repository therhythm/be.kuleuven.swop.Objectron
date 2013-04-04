package be.kuleuven.swop.objectron.domain.grid;


import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.Wall;
import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.viewmodel.SquareViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Nik Torfs
 *         Date: 22/02/13
 *         Time: 07:04
 */
public class Grid {
    private Square[][] squares;
    private List<Wall> walls;

    public Grid(Square[][] squares,List<Wall> walls){
        this.squares = squares;
        this.walls = walls;
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

    public Square getSquareAtPosition(int vertIndex, int horIndex) {
        if (!validIndex(horIndex, vertIndex)) {
            throw new IllegalArgumentException("Not a valid square index");
        }

        return squares[vertIndex][horIndex];
    }


    private boolean validIndex(int horIndex, int vertIndex) {
        return horIndex > -1 && horIndex < squares[0].length
                && vertIndex > -1 && vertIndex < squares.length;
    }

    public List<List<SquareViewModel>> getWalls() {
        List<List<SquareViewModel>> wallViewModels = new ArrayList<List<SquareViewModel>>();
        for (Wall w : this.walls) {
            wallViewModels.add(w.getWallViewModel());
        }
        return wallViewModels;
    }

    public void newTurn(Player player) {
        for (Square[] square : squares) {
            for (Square sq : square) {
                sq.newTurn(player);
            }
        }
    }
}
