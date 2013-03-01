package be.kuleuven.swop.objectron.model;


import be.kuleuven.swop.objectron.model.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.model.exception.NotEnoughActionsException;
import be.kuleuven.swop.objectron.model.item.LightMine;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Nik Torfs
 *         Date: 22/02/13
 *         Time: 07:04
 */
public class Grid {
    private Square[][] squares;
    private GridBuilder gridBuilder;

    public Grid(int width, int height) {
        gridBuilder = new GridBuilder(width, height);
        this.squares = new Square[height][width];
        setupNeighbours();
    }

    public void buildGrid(Square playerOneSquare, Square playerTwoSquare) {
        squares = gridBuilder.build(playerOneSquare,playerTwoSquare, squares);
    }

    public void makeMove(Direction direction, Player player) throws InvalidMoveException, NotEnoughActionsException {
        Square neighbour = player.getCurrentSquare().getNeighbour(direction);
        if (!validPosition(neighbour)) {
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

    private boolean validPosition(Square neighbour) {
        return neighbour != null && !neighbour.isObstructed();
    }

    private void setupNeighbours() {
        for (int vertical = 0; vertical < squares.length; vertical++) {
            for (int horizontal = 0; horizontal < squares[0].length; horizontal++) {
                squares[vertical][horizontal] = new Square();
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
}
