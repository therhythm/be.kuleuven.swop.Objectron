package be.kuleuven.swop.objectron.domain;

import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.movement.Movement;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.util.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: thomas
 * Date: 28/02/13
 * Time: 21:45
 * To change this template use File | Settings | File Templates.
 */
public class Wall implements Obstruction {

    private List<Square> squares = new ArrayList<Square>();

    public void addSquare(Square square) {
        squares.add(square);
    }

    public int getLength() {
        return squares.size();
    }

    public void build() {
        for (Square square : squares) {
            square.addObstruction(this);
        }
    }

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
