package be.kuleuven.swop.objectron.domain.grid;


import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Wall;
import be.kuleuven.swop.objectron.domain.effect.Effect;
import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.gamestate.TurnSwitchObserver;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.effect.powerfailure.PrimaryPowerFailure;
import be.kuleuven.swop.objectron.domain.item.forceField.ForceFieldArea;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.domain.util.Observable;
import be.kuleuven.swop.objectron.domain.util.Position;
import be.kuleuven.swop.objectron.viewmodel.GridViewModel;
import be.kuleuven.swop.objectron.viewmodel.SquareViewModel;

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
    private List<Position> playerPositions;
    private int powerFailureChance;

    public Grid(Square[][] squares, List<Wall> walls, Dimension dimension, ForceFieldArea forceFieldArea,
                List<Position> playerPositions, int powerFailureChance) {
        this.squares = squares;
        this.walls = walls;
        this.dimension = dimension;
        this.forceFieldArea = forceFieldArea;
        this.playerPositions = playerPositions;
        this.powerFailureChance = powerFailureChance;
    }

    public Square makeMove(Direction direction, Square currentSquare) throws InvalidMoveException,
            NotEnoughActionsException {
        Square neighbour = currentSquare.getNeighbour(direction);

        if (neighbour == null)
            throw new InvalidMoveException();
        if (!neighbour.isValidPosition(direction)) {
            throw new InvalidMoveException();
        }

        return neighbour;
    }

    public List<Square> getPlayerPositions() {
        List<Square> positions = new ArrayList<>();
        for (Position pos : playerPositions) {
            positions.add(getSquareAtPosition(pos));
        }
        return positions;
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
        List<List<Position>> wallViewModels = new ArrayList<>();
        for (Wall w : this.walls) {
            wallViewModels.add(w.getWallViewModel());
        }
        return wallViewModels;
    }

    public void newTurn(Turn currentTurn) {
        for (Square[] square : squares) {
            for (Square sq : square) {
                sq.newTurn(currentTurn);
            }
        }
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
    public void turnEnded(Observable<TurnSwitchObserver> observable) {
        for (Square[] square : squares) {
            for (Square sq : square) {
                if (losingPower()) {
                    new PrimaryPowerFailure(sq, observable);
                }
            }
        }
    }

    private boolean losingPower() {
        int r = (int) (Math.random() * 100);
        return r < powerFailureChance;
    }

    @Override
    public void update(Turn turn) {
        // do nothing
    }

    @Override
    public void actionReduced() {
        // actionreduced
    }

    @Override
    public void actionHappened(Observable<TurnSwitchObserver> observable) {
        //do nothing
    }

    public ForceFieldArea getForceFieldArea() {
        return forceFieldArea;
    }

    //obstruction is van het type wall  //todo ??
    public ArrayList<Square> getSquaresNotObstructed() {
        ArrayList<Square> result = new ArrayList<>();
        for (Square[] row : squares) {
            for (Square square : row) {
                if (!square.isObstructed())
                    result.add(square);
            }
        }
        return result;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public GridViewModel getViewModel() {
        List<SquareViewModel> squareViewModels = new ArrayList<>();
        for(Square[] row: squares){
            for(Square sq: row){
                squareViewModels.add(sq.getViewModel());
            }

        }
        return new GridViewModel(squareViewModels);
    }
}
