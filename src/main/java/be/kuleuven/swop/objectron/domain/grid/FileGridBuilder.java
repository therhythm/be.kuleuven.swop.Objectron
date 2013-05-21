package be.kuleuven.swop.objectron.domain.grid;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Wall;
import be.kuleuven.swop.objectron.domain.exception.InvalidFileException;
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

    private Map<Integer, Position> playerPositions = new HashMap<>(); //hashmap to have the right order

    public FileGridBuilder(String file) throws InvalidFileException {
        super();
        GridFileReader fileReader = new GridFileReader();
        try {
            input = fileReader.readGridFile(file);
        } catch (IOException e) {
            throw new InvalidFileException("The specified file wasn't usable");
        }
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
    public void buildWalls(List<List<Position>> walls) {

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
        setupNeighbours();
        interpretInput(input);
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

    private void interpretInput(char[][] input) {
        wallSegments = new ArrayList<>();
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input.length; j++) {
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
