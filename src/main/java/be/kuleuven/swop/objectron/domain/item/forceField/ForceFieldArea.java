package be.kuleuven.swop.objectron.domain.item.forceField;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.util.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Peter
 * Date: 28/04/13
 * Time: 9:49
 * To change this template use File | Settings | File Templates.
 */
public class ForceFieldArea implements ForceFieldObserver {
    private final int maxRange = 3;
    private Map<ForceField, Square> listForceFields;
    private List<ForceFieldPair> listForceFieldPairs;
    private Grid grid;

    @Override
    public void update() {
        for (ForceFieldPair forcefieldPair : listForceFieldPairs) {
            forcefieldPair.update();
        }
    }

    public void addForceField(Square square, ForceField forceField) {
        listForceFields.put(forceField, square);

    }

    public void placeForceField(ForceField changedForceField, Square currentSquare) {
        updateForceField(changedForceField,currentSquare);
        for (ForceField forceField : listForceFields.keySet()) {
            if (!changedForceField.equals(forceField)) {
                checkForceField(changedForceField, forceField);
            }
        }
    }

    private void updateForceField(ForceField changedForceField, Square currentSquare) {
        listForceFields.remove(changedForceField);
        listForceFields.put(changedForceField, currentSquare);
    }

    private void checkForceField(ForceField forceField1, ForceField forceField2) {
        for (Direction direction : Direction.values()) {
            if (withinRange(listForceFields.get(forceField1), listForceFields.get(forceField2),direction)){
                List<Square> squaresBetween = getSquaresBetween(listForceFields.get(forceField1), listForceFields.get(forceField2),direction);
                ForceFieldPair forceFieldPair = new ForceFieldPair(forceField1,forceField2,squaresBetween);
            }
        }
    }

    private List<Square> getSquaresBetween(Square square1, Square square2,Direction direction) {
        List<Square> result = new ArrayList<Square>();
       result.add(square1);

        for (int i = 0; i < maxRange; i++) {
            Square neighbor = square1.getNeighbour(direction);
             result.add(neighbor);
            if(square1.equals(square2))
                return result;
        }
        return result;
    }

    private boolean withinRange(Square currentSquare, Square square, Direction direction) {
        for (int i = 0; i < maxRange; i++) {
            Square neighbor = currentSquare.getNeighbour(direction);
            if (neighbor == null)
                return false;
            if(neighbor.equals(square))
                return true;
        }
        return false;
    }
}
