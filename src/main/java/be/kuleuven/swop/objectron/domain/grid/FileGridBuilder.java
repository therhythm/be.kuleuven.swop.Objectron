package be.kuleuven.swop.objectron.domain.grid;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Wall;
import be.kuleuven.swop.objectron.domain.exception.FileInvalidException;
import be.kuleuven.swop.objectron.domain.grid.Dijkstra.Dijkstra;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.square.SquareObserver;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.domain.util.GridFileReader;
import be.kuleuven.swop.objectron.domain.util.Position;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : Kasper Vervaecke
 *         Date: 11/05/13
 *         Time: 15:49
 */
public class FileGridBuilder extends GridBuilder {
    private List<Square> wallSegments;
    private char[][] input;
    private int nbPlayers;

    private Map<Integer, Position> playerPositions = new HashMap<>(); //hashmap to have the right order

    public FileGridBuilder(String file, int nbPlayers) throws IOException, FileInvalidException {
        super();
        this.nbPlayers = nbPlayers;
        GridFileReader fileReader = new GridFileReader();
        input = fileReader.readGridFile(file);
        initGrid(Square.POWER_FAILURE_CHANCE);
        validateFile();
    }

    @Override
    public void setStartingPositions(List<Position> positions) {
        for (int i = 1; i <= positions.size(); i++) {
            playerPositions.put(i, positions.get(i));
        }
    }

    @Override
    public void buildWalls() {
        walls = new ArrayList<>();
        for (Square segment : wallSegments) {
            Wall wall = new Wall();
            wall.addSquare(segment);
            wall.build();
            walls.add(wall);
        }
    }

    @Override
    public void buildWalls(List<Wall> walls) {
        //To change body of implemented methods use File | Settings | File Templates.
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
        dimension = new Dimension(input.length - 2, input.length - 2);
        this.squares = new Square[dimension.getHeight()][dimension.getWidth()];
        for (int vertical = 0; vertical < squares.length; vertical++) {
            for (int horizontal = 0; horizontal < squares[0].length; horizontal++) {
                Position pos = new Position(horizontal, vertical);
                squares[vertical][horizontal] = new Square(pos, powerFailureChance);
            }
        }
        interpretInput();
        setupNeighbours();
    }

    private void validateFile() throws FileInvalidException {
        checkPaths();
        checkStartingPositions();
    }

    private void checkStartingPositions() throws FileInvalidException {
        if (nbPlayers != playerPositions.size()) {
            throw new FileInvalidException("The number of starting positions in the file does not match the number of players.");
        }
    }

    private void checkPaths() throws FileInvalidException {
        ArrayList<Square> freeSquares = new ArrayList<>();
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares.length; j++) {
                Square square = squares[i][j];
                if (!wallSegments.contains(square)) {
                    freeSquares.add(square);
                }
            }
        }
        Dijkstra dijkstra = new Dijkstra(freeSquares);
        for (int i = 0; i < freeSquares.size(); i++) {
            for (int j = 0; j < freeSquares.size(); j++) {
                if (dijkstra.getShortestDistance(freeSquares.get(i), freeSquares.get(j)) == Double.POSITIVE_INFINITY
                        && i != j) {
                    throw new FileInvalidException("There are unreachable squares in this input.");
                }
            }
        }
    }


    @Override
    public Grid buildGrid() {
        return new Grid(squares, walls, dimension, forceFieldArea, getPlayerPositions());
    }

    @Override
    protected List<Position> getPlayerPositions() {
        List<Position> positions = new ArrayList<>();
        for (int i = 1; i < playerPositions.size(); i++) {
            positions.add(playerPositions.get(i));
        }
        return positions;
    }

    private void interpretInput() {
        wallSegments = new ArrayList<>();
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                char c = input[i][j];

                if (c == '#') {
                    Square square = squares[j - 1][i - 1];
                    wallSegments.add(square);
                } else if ((c - '0') > 0 && (c - '0') < 10) { // little trick
                    playerPositions.put(c - 48, new Position(j - 1, i - 1));
                }
            }
        }
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
}
