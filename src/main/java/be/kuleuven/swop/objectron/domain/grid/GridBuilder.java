package be.kuleuven.swop.objectron.domain.grid;


import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Wall;
import be.kuleuven.swop.objectron.domain.item.effect.Teleporter;
import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.item.*;
import be.kuleuven.swop.objectron.domain.item.forceField.ForceField;
import be.kuleuven.swop.objectron.domain.item.forceField.ForceFieldArea;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.square.SquareObserver;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.domain.util.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Nik
 * Date: 4/4/13
 * Time: 2:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class GridBuilder {
    private static final double MAX_WALL_COVERAGE_PERCENTAGE = 0.2;
    private static final int MIN_WALL_LENGTH = 2;
    private static final double MAX_WALL_LENGTH_PERCENTAGE = 0.5;
    private static final int MIN_GRID_WIDTH = 10;
    private static final int MIN_GRID_HEIGHT = 10;
    private static final double PERCENTAGE_OF_TELEPORTERS = 0.03;
    private static final double PERCENTAGE_OF_LIGHTMINES = 0.02;
    private static final double PERCENTAGE_OF_IDENTITYDISCS = 0.02;
    private static final double PERCENTAGE_OF_FORCEFIELDS = 0.07;



    private Dimension dimension;
    private Position p1Pos;
    private Position p2Pos;

    private Square[][] squares;
    private List<Wall> walls;
    private ForceFieldArea forceFieldArea;

    public GridBuilder(Dimension dimension, Position p1Pos, Position p2Pos) throws GridTooSmallException {
        if (!isValidDimension(dimension)) {
            throw new GridTooSmallException("The grid needs to be at least " +
                    MIN_GRID_HEIGHT + " rows by " +
                    MIN_GRID_HEIGHT + " columns");
        }
        this.dimension = dimension;
        this.p1Pos = p1Pos;
        this.p2Pos = p2Pos;
        initGrid(Square.POWER_FAILURE_CHANCE);
        forceFieldArea = new ForceFieldArea();
    }

    public void buildWalls() {
        walls = new ArrayList<Wall>();

        int maxNumberOfWalls = (int) Math.floor(MAX_WALL_COVERAGE_PERCENTAGE *
                (dimension.area() / MIN_WALL_LENGTH));
        int numberOfWalls = getRandomWithMax(1, maxNumberOfWalls);

        while (walls.size() < numberOfWalls && isAnotherWallPossible()) {
            buildWall();
        }
    }

    public void buildWalls(List<Wall> walls) {
        this.walls = walls;

    }

    public Grid getGrid() {
        return new Grid(squares, walls, dimension,forceFieldArea);
    }

    private Square calculateMiddleSquare() {
        int HIndex = Math.round(Math.abs(p1Pos.getHIndex() - p2Pos.getHIndex() + 1) / 2);
        int VIndex = Math.round(Math.abs(p1Pos.getHIndex() - p2Pos.getHIndex() + 1) / 2);
        return squares[VIndex][HIndex];
    }

    private void placeChargedIdentityDisc() {
        Square middle = calculateMiddleSquare();
        ArrayList<Square> randomMiddleSquares = new ArrayList<Square>();
        randomMiddleSquares.add(middle);
        randomMiddleSquares.add(middle.getNeighbour(Direction.UP));
        randomMiddleSquares.add(middle.getNeighbour(Direction.UP_LEFT));
        randomMiddleSquares.add(middle.getNeighbour(Direction.LEFT));
        randomMiddleSquares.add(middle.getNeighbour(Direction.DOWN_LEFT));
        randomMiddleSquares.add(middle.getNeighbour(Direction.DOWN));
        randomMiddleSquares.add(middle.getNeighbour(Direction.DOWN_RIGHT));
        randomMiddleSquares.add(middle.getNeighbour(Direction.RIGHT));
        randomMiddleSquares.add(middle.getNeighbour(Direction.UP_RIGHT));
        Random random = new Random();
        Square randomSquare;
        while (randomMiddleSquares.size() > 0) {
            int randomIndex = random.nextInt(randomMiddleSquares.size());
            randomSquare = randomMiddleSquares.get(randomIndex);
            if (randomSquare != null) {
                if (!randomSquare.isObstructed()) {
                    randomSquare.addItem(new IdentityDisc(new ChargedIdentityDiscBehavior()));
                    break;
                }
            }
            randomMiddleSquares.remove(randomIndex);
        }
    }

    public void buildItems() {
        int numberOfLightmines = (int) Math.ceil(PERCENTAGE_OF_LIGHTMINES * dimension.area());
        int numberOfTeleporters = (int) Math.ceil(PERCENTAGE_OF_TELEPORTERS * dimension.area());
        int numberOfIdentityDiscs = (int) Math.ceil(PERCENTAGE_OF_IDENTITYDISCS * dimension.area());
        int numberOfForceFields = (int) Math.floor(PERCENTAGE_OF_FORCEFIELDS * dimension.area());


        placeLightMines(numberOfLightmines);
        placeTeleporters(numberOfTeleporters);
        placeIdentityDiscs(numberOfIdentityDiscs);
        placeForceFields(numberOfForceFields);
    }

    private void placeForceFields(int numberOfItems) {
        List<Item> forceFields = new ArrayList<Item>();
        for(int i = 0;i<numberOfItems;i++){
            ForceField forcefield = new ForceField(forceFieldArea);
            forceFields.add(forcefield);
        }
        placeOtherItems(forceFields);
    }

    private void placeIdentityDiscs(int numberOfItems) {
        placeItemToPlayer(squares[p1Pos.getVIndex()][p1Pos.getHIndex()], new IdentityDisc(new NormalIdentityDiscBehavior()));
        placeItemToPlayer(squares[p2Pos.getVIndex()][p2Pos.getHIndex()], new IdentityDisc(new NormalIdentityDiscBehavior()));
        numberOfItems-=2;
        List<Item> identityDiscs = new ArrayList<Item>();

        for (int i = 0; i < numberOfItems; i++) {
            identityDiscs.add(new IdentityDisc(new NormalIdentityDiscBehavior()));
        }
        placeOtherItems(identityDiscs);

        placeChargedIdentityDisc();
    }

    private void placeLightMines(int numberOfItems) {
        placeItemToPlayer(squares[p1Pos.getVIndex()][p1Pos.getHIndex()], new LightMine());
        placeItemToPlayer(squares[p2Pos.getVIndex()][p2Pos.getHIndex()], new LightMine());
        numberOfItems -= 2;
        List<Item> lightMines = new ArrayList<Item>();
        List<Item> identityDiscs = new ArrayList<Item>();

        for (int i = 0; i < numberOfItems; i++) {
            lightMines.add(new LightMine());
        }
        placeOtherItems(lightMines);
    }

    private void placeTeleporters(int numberOfTeleporters) {
        Teleporter[] teleporters = new Teleporter[numberOfTeleporters];
        for (int i = 0; i < numberOfTeleporters; i++) {
            Square randomSquare = getRandomSquare();
            //TODO looks for empty squares, but teleporters can be placed alongside other items
            while (randomSquare.getAvailableItems().size() != 0 ||
                    randomSquare.isObstructed()) {
                randomSquare = getRandomSquare();
            }
            Teleporter teleporter = new Teleporter(randomSquare);
            randomSquare.addEffect(teleporter);

            //TODO no need to set effect as active... all effects are active
            /*try {
                randomSquare.setActiveItem(teleporter);
            } catch (SquareOccupiedException e) {
                e.printStackTrace();
            } */
            teleporters[i] = teleporter;
        }
        //TODO multiple teleporters can have the same destination, is this allowed?
        for (int i = 0; i < numberOfTeleporters; i++) {
            int random = (int) Math.floor(Math.random() * 3);
            while (random == i) {
                random = (int) Math.floor(Math.random() * 3);
            }
            teleporters[i].setDestination(teleporters[random]);
        }
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

    private void placeItemToPlayer(Square playerOneSquare, Item item) {
        for (Square square : getAllNeighboursFromSquare(playerOneSquare)) {
            //find the middle tile
            if (getAllNeighboursFromSquare(square).size() == 8) {
                placeItemArea(square, item);
                break;
            }
        }
    }

    private void placeOtherItems(List<Item> items) {
        for (Item item : items) {
            Square randomSquare = getRandomSquare();
            while (randomSquare.getAvailableItems().size() != 0
                    || randomSquare.isObstructed()) {
                randomSquare = getRandomSquare();
            }

            randomSquare.addItem(item);
        }
    }


    private void placeItemArea(Square square, Item item) {
        List<Square> possibleSquares = getAllNeighboursFromSquare(square);
        List<Square> goodSquares = new ArrayList<Square>();
        possibleSquares.add(square);
        for (Square s : possibleSquares) {
            if (!s.isObstructed() && s.getAvailableItems().size() == 0) {
                goodSquares.add(s);
            }
        }

        Random generator = new Random();
        int randomIndex = generator.nextInt(goodSquares.size());


        goodSquares.get(randomIndex).addItem(item);


    }

    private List<Square> getAllNeighboursFromSquare(Square square) {
        List<Square> neighbourSquares = new ArrayList<Square>();
        for (Direction d : Direction.values()) {
            if (square.getNeighbour(d) != null) {
                neighbourSquares.add(square.getNeighbour(d));
            }
        }
        return neighbourSquares;
    }


    public void initGrid(int powerFailureChance) {
        this.squares = new Square[dimension.getHeight()][dimension.getWidth()];
        for (int vertical = 0; vertical < squares.length; vertical++) {
            for (int horizontal = 0; horizontal < squares[0].length; horizontal++) {
                Position pos = new Position(horizontal, vertical);
                squares[vertical][horizontal] = new Square(pos, powerFailureChance);
                if (pos.equals(p1Pos) || pos.equals(p2Pos)) {
                    squares[vertical][horizontal].setObstructed(true);
                }
            }
        }
        setupNeighbours();
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

    private Square getRandomSquare() {
        int verticalIndex = getRandomWithMax(0, dimension.getHeight() - 1);
        int horizontalIndex = getRandomWithMax(0, dimension.getWidth() - 1);
        return squares[verticalIndex][horizontalIndex];
    }

    private double calculateWallPercentage(int extraWalls) {
        for (Wall w : walls) {
            extraWalls += w.getLength();
        }
        return (double) extraWalls / (double) dimension.area();
    }

    private int getRandomWithMax(double min, double max) {
        double rand = min + Math.random() * (max - min + 1);
        return (int) Math.floor(rand);
    }

    private boolean isValidDimension(Dimension dimension) {
        return dimension.getWidth() >= MIN_GRID_WIDTH
                && dimension.getHeight() >= MIN_GRID_HEIGHT;
    }

    private boolean isValidPosition(Position pos) {
        return pos.getHIndex() > -1
                && pos.getHIndex() < dimension.getWidth()
                && pos.getVIndex() > -1
                && pos.getVIndex() < dimension.getHeight();
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
        return true;
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

    public void addObserver(SquareObserver observer) {
        for (Square[] row : squares) {
            for (Square s : row) {
                s.attach(observer);
            }
        }
    }
}
