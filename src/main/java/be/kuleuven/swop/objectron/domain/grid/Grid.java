package be.kuleuven.swop.objectron.domain.grid;


import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Wall;
import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.gamestate.TurnSwitchObserver;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.item.effect.Effect;
import be.kuleuven.swop.objectron.domain.item.forceField.ForceFieldArea;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.domain.util.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : Nik Torfs
 *         Date: 22/02/13
 *         Time: 07:04
 */
public class Grid implements TurnSwitchObserver {
    private Square[][] squares;
    private Dimension dimension;
    private List<Wall> walls;
    private ForceFieldArea forceFieldArea;

    public Grid(Square[][] squares, List<Wall> walls, Dimension dimension) {
        this.squares = squares;
        this.walls = walls;
        this.dimension = dimension;
        this.forceFieldArea = new ForceFieldArea();
    }

    public Grid(Square[][] squares, List<Wall> walls, Dimension dimension, ForceFieldArea forceFieldArea) {
        this.squares = squares;
        this.walls = walls;
        this.dimension = dimension;
        this.forceFieldArea = forceFieldArea;
    }

    public Square makeMove(Direction direction, Square currentSquare) throws InvalidMoveException, NotEnoughActionsException {
        Square neighbour = currentSquare.getNeighbour(direction);

        if (neighbour == null)
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

    public Map<Position, List<Item>> getItems() {
        Map<Position, List<Item>> items = new HashMap<>();
        for (Square[] row : squares) {
            for (Square sq : row) {
                items.put(sq.getPosition(), sq.getAvailableItems());
            }
        }
        return items;
    }

    public Map<Position, List<Effect>> getEffects() {
        Map<Position, List<Effect>> effects = new HashMap<>();
        for (Square[] row : squares) {
            for (Square sq : row) {
                effects.put(sq.getPosition(), sq.getEffects());
            }
        }
        return effects;
    }

    @Override
    public void turnEnded(Turn turn) {
        for (Square[] square : squares) {
            for (Square sq : square) {
                sq.newTurn(turn);
            }
        }
    }

    @Override
    public void update(Turn turn) {
        // do nothing
    }

    @Override
    public void actionReduced() {
        // actionreduced
    }

    public ForceFieldArea getForceFieldArea() {
        return forceFieldArea;
    }

    //obstruction is van het type wall  //todo ??
    public ArrayList<Square> getSquaresNotObstructed() {
        ArrayList<Square> result = new ArrayList<Square>();
        for (Square[] row : squares) {
            for (Square square : row) {
                if (!square.isObstructed())
                    result.add(square);
            }
        }
        return result;
    }
}
