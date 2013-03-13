package be.kuleuven.swop.objectron.domain;


import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException;
import be.kuleuven.swop.objectron.viewmodel.SquareViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Nik Torfs
 *         Date: 22/02/13
 *         Time: 07:04
 */
public class Grid {
    private static final int MIN_WIDTH = 10;
    private static final int MIN_HEIGHT = 10;

    private Square[][] squares;
    private GridBuilder gridBuilder;

    public Grid(int width, int height) throws GridTooSmallException{
        if(!validDimensions(width,height)){
            throw new GridTooSmallException("The dimensions of the grid need to be at least 10x10");
        }
        gridBuilder = new GridBuilder(width, height);
        this.squares = new Square[height][width];
        setupNeighbours();
    }

    private boolean validDimensions(int width, int height) {
        return width >= MIN_WIDTH && height >= MIN_HEIGHT;
    }


    public void buildGrid(Square playerOneSquare, Square playerTwoSquare) {
        squares = gridBuilder.build(playerOneSquare, playerTwoSquare, squares);
    }

    public void makeMove(Direction direction, Player player) throws InvalidMoveException, NotEnoughActionsException {
        Square neighbour = player.getCurrentSquare().getNeighbour(direction);
        if (player.getAvailableActions() == 0) {
            throw new NotEnoughActionsException("You don't have enough actions left");
        }
        else if (!validPosition(neighbour,direction)) {
            throw new InvalidMoveException();
        }

        player.move(neighbour);
    }

    public Square getSquareAtPosition(int vertIndex, int horIndex) {
        if (!validIndex(horIndex, vertIndex)) {
            throw new IllegalArgumentException("Not a valid square index");
        }

        return squares[vertIndex][horIndex];
    }
       //TODO proberder programmeren, evt implementeren op square niveau?
    private boolean validPosition(Square neighbour, Direction direction) {
        if(neighbour ==null)
            return false;
        if ( neighbour.isObstructed())
            return false;

        //diagonaal check
        if(direction == Direction.UP_LEFT)
              if(neighbour.getNeighbour(Direction.RIGHT).isObstructed() && neighbour.getNeighbour(Direction.DOWN).isObstructed())
                  return false;

        if(direction == Direction.DOWN_LEFT)
            if(neighbour.getNeighbour(Direction.RIGHT).isObstructed() && neighbour.getNeighbour(Direction.UP).isObstructed())
                return false;

        if(direction == Direction.UP_RIGHT)
            if(neighbour.getNeighbour(Direction.LEFT).isObstructed() && neighbour.getNeighbour(Direction.DOWN).isObstructed())
                return false;

        if(direction == Direction.DOWN_RIGHT)
            if(neighbour.getNeighbour(Direction.LEFT).isObstructed() && neighbour.getNeighbour(Direction.UP).isObstructed())
                return false;

        return true;

    }

    private void setupNeighbours() {
        for (int vertical = 0; vertical < squares.length; vertical++) {
            for (int horizontal = 0; horizontal < squares[0].length; horizontal++) {
                squares[vertical][horizontal] = new Square(horizontal, vertical);
            }
        }

        for (int vertical = 0; vertical < squares.length; vertical++) {
            for (int horizontal = 0; horizontal < squares[0].length; horizontal++) {
                Square current = squares[vertical][horizontal];
                for (Direction direction : Direction.values()) {
                    int horIndex = direction.applyHorizontalOperation(horizontal);
                    int vertIndex = direction.applyVerticalOperation(vertical);
                    if (validIndex(horIndex, vertIndex)) {
                        current.addNeighbour(direction, squares[vertIndex][horIndex]);
                    }
                }
            }
        }
    }

    private boolean validIndex(int horIndex, int vertIndex) {
        return horIndex > -1 && horIndex < squares[0].length
                && vertIndex > -1 && vertIndex < squares.length;
    }

    public List<List<SquareViewModel>> getWalls() {
        List<List<SquareViewModel>> wallViewModels = new ArrayList<List<SquareViewModel>>();
        for (Wall w : gridBuilder.getWalls()) {
            wallViewModels.add(w.getWallViewModel());
        }
        return wallViewModels;
    }
}