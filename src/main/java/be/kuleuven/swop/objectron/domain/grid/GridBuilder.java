package be.kuleuven.swop.objectron.domain.grid;

import be.kuleuven.swop.objectron.domain.Wall;
import be.kuleuven.swop.objectron.domain.effect.Teleporter;
import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.exception.SquareUnreachableException;
import be.kuleuven.swop.objectron.domain.exception.TooManyPlayersException;
import be.kuleuven.swop.objectron.domain.grid.Dijkstra.Dijkstra;
import be.kuleuven.swop.objectron.domain.item.*;
import be.kuleuven.swop.objectron.domain.item.forceField.ForceFieldArea;
import be.kuleuven.swop.objectron.domain.item.forceField.ForcefieldGenerator;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.domain.util.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author : Kasper Vervaecke
 *         Date: 13/05/13
 *         Time: 10:13
 */

public abstract class GridBuilder {
    public static final double PERCENTAGE_OF_TELEPORTERS = 0.03;
    public static final double PERCENTAGE_OF_LIGHTMINES = 0.02;
    public static final double PERCENTAGE_OF_IDENTITYDISCS = 0.02;
    public static final double PERCENTAGE_OF_FORCEFIELDS = 0.07;
    private static final int IDENTITY_DISK_PLAYER_AREA = 7;
    private static final int LIGHT_MINE_PLAYER_AREA = 5;
    public static final int POWER_FAILURE_CHANCE = 1;

    protected int powerFailureChance = POWER_FAILURE_CHANCE;

    protected Dimension dimension;
    protected Square[][] squares;
    protected ForceFieldArea forceFieldArea;
    protected List<Wall> walls;

    public GridBuilder() {
        forceFieldArea = new ForceFieldArea();
    }

    public abstract void setStartingPositions(List<Position> positions) throws TooManyPlayersException;

    public abstract void buildWalls();

    public abstract void buildWalls(List<List<Position>> wallPositions);

    public abstract void initGrid(int powerFailureChance);

    public abstract Grid buildGrid();

    public void buildItems() {
        int numberOfLightmines = (int) Math.ceil(GeneratedGridBuilder.PERCENTAGE_OF_LIGHTMINES * dimension.area());
        int numberOfTeleporters = (int) Math.ceil(GeneratedGridBuilder.PERCENTAGE_OF_TELEPORTERS * dimension.area());
        int numberOfIdentityDiscs = (int) Math.ceil(GeneratedGridBuilder.PERCENTAGE_OF_IDENTITYDISCS * dimension.area
                ());
        int numberOfForceFields = (int) Math.floor(GeneratedGridBuilder.PERCENTAGE_OF_FORCEFIELDS * dimension.area());


        placeLightMines(numberOfLightmines);
        placeTeleporters(numberOfTeleporters);
        placeIdentityDiscs(numberOfIdentityDiscs);
        placeForceFields(numberOfForceFields);
    }

    protected abstract List<Position> getPlayerPositions();

    private void placeChargedIdentityDisc() {
        ArrayList<Square> squaresNotObstructed = getSquaresNotObstructed();
        Dijkstra dijkstra = new Dijkstra(squaresNotObstructed);

        for (Square square : squaresNotObstructed) {
            List<Double> distances = new ArrayList<>();

            for (Position pos : getPlayerPositions()) {
                try {
                    distances.add(dijkstra.getShortestDistance(squares[pos.getVIndex()][pos.getHIndex()], square));
                } catch (SquareUnreachableException e) {
                    break;
                }
            }
            boolean distOk = true;
            for (Double distance : distances) {
                for (Double distance2 : distances) {
                    if (Math.abs(distance - distance2) > 2) {
                        distOk = false;
                    }
                }
            }

            if (distOk) {
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
        for (Position pos : getPlayerPositions()) {
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
        for (Position pos : getPlayerPositions()) {
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

    private void placeItemToPlayer(Square playerSquare, Item item, int sizeOfArea) {
        int hIndex = playerSquare.getPosition().getHIndex() - (int) Math.floor(sizeOfArea / 2);
        int vIndex = playerSquare.getPosition().getVIndex() - (int) Math.floor(sizeOfArea / 2);

        List<Position> area = new ArrayList<>();

        for (int v = vIndex; v < vIndex + sizeOfArea; v++) {
            if (v >= 0 && v < squares.length) {
                for (int h = hIndex; h < hIndex + sizeOfArea; h++) {
                    if (h >= 0 && h < squares[0].length) {
                        area.add(new Position(h, v));
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
            if (!squares[p.getVIndex()][p.getHIndex()].isObstructed() && squares[p.getVIndex()][p.getHIndex()]
                    .getAvailableItems().size() == 0) {
                goodPositions.add(p);
            }
        }

        Random generator = new Random();
        Position randomPosition = goodPositions.get(generator.nextInt(goodPositions.size()));
        squares[randomPosition.getVIndex()][randomPosition.getHIndex()].addItem(item);
    }

    protected Square getRandomSquare() {
        int verticalIndex = getRandomWithMax(0, dimension.getHeight() - 1);
        int horizontalIndex = getRandomWithMax(0, dimension.getWidth() - 1);
        return squares[verticalIndex][horizontalIndex];
    }

    private ArrayList<Square> getSquaresNotObstructed() {
        ArrayList<Square> result = new ArrayList<>();
        for (Square[] row : squares) {
            for (Square square : row) {

                if (!square.isObstructed()) {
                    result.add(square);
                }
            }
        }
        return result;
    }

    protected int getRandomWithMax(double min, double max) {
        double rand = min + Math.random() * (max - min + 1);
        return (int) Math.floor(rand);
    }

    protected boolean isValidPosition(Position pos) {
        return pos.getHIndex() > -1
                && pos.getHIndex() < dimension.getWidth()
                && pos.getVIndex() > -1
                && pos.getVIndex() < dimension.getHeight();
    }
}
