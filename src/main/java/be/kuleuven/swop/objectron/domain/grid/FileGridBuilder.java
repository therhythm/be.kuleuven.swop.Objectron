package be.kuleuven.swop.objectron.domain.grid;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Wall;
import be.kuleuven.swop.objectron.domain.item.forceField.ForceFieldArea;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.square.SquareObserver;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.domain.util.GridFileReader;
import be.kuleuven.swop.objectron.domain.util.Position;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Kasper Vervaecke
 *         Date: 11/05/13
 *         Time: 15:49
 */
public class FileGridBuilder implements GridBuilder {
    private Dimension dimension;
    private Position playerOnePosition;
    private Position playerTwoPosition;
    private List<Wall> walls;
    private List<Square> wallSegments;
    private Square[][] squares;
    private ForceFieldArea forceFieldArea;
    private char[][] input;

    public FileGridBuilder(String file) throws IOException {
        GridFileReader fileReader = new GridFileReader();
        input = fileReader.readGridFile(file);
        forceFieldArea = new ForceFieldArea();
    }

    @Override
    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    @Override
    public void setStartingPositions(Position playerOnePosition, Position playerTwoPosition) {
        this.playerOnePosition = playerOnePosition;
        this.playerTwoPosition = playerTwoPosition;
    }

    @Override
    public void buildWalls() {
        walls = new ArrayList<>();
        for (Square segment: wallSegments) {
            Wall wall = new Wall();
            wall.addSquare(segment);
            wall.build();
            walls.add(wall);
        }
    }

    @Override
    public void buildWalls(List<Wall> walls) {
        //Building pre-defined walls is not possible with file-generated grids.
    }

    @Override
    public void buildItems() {

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
        setDimension(new Dimension(input.length-2, input.length-2));
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
    public Grid getGrid() {
        return new Grid(squares, walls, dimension, forceFieldArea);
    }

    private void interpretInput(char[][] input) {
        wallSegments = new ArrayList<>();
        Position playerOnePosition = null;
        Position playerTwoPosition = null;
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input.length; j++) {
                char c = input[i][j];
                switch (c) {
                    case '#':
                        Square square = squares[j - 1][i - 1];
                        wallSegments.add(square);
                        break;
                    case '1':
                        playerOnePosition = new Position(j - 1,i - 1);
                        break;
                    case '2':
                        playerTwoPosition = new Position(j - 1,i - 1);
                        break;
                }
            }
        }
        setStartingPositions(playerOnePosition, playerTwoPosition);
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

    private boolean isValidPosition(Position pos) {
        return pos.getHIndex() > -1
                && pos.getHIndex() < dimension.getWidth()
                && pos.getVIndex() > -1
                && pos.getVIndex() < dimension.getHeight();
    }

    public Dimension getDimension() {
        return dimension;
    }

    public Position getPlayerOnePosition() {
        return playerOnePosition;
    }

    public Position getPlayerTwoPosition() {
        return playerTwoPosition;
    }
}
