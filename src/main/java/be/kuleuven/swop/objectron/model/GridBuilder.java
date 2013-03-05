package be.kuleuven.swop.objectron.model;

import be.kuleuven.swop.objectron.model.item.LightMine;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Kasper Vervaecke
 *         Date: 01/03/13
 *         Time: 12:31
 */
public class GridBuilder {
    private static final double MAX_WALL_COVERAGE_PERCENTAGE = 0.2;
    private static final int    MIN_WALL_LENGTH = 2;
    private static final double MAX_WALL_LENGTH_PERCENTAGE = 0.5;
    private static final double PERCENTAGE_OF_ITEMS = 0.05;

    private Square[][] squares;
    private List<Wall> walls;
    private List<Square> invalidSquares;

    public GridBuilder(int gridWidth, int gridHeight) {
        squares = new Square[gridHeight][gridWidth];
    }

    public Square[][] build(Square playerOneSquare, Square playerTwoSquare, Square[][] squares) {
        this.squares = squares;
        invalidSquares = new ArrayList<Square>();
        setupWalls();
        setupItems(playerOneSquare, playerTwoSquare);
        return squares;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    private void setupWalls() {
        walls = new ArrayList<Wall>();

        int maxNumberOfWalls = (int) Math.floor(MAX_WALL_COVERAGE_PERCENTAGE *
                ((squares.length * squares[0].length) / MIN_WALL_LENGTH));
        int numberOfWalls = getRandomWithMax(1, maxNumberOfWalls + 1);

        boolean twentyPercentReached = false;
        while (walls.size() < numberOfWalls && !twentyPercentReached && ! (invalidSquares.size() > (0.8 * squares.length * squares[0].length))) {
            twentyPercentReached = makeWall();
        }
    }

    private void setupItems(Square playerOneSquare, Square playerTwoSquare) {
        int numberOfItems = (int) Math.ceil(PERCENTAGE_OF_ITEMS * (squares.length * squares[0].length));

        placeLightMineCloseToPlayer(playerOneSquare);
        placeLightMineCloseToPlayer(playerTwoSquare);

        numberOfItems -= 2;
        placeOtherMines(numberOfItems);
    }

    private void placeLightMineCloseToPlayer(Square playerOneSquare) {
        for (Square square:getAllNeighboursFromSquare(playerOneSquare)) {
            //find the middle tile
            if (getAllNeighboursFromSquare(square).size() == 8) {
                placeLightMineInArea(square);
            }
        }
    }

    private void placeOtherMines(int numberOfItems) {
        for (int i = 0; i<numberOfItems ; i++) {
            Square randomSquare = getRandomSquare();
            while (randomSquare.getAvailableItems().size() != 0
                    && !randomSquare.isObstructed()) {
                randomSquare = getRandomSquare();
            }
            randomSquare.addItem(new LightMine());
        }
    }

    private void placeLightMineInArea(Square square) {
        List<Square> possibleSquares = getAllNeighboursFromSquare(square);
        List<Square> goodSquares = new ArrayList<Square>();
        possibleSquares.add(square);
        for (Square s:possibleSquares) {
            if (!s.isObstructed() && s.getAvailableItems().size() == 0) {
                goodSquares.add(s);
            }
        }
    }

    private List<Square> getAllNeighboursFromSquare(Square square) {
        List<Square> neighbourSquares = new ArrayList<Square>();
        for (Direction d: Direction.values()) {
            if (square.getNeighbour(d) != null) {
                neighbourSquares.add(square.getNeighbour(d));
            }
        }
        return neighbourSquares;
    }

    private boolean makeWall() {
        Square randomSquare = getRandomSquare();
        while (!isValidWallPosition(randomSquare) && ! (invalidSquares.size() > (0.8 * squares.length * squares[0].length))) {
            invalidSquares.add(randomSquare);
            randomSquare = getRandomSquare();
        }

        Direction direction;
        Double rand = Math.random();
        int maxLength;
        if (rand < 0.5) {
            direction = Direction.UP;
            maxLength = getRandomWithMax(MIN_WALL_LENGTH, squares.length * MAX_WALL_LENGTH_PERCENTAGE);
        } else {
            direction = Direction.LEFT;
            maxLength = getRandomWithMax(MIN_WALL_LENGTH, squares[0].length * MAX_WALL_LENGTH_PERCENTAGE);
        }

        generateValidWall(randomSquare, direction, maxLength);
        return !(calculateWallPercentage(1) <= MAX_WALL_COVERAGE_PERCENTAGE);
    }

    private Square getRandomSquare() {
        int verticalIndex = getRandomWithMax(0, squares.length);
        int horizontalIndex = getRandomWithMax(0, squares[0].length);
        return getSquareAtPosition(verticalIndex, horizontalIndex);
    }

    private void generateValidWall(Square currentSquare, Direction direction, int maxLength) {
        int length = 1;
        boolean isValid = true;
        Wall wall = new Wall();
        wall.addSquare(currentSquare);

        while (length <= maxLength && isValid && calculateWallPercentage(length + 1)
                <= MAX_WALL_COVERAGE_PERCENTAGE) {
            currentSquare = currentSquare.getNeighbour(direction);
            if (currentSquare != null) {
                if (isValidWallPosition(currentSquare)) {
                    wall.addSquare(currentSquare);
                    length++;
                } else {
                    isValid = false;
                }
            } else {
                isValid = false;
            }
        }

        if (length >= MIN_WALL_LENGTH) {
            wall.build();
            walls.add(wall);
        }
    }

    private Square getSquareAtPosition(int verticalIndex, int horizontalIndex) {
        if (!validIndex(horizontalIndex, verticalIndex)) {
            throw new IllegalArgumentException("Not a valid square index");
        }
        return squares[verticalIndex][horizontalIndex];
    }

    private boolean isValidWallPosition(Square square) {
        if(square.isObstructed()){
            return false;
        }
        for (Direction d:Direction.values()) {
            if (square.getNeighbour(d) != null && square.getNeighbour(d).isObstructed()) {
                return false;
            }
        }
        return true;
    }

    private int getRandomWithMax(double min, double max) {
        double rand = min + Math.random() * (max - min);
        return (int) Math.floor(rand);
    }

    private double calculateWallPercentage(int extraWalls) {
        for (Wall w:walls) {
            extraWalls += w.getLength();
        }
        double wallArea = (double) extraWalls;
        double gridArea = (double) squares.length * squares[0].length;
        return wallArea/gridArea;
    }

    private boolean validIndex(int horizontalIndex, int verticalIndex) {
        return horizontalIndex > -1 && horizontalIndex < squares[0].length
                && verticalIndex > -1 && verticalIndex < squares.length;
    }
}
