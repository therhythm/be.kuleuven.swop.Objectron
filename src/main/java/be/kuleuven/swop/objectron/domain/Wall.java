package be.kuleuven.swop.objectron.domain;

import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.movement.Movement;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.util.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * A class of Walls implementing Obstructions.
 * Created with IntelliJ IDEA.
 * User: thomas
 * Date: 28/02/13
 * Time: 21:45
 */
public class Wall implements Obstruction {

    private List<Square> squares = new ArrayList<Square>();

    public void addSquare(Square square) {
        squares.add(square);
    }

    public int getLength() {
        return squares.size();
    }

    /**
     * Add an obstruction to every square in this wall.
     * @post All squares in the wall are obstructed.
     *       | for (square:squares)
     *       |  new.square.isObstructed()
     */
    public void build() {
        for (Square square : squares) {
            square.addObstruction(this);
        }
    }

    /**
     * Get a list of the positions in this wall.
     * @return A list of positions.
     */
    public List<Position> getWallViewModel() {
        List<Position> squaresVm = new ArrayList<Position>();
        for (Square s : this.squares) {
            squaresVm.add(s.getPosition());
        }
        return squaresVm;
    }

    @Override
    public void hit(Movement movement) throws InvalidMoveException {
        movement.hitWall(this);
    }
}
