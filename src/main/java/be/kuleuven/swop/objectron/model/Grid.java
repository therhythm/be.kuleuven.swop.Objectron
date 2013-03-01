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
    public static final double MAX_WALL_COVERAGE_PERCENTAGE = 0.2;
    public static final int MIN_WALL_LENGTH = 2;
    public static final double MAX_WALL_LENGTH_PERCENTAGE = 0.5;
    public static final double PERCENTAGE_OF_ITEMS = 0.05;

    private Square[][] squares;
    private List<Wall> walls;

    public Grid(int width, int height) {
        this.squares = new Square[height][width];
        setupNeighbours();
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

    /**
     * NOTE: BUILDER pattern might be useful here if multiple grid setup strategies are needed (or TEMPLATE METHOD)
     */
    public void setupGrid(Square p1Square,Square p2Square) {
        setupWalls();
        setupItems(p1Square,p2Square);
    }

    private void setupItems(Square p1Square, Square p2Square) {
        int numberOfItems = (int) Math.ceil(PERCENTAGE_OF_ITEMS * (squares.length * squares[0].length));

        //players 3x3 square
        for (Square square:getAllNeighboursFromSquare(p1Square)){
            if(getAllNeighboursFromSquare(square).size() == 8){
                placeLightMineInArea(square);
                numberOfItems --;
            }
        }

        for (Square square:getAllNeighboursFromSquare(p2Square)){
            if(getAllNeighboursFromSquare(square).size() == 8){
                placeLightMineInArea(square);
                numberOfItems --;
            }
        }

        //rest mines
        placeOtherMines(numberOfItems);

    }

    private List<Square> getAllNeighboursFromSquare(Square square){
        List<Square> neigbourSquares = new ArrayList<Square>();
        for(Direction d: Direction.values()){
            if(square.getNeighbour(d) != null){
                neigbourSquares.add(square.getNeighbour(d));
            }
        }
        return neigbourSquares;
    }

    private void placeLightMineInArea(Square square){
        List<Square> possibleSquares = getAllNeighboursFromSquare(square);
        List<Square> goodSquares = new ArrayList<Square>();
        possibleSquares.add(square);
        for(Square s:possibleSquares){
            if(!s.isObstructed() && s.getAvailableItems().size() == 0){
                goodSquares.add(s);
            }
        }

        int randomIndex = getRandomWithMax(0,goodSquares.size());
        goodSquares.get(randomIndex).addItem(new LightMine());

    }

    private void placeOtherMines(int numberOfMines){
        for(int i = 0;i<numberOfMines;i++) {
            Square randomSquare = getRandomSquare();
            while (randomSquare.getAvailableItems().size() != 0 && !randomSquare.isObstructed()) {
                randomSquare = getRandomSquare();
            }
        randomSquare.addItem(new LightMine());
        }
    }

    private void setupWalls() {
        walls = new ArrayList<Wall>();

        int maxNumberOfWalls = (int) Math.floor(MAX_WALL_COVERAGE_PERCENTAGE * ((squares.length * squares[0].length) / 2));
        int numberOfWalls = getRandomWithMax(1, maxNumberOfWalls + 1);

        boolean twentyPercentReached = false;
        while (walls.size() < numberOfWalls && !twentyPercentReached) {
            twentyPercentReached = makeWall();
        }
    }

    private double calculateWallPercentage(int extraWalls) {
        for (Wall w : walls) {
            extraWalls += w.getLength();
        }
        double wallArea = (double) extraWalls;
        double gridArea = (double) squares.length * squares[0].length;
        return wallArea / gridArea;
    }

    private boolean makeWall() {
        //Startsquare kiezen
        Square randomSquare = getRandomSquare();
        while (!isValidWallPosition(randomSquare) && !randomSquare.isObstructed()) {
            randomSquare = getRandomSquare();
        }

        //Direction bepalen
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
        int vertIndex = getRandomWithMax(0, squares.length);
        int horIndex = getRandomWithMax(0, squares[0].length);

        return getSquareAtPosition(vertIndex, horIndex);
    }

    private boolean isValidWallPosition(Square square) {
        for (Direction d : Direction.values()) {
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

    private void generateValidWall(Square currentSquare, Direction direction, int maxLength) {
        int length = 1;
        boolean isValid = true;
        Wall wall = new Wall();
        wall.addSquare(currentSquare);

        while (length <= maxLength && isValid && calculateWallPercentage(length + 1) <= MAX_WALL_COVERAGE_PERCENTAGE) {
            currentSquare = currentSquare.getNeighbour(direction);
            if (currentSquare != null)
                if (isValidWallPosition(currentSquare)) {
                    wall.addSquare(currentSquare);
                    length++;
                } else {
                    isValid = false;
                }
            else
                isValid = false;
        }

        if (length >= MIN_WALL_LENGTH) {
            wall.build();
            walls.add(wall);
        }


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
                for (Direction d : Direction.values()) {
                    int horIndex = d.applyHorizontalOperation(horizontal);
                    int vertIndex = d.applyVerticalOperation(vertical);
                    if (validIndex(horIndex, vertIndex)) {
                        current.addNeighbour(d, squares[vertIndex][horIndex]);
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
