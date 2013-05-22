package be.kuleuven.swop.objectron.domain.grid;


import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Wall;
import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.exception.TooManyPlayersException;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.square.SquareObserver;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.domain.util.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Nik
 * Date: 4/4/13
 * Time: 2:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class GeneratedGridBuilder extends GridBuilder {
    private static final int MAX_PLAYERS = 4;
    private static final double MAX_WALL_COVERAGE_PERCENTAGE = 0.2;
    private static final int MIN_WALL_LENGTH = 2;
    private static final double MAX_WALL_LENGTH_PERCENTAGE = 0.5;
    private static final int MIN_GRID_WIDTH = 10;
    private static final int MIN_GRID_HEIGHT = 10;
    private List<Position> playerPositions;

    public GeneratedGridBuilder(Dimension dimension, int nbPlayers) throws GridTooSmallException, TooManyPlayersException {
        super();

        if(nbPlayers > MAX_PLAYERS){
            throw new TooManyPlayersException("You can only play with " + MAX_PLAYERS + " on this grid");
        }

        if (!isValidDimension(dimension)) {
            throw new GridTooSmallException("The grid needs to be at least " +
                    MIN_GRID_HEIGHT + " rows by " +
                    MIN_GRID_HEIGHT + " columns");
        }

        this.dimension = dimension;

        initPlayerPositions(nbPlayers);
        initGrid(Square.POWER_FAILURE_CHANCE);
    }

    private void initPlayerPositions(int nbPlayers) {
        List<Position> tempPositions = new ArrayList<>();
        tempPositions.add(new Position(0, this.dimension.getHeight() - 1));
        tempPositions.add(new Position(this.dimension.getWidth() - 1, 0));
        tempPositions.add(new Position(0, 0));
        tempPositions.add(new Position(this.dimension.getWidth() - 1, dimension.getHeight() - 1));

        playerPositions = new ArrayList<>();
        for (int i = 0; i < nbPlayers; i++) {
            playerPositions.add(tempPositions.get(i));
        }
    }

    @Override
    public void setStartingPositions(List<Position> positions) {
        this.playerPositions = positions;
    }

    @Override
    public void buildWalls() {
        if(walls != null){
            return;
        }
        walls = new ArrayList<>();

        int maxNumberOfWalls = (int) Math.floor(MAX_WALL_COVERAGE_PERCENTAGE *
                (dimension.area() / MIN_WALL_LENGTH));
        int numberOfWalls = getRandomWithMax(1, maxNumberOfWalls);

        while (walls.size() < numberOfWalls && isAnotherWallPossible()) {
            buildWall();
        }
    }

    @Override
    public void buildWalls(List<List<Position>> wallPositions) {
        List<Wall> walls = new ArrayList<>();
        for (List<Position> positionsOfWall : wallPositions) {
            Wall wall = new Wall();
            for(Position pos: positionsOfWall){
                wall.addSquare(squares[pos.getVIndex()][pos.getHIndex()]);
                squares[pos.getVIndex()][pos.getHIndex()].addObstruction(wall);
            }
            walls.add(wall);
        }

        this.walls = walls;
    }

    @Override
    public void addObserver(SquareObserver observer) {
        for (Square[] row : squares) {
            for (Square s : row) {
                s.attach(observer);
            }
        }
    }

    @Override
    public void initGrid(int powerFailureChance) {
        this.squares = new Square[dimension.getHeight()][dimension.getWidth()];
        for (int vertical = 0; vertical < squares.length; vertical++) {
            for (int horizontal = 0; horizontal < squares[0].length; horizontal++) {
                Position pos = new Position(horizontal, vertical);
                squares[vertical][horizontal] = new Square(pos, powerFailureChance);
            }
        }
        setupNeighbours();
    }

    @Override
    public Grid buildGrid() {
        return new Grid(squares, walls, dimension, forceFieldArea, playerPositions);
    }

    @Override
    protected List<Position> getPlayerPositions() {
        return playerPositions;
    }

    private void buildWall() {
        Square randomSquare = getRandomSquare();
        while (!isValidWallPosition(randomSquare)) {
            randomSquare = getRandomSquare();
        }

        Direction direction;
        int maxLength;
        int rand = getRandomWithMax(1, 4);
        switch (rand) {
            case 1:
                direction = Direction.UP;
                maxLength = getRandomWithMax(MIN_WALL_LENGTH,
                        dimension.getHeight() * MAX_WALL_LENGTH_PERCENTAGE);
                break;
            case 2:
                direction = Direction.LEFT;
                maxLength = getRandomWithMax(MIN_WALL_LENGTH,
                        dimension.getWidth() * MAX_WALL_LENGTH_PERCENTAGE);
                break;
            case 3:
                direction = Direction.DOWN;
                maxLength = getRandomWithMax(MIN_WALL_LENGTH,
                        dimension.getHeight() * MAX_WALL_LENGTH_PERCENTAGE);
                break;
            default:
                direction = Direction.RIGHT;
                maxLength = getRandomWithMax(MIN_WALL_LENGTH,
                        dimension.getWidth() * MAX_WALL_LENGTH_PERCENTAGE);
        }

        buildWall(randomSquare, direction, maxLength);
    }

    private void buildWall(Square currentSquare, Direction direction, int maxLength) {
        Wall wall = new Wall();
        double wallPercentage = calculateWallPercentage(0);
        while (wall.getLength() <= maxLength
                && wallPercentage <= MAX_WALL_COVERAGE_PERCENTAGE
                && currentSquare != null
                && isValidWallPosition(currentSquare)) {

            wall.addSquare(currentSquare);
            wallPercentage = calculateWallPercentage(wall.getLength());
            currentSquare = currentSquare.getNeighbour(direction);
        }

        if (wall.getLength() >= MIN_WALL_LENGTH) {
            wall.build();
            walls.add(wall);
        }
    }

    private boolean isValidWallPosition(Square square) {
        if (square.isObstructed()) {
            return false;
        }
        for (Direction d : Direction.values()) {
            if (square.getNeighbour(d) != null && square.getNeighbour(d).isObstructed()) {
                return false;
            }
        }

        for (Position p : playerPositions) {
            if (p.getVIndex() == square.getPosition().getVIndex() && p.getHIndex() == square.getPosition().getHIndex
                    ()) {
                return false;
            }
        }
        return true;
    }

    private void setupNeighbours() {
        for (int vertical = 0; vertical < dimension.getHeight(); vertical++) {
            for (int horizontal = 0; horizontal < dimension.getWidth(); horizontal++) {
                Square current = squares[vertical][horizontal];
                for (Direction direction : Direction.values()) {
                    Position newPos = direction.applyPositionChange(new Position(horizontal, vertical));
                    if (isValidPosition(newPos)) {
                        current.addNeighbour(direction, squares[newPos.getVIndex()][newPos.getHIndex()]);
                    }
                }
            }
        }
    }

    private double calculateWallPercentage(int extraWalls) {
        for (Wall w : walls) {
            extraWalls += w.getLength();
        }
        return (double) extraWalls / (double) dimension.area();
    }

    private boolean isValidDimension(Dimension dimension) {
        return dimension.getWidth() >= MIN_GRID_WIDTH
                && dimension.getHeight() >= MIN_GRID_HEIGHT;
    }

    private boolean isAnotherWallPossible() {
        boolean possible = false;
        for (Square[] row : squares) {
            for (Square sq : row) {
                if (isValidWallPosition(sq)) {
                    for (Direction d : Direction.values()) {
                        if (sq.getNeighbour(d) != null && isValidWallPosition(sq.getNeighbour(d))) {
                            possible = true;
                            break;
                        }
                    }
                    if (possible) break;
                }
            }
            if (possible) break;
        }
        double wallCoverage = calculateWallPercentage(1);

        return possible &&
                wallCoverage <= MAX_WALL_COVERAGE_PERCENTAGE;
    }

}
