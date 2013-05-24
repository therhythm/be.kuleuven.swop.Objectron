package be.kuleuven.swop.objectron.domain.grid;


import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.Wall;
import be.kuleuven.swop.objectron.domain.effect.Effect;
import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.gamestate.TurnObserver;
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

    /**
     * Initializes a new Grid with all the given parameters.
     *
     * @param squares A list of squares for the grid to use
     * @param walls A list of walls for the new grid
     * @param dimension The dimension for the new Grid
     * @param forceFieldArea The forcefieldArea for the new grid
     * @param playerPositions The positions where the players must start
     * @param powerFailureChance The chance of a square losing power each turn
     */
    public Grid(Square[][] squares, List<Wall> walls, Dimension dimension, ForceFieldArea forceFieldArea,
                List<Position> playerPositions, int powerFailureChance) {
        this.squares = squares;
        this.walls = walls;
        this.dimension = dimension;
        this.forceFieldArea = forceFieldArea;
        this.playerPositions = playerPositions;
        this.powerFailureChance = powerFailureChance;
    }

    /**
     * Tries to move in a certain direction , and returns the square in that direction
     * @param direction the direction to move
     * @param currentSquare the square where to move from
     * @return the neighbouring square in the given direction
     * @throws InvalidMoveException
     *         The square in that direction isn't valid to make a move on
     */
    public Square makeMove(Direction direction, Square currentSquare) throws InvalidMoveException {
        Square neighbour = currentSquare.getNeighbour(direction);

        if (neighbour == null)
            throw new InvalidMoveException();
        if (!neighbour.isValidPosition(direction)) {
            throw new InvalidMoveException();
        }

        return neighbour;
    }

    /**
     * Returns the positions of the players
     */
    public List<Square> getPlayerPositions() {
        List<Square> positions = new ArrayList<>();
        for (Position pos : playerPositions) {
            positions.add(getSquareAtPosition(pos));
        }
        return positions;
    }

    /**
     * Returns the square of the grid
     * @param position the postion of the square to be found
     * @throws IllegalArgumentException
     *         The given position isnt valid for this grid
     */
    public Square getSquareAtPosition(Position position) {
        if (!isValidPosition(position)) {
            throw new IllegalArgumentException("Not a valid square index");
        }

        return squares[position.getVIndex()][position.getHIndex()];
    }

    /**
     * checks if a given position is valid on this grid
     * @param pos the position to check
     */
    private boolean isValidPosition(Position pos) {
        return pos.getHIndex() > -1
                && pos.getHIndex() < dimension.getWidth()
                && pos.getVIndex() > -1
                && pos.getVIndex() < dimension.getHeight();
    }

    /**
     * Returns the list of walls
     */
    public List<List<Position>> getWalls() {
        List<List<Position>> wallViewModels = new ArrayList<>();
        for (Wall w : this.walls) {
            wallViewModels.add(w.getWallViewModel());
        }
        return wallViewModels;
    }

    /**
     * Returns a map of item positions
     */
    public Map<Position, List<Item>> getItems() {
        Map<Position, List<Item>> items = new HashMap<>();
        for (Square[] row : squares) {
            for (Square sq : row) {
                items.put(sq.getPosition(), sq.getAvailableItems());
            }
        }
        return items;
    }

    /**
     * Returns a map of effects and their positions
     */
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
    public void actionHappened(TurnManager turnManager) {
        // do nothing
    }

    /**
     * Returns the forceFieldArea
     */
    public ForceFieldArea getForceFieldArea() {
        return forceFieldArea;
    }

    /**
     * Returns the squares that aren't obstructed
     */
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

    /**
     * Returns the dimension of the grid
     */
    public Dimension getDimension() {
        return dimension;
    }

    /**
     * Returns the viewmodel of the grid
     */
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
