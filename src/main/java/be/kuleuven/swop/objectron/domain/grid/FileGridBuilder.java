package be.kuleuven.swop.objectron.domain.grid;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Wall;
import be.kuleuven.swop.objectron.domain.exception.SquareUnreachableException;
import be.kuleuven.swop.objectron.domain.exception.TooManyPlayersException;
import be.kuleuven.swop.objectron.domain.grid.Dijkstra.Dijkstra;
import be.kuleuven.swop.objectron.domain.exception.InvalidFileException;
import be.kuleuven.swop.objectron.domain.square.Square;
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
    private final static int MAX_PLAYERS = 9;
    private int max_players = MAX_PLAYERS;
    private List<Square> wallSegments;
    private char[][] input;
    private int nbPlayers;

    private Map<Integer, Position> playerPositions = new HashMap<>(); //hashmap to have the right order


    public FileGridBuilder(String file, int nbPlayers) throws InvalidFileException, TooManyPlayersException{
        super();
        this.nbPlayers = nbPlayers;
        try {
            GridFileReader fileReader = new GridFileReader();
            input = fileReader.readGridFile(file);
        } catch (IOException e) {
            throw new InvalidFileException("The specified file wasn't usable");
        }

        initGrid(powerFailureChance);
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
    public void buildWalls(List<List<Position>> walls) {

    }

    @Override
    public void initGrid(int powerFailureChance) {
        this.powerFailureChance = powerFailureChance;
        dimension = new Dimension(input.length - 2, input[0].length - 2);
        this.squares = new Square[dimension.getHeight()][dimension.getWidth()];
        for (int vertical = 0; vertical < squares.length; vertical++) {
            for (int horizontal = 0; horizontal < squares[0].length; horizontal++) {
                Position pos = new Position(horizontal, vertical);
                squares[vertical][horizontal] = new Square(pos);
            }
        }
        interpretInput();
        setupNeighbours();
    }

    private void validateFile() throws InvalidFileException, TooManyPlayersException {
        checkNbPlayers();
        checkPaths();
        checkStartingPositions();
    }

    private void checkNbPlayers() throws TooManyPlayersException {
        if(nbPlayers > max_players){
            throw new TooManyPlayersException("You can only play with " + max_players + " on this grid");
        }
    }

    private void checkStartingPositions() throws InvalidFileException {
        if (nbPlayers != playerPositions.size()) {
            throw new InvalidFileException("The number of starting positions in the file does not match the number of players.");
        }
    }

    private void checkPaths() throws InvalidFileException {
        ArrayList<Square> freeSquares = new ArrayList<>();
        for (Square[] row : squares) {
            for (Square square : row) {
                if (!wallSegments.contains(square)) {
                    freeSquares.add(square);
                }
            }
        }
        Dijkstra dijkstra = new Dijkstra(freeSquares);
        for (int i = 0; i < freeSquares.size(); i++) {
            for (int j = 0; j < freeSquares.size(); j++) {
                try {
                    if (dijkstra.getShortestDistance(freeSquares.get(i), freeSquares.get(j)) == Double.POSITIVE_INFINITY
                            && i != j) {
                        throw new InvalidFileException("There are unreachable squares in this input.");
                    }
                } catch (SquareUnreachableException e) {
                    throw new InvalidFileException("There are unreachable squares in this input.");
                }
            }
        }
    }


    @Override
    public Grid buildGrid() {
        return new Grid(squares, walls, dimension, forceFieldArea, getPlayerPositions(), powerFailureChance);
    }

    @Override
    protected List<Position> getPlayerPositions() {
        List<Position> positions = new ArrayList<>();
        for (int i = 0; i < playerPositions.size(); i++) {
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
                    playerPositions.put((c - '0') - 1, new Position(j - 1, i - 1));
                }
            }
        }
        max_players = playerPositions.size();
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
