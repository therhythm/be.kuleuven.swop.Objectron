package be.kuleuven.swop.objectron.domain.grid;


import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Wall;
import be.kuleuven.swop.objectron.domain.effect.Teleporter;
import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.grid.Dijkstra.Dijkstra;
import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.item.*;
import be.kuleuven.swop.objectron.domain.item.forceField.ForcefieldGenerator;
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
public class GeneratedGridBuilder implements GridBuilder {
    private static final double MAX_WALL_COVERAGE_PERCENTAGE = 0.2;
    private static final int MIN_WALL_LENGTH = 2;
    private static final double MAX_WALL_LENGTH_PERCENTAGE = 0.5;
    private static final int MIN_GRID_WIDTH = 10;
    private static final int MIN_GRID_HEIGHT = 10;
    public static final double PERCENTAGE_OF_TELEPORTERS = 0.03;
    public static final double PERCENTAGE_OF_LIGHTMINES = 0.02;
    public static final double PERCENTAGE_OF_IDENTITYDISCS = 0.02;
    public static final double PERCENTAGE_OF_FORCEFIELDS = 0.07;
    private static final int IDENTITY_DISK_PLAYER_AREA = 7;
    private static final int LIGHT_MINE_PLAYER_AREA = 5;

    private Dimension dimension;
    private List<Position> playerPositions;
    private Square[][] squares;
    private List<Wall> walls;
    private ForceFieldArea forceFieldArea;

    public GeneratedGridBuilder(Dimension dimension, int nbPlayers) throws GridTooSmallException {
        if (!isValidDimension(dimension)) {
            throw new GridTooSmallException("The grid needs to be at least " +
                    MIN_GRID_HEIGHT + " rows by " +
                    MIN_GRID_HEIGHT + " columns");
        }

        this.dimension = dimension;


        forceFieldArea = new ForceFieldArea();

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
        for(int i = 0; i < nbPlayers; i ++){
            playerPositions.add(tempPositions.get(i));
        }
    }

    @Override
    public void setStartingPositions(List<Position> positions) {
        this.playerPositions = positions;
    }

    @Override
    public void buildWalls() {
        walls = new ArrayList<>();

        int maxNumberOfWalls = (int) Math.floor(MAX_WALL_COVERAGE_PERCENTAGE *
                (dimension.area() / MIN_WALL_LENGTH));
        int numberOfWalls = getRandomWithMax(1, maxNumberOfWalls);

        while (walls.size() < numberOfWalls && isAnotherWallPossible()) {
            buildWall();
        }
    }

    @Override
    public void buildWalls(List<Wall> walls) {
        this.walls = walls;

    }

    @Override
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

    private ArrayList<Square> getSquaresNotObstructed() {
        ArrayList<Square> result = new ArrayList<>();
        for (Square[] row : squares) {
            for (Square square : row) {

                if (!square.isObstructed()){
                    result.add(square);
                }
            }
        }
        return result;
    }

    private void placeChargedIdentityDisc() {
        ArrayList<Square> squaresNotObstructed = getSquaresNotObstructed();
        Dijkstra dijkstra = new Dijkstra(squaresNotObstructed);

        for (Square square : squaresNotObstructed) {
            List<Double> distances = new ArrayList<>();
            for(Position pos : playerPositions){
                distances.add(dijkstra.getShortestDistance(squares[pos.getVIndex()][pos.getHIndex()], square));
            }
            boolean distOk = true;
            for(Double distance : distances){
                for(Double distance2 : distances){
                    if (Math.abs(distance - distance2) > 2) {
                        distOk = false;
                    }
                }
            }

            if(distOk){
                square.addItem(new IdentityDisc(new ChargedIdentityDiscBehavior()));
                return;
            }
        }
    }

    private void placeForceFields(int numberOfItems) {
        List<ForcefieldGenerator> forceFieldGenerators = new ArrayList<>();
        for (int i = 0; i < numberOfItems; i++) {
            ForcefieldGenerator forcefieldGenerator = new ForcefieldGenerator(forceFieldArea);
            forceFieldGenerators.add(forcefieldGenerator);
        }

        for (ForcefieldGenerator forcefieldGenerator : forceFieldGenerators) {
            Square randomSquare = getRandomSquare();
            boolean added = false;
            while (!added) {
                while (randomSquare.getAvailableItems().size() != 0
                        || randomSquare.isObstructed()) {
                    randomSquare = getRandomSquare();
                }

                try {
                    forceFieldArea.placeForceField(forcefieldGenerator, randomSquare);
                    added = true;
                } catch (SquareOccupiedException exc) {
                    added = false;

                }

            }
        }

    }

    private void placeIdentityDiscs(int numberOfItems) {
        for(Position pos : playerPositions){
            Square target = squares[pos.getVIndex()][pos.getHIndex()];
            IdentityDisc disc = new IdentityDisc(new NormalIdentityDiscBehavior());
            placeItemToPlayer(target, disc, IDENTITY_DISK_PLAYER_AREA);
            numberOfItems--;
        }

        List<Item> identityDiscs = new ArrayList<>();

        for (int i = 0; i < numberOfItems; i++) {
            identityDiscs.add(new IdentityDisc(new NormalIdentityDiscBehavior()));
        }
        placeOtherItems(identityDiscs);

        placeChargedIdentityDisc();
    }

    private void placeLightMines(int numberOfItems) {
        for(Position pos : playerPositions){
            Square target = squares[pos.getVIndex()][pos.getHIndex()];
            placeItemToPlayer(target, new LightMine(), LIGHT_MINE_PLAYER_AREA);
            numberOfItems--;
        }
        List<Item> lightMines = new ArrayList<>();

        for (int i = 0; i < numberOfItems; i++) {
            lightMines.add(new LightMine());
        }
        placeOtherItems(lightMines);
    }

    private void placeTeleporters(int numberOfTeleporters) {
        Teleporter[] teleporters = new Teleporter[numberOfTeleporters];
        for (int i = 0; i < numberOfTeleporters; i++) {
            Square randomSquare = getRandomSquare();
            // todo what with player locations
            while (randomSquare.isObstructed()) {
                randomSquare = getRandomSquare();
            }

            Teleporter teleporter = new Teleporter(randomSquare);
            randomSquare.addEffect(teleporter);

            teleporters[i] = teleporter;
        }

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

    private void placeItemToPlayer(Square playerSquare, Item item, int sizeOfArea) {
        int hIndex = playerSquare.getPosition().getHIndex() - (int) Math.floor(sizeOfArea / 2);
        int vIndex = playerSquare.getPosition().getVIndex() - (int) Math.floor(sizeOfArea / 2);

        List<Position> area = new ArrayList<>();

        for(int v = vIndex; v < vIndex + sizeOfArea; v ++){
            if(v >= 0 && v < squares.length)  {
                for(int h = hIndex; h < hIndex + sizeOfArea; h++){
                    if(h >= 0 && h < squares[0].length){
                        area.add(new Position(h,v));
                    }
                }
            }
        }

        area.remove(playerSquare.getPosition());
        placeItemArea(area, item);
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

    private void placeItemArea(List<Position> area, Item item) {
        List<Position> goodPositions = new ArrayList<>();
        for (Position p : area) {
            if (!squares[p.getVIndex()][p.getHIndex()].isObstructed() && squares[p.getVIndex()][p.getHIndex()].getAvailableItems().size() == 0) {
                goodPositions.add(p);
            }
        }

        Random generator = new Random();
        Position randomPosition = goodPositions.get(generator.nextInt(goodPositions.size()));
        squares[randomPosition.getVIndex()][randomPosition.getHIndex()].addItem(item);
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

        for(Position p: playerPositions){
            if(p.getVIndex() == square.getPosition().getVIndex() && p.getHIndex() == square.getPosition().getHIndex()){
                return false;
            }
        }
        return true;
    }


    private List<Square> getAllNeighboursFromSquare(Square square) {
        List<Square> neighbourSquares = new ArrayList<>();
        for (Direction d : Direction.values()) {
            if (square.getNeighbour(d) != null) {
                neighbourSquares.add(square.getNeighbour(d));
            }
        }
        return neighbourSquares;
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
