package be.kuleuven.swop.objectron.model;


import java.util.ArrayList;
import java.util.List;

/**
 * @author : Nik Torfs
 *         Date: 22/02/13
 *         Time: 07:04
 */
public class Grid {

    private Square[][] squares;
    private List<Wall> walls;

    public Grid(int width, int height) {
        this.squares = new Square[height][width];

        setupGrid();
    }

    public void makeMove(Direction direction, Player player) throws InvalidMoveException {
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
    private void setupGrid() {
        setupNeighbours();
        setupWalls();
        setupItems();
    }

    private void setupItems() {
        //To change body of created methods use File | Settings | File Templates.
    }

    private void setupWalls() {
        walls = new ArrayList<Wall>();

        int maxNumberOfWalls = (int) Math.floor(0.2 * ((squares.length * squares[0].length) / 2));
        int numberOfWalls = getRandomWithMax(maxNumberOfWalls + 1);
        if (numberOfWalls == 0) {
            numberOfWalls = 1;
        }

        boolean twentyPercentReached = false;
        while (walls.size() < numberOfWalls && !twentyPercentReached) {
            twentyPercentReached = makeWall();
        }

        System.out.println("wallpercentage: " + calculateWallPercentage(0));

        for(int j = 0;j < squares[0].length;j++) {
            for(int i = 0;i < squares.length;i++){
               if(squares[i][j].isObstructed())
                   System.out.print("1 ");
               else
                   System.out.print("0 ");
            }
        System.out.print("\n");
        }


    }

    private double calculateWallPercentage(int extraWalls) {
        for (Wall w : walls) {
            extraWalls += w.getLength();
        }
        double wallArea = (double) extraWalls;
        double gridArea = (double) squares.length * squares[0].length;
        double percentage = wallArea / gridArea;
        return percentage;
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
        if (rand < 0.5) {
            direction = Direction.UP;
        } else {
            direction = Direction.LEFT;
        }

        //max lengte wall bepalen
        int maxLength;
        if (direction == Direction.UP) {
            maxLength = getRandomWithMax(squares.length / 2);
        } else {
            maxLength = getRandomWithMax(squares[0].length / 2);
        }

        generateValidWall(randomSquare, direction, maxLength);
        return !(calculateWallPercentage(1) <= 0.2);
    }

    private Square getRandomSquare() {
        int vertIndex = getRandomWithMax(squares.length);
        int horIndex = getRandomWithMax(squares[0].length);

        return getSquareAtPosition(vertIndex, horIndex);
    }

    private boolean isValidWallPosition(Square square) {
        boolean valid = true;
        for (Direction d : Direction.values()) {
              if(square.getNeighbour(d) != null) {

                if (square.getNeighbour(d).isObstructed()) {
                    valid = false;
                }
              }
        }
        return valid;
    }

    private int getRandomWithMax(double max) {
        double rand = Math.random() * max;
        return (int) Math.floor(rand);
    }

    private void generateValidWall(Square currentSquare, Direction direction, int maxLength) {
        int length = 1;
        boolean notInvalid = true;
        Wall wall = new Wall();
        wall.addSquare(currentSquare);

        while (length <= maxLength && notInvalid && calculateWallPercentage(length + 1) <= 0.2) {
            currentSquare = currentSquare.getNeighbour(direction);
            if (currentSquare != null)
                if (isValidWallPosition(currentSquare)) {
                    wall.addSquare(currentSquare);
                    length++;
                } else {
                    notInvalid = false;
                }
            else
                notInvalid = false;
        }

        if (length >= 2) {
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

    public void initializeGrid() {
    }
}
