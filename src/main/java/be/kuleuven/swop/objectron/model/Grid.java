package be.kuleuven.swop.objectron.model;

/**
 * @author : Nik Torfs
 *         Date: 22/02/13
 *         Time: 07:04
 */
public class Grid {

    private Square[][] squares;

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
        //To change body of created methods use File | Settings | File Templates.
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
