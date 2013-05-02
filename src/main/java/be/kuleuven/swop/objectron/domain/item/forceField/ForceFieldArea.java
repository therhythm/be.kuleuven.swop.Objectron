package be.kuleuven.swop.objectron.domain.item.forceField;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.square.Square;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Peter
 * Date: 28/04/13
 * Time: 9:49
 * To change this template use File | Settings | File Templates.
 */
public class ForceFieldArea implements TurnObserver {
    private final int maxRange = 3;
    private Map<ForceField, Square> listForceFields;
    private List<ForceFieldPair> listForceFieldPairs;

    public ForceFieldArea() {
        listForceFields = new HashMap<ForceField, Square>();
        listForceFieldPairs = new ArrayList<ForceFieldPair>();
    }

    @Override
    public void update() {
        for (ForceFieldPair forcefieldPair : listForceFieldPairs) {
            forcefieldPair.update();
        }
    }


    public void placeForceField(Item changedForceField, Square currentSquare) throws SquareOccupiedException {
        if(listForceFields.values().contains(currentSquare))
            throw new SquareOccupiedException("This square already contains a force field");
        currentSquare.addItem(changedForceField);
        listForceFields.put((ForceField) changedForceField, currentSquare);
        for (ForceField forceField : listForceFields.keySet()) {
            if (!changedForceField.equals(forceField)) {

                checkForceField((ForceField) changedForceField, forceField);
            }
        }
    }

    public void pickUpForceField(Item changedForceField) {
        listForceFields.put((ForceField) changedForceField, null);
        List<ForceFieldPair> forcefieldPairsCopy = new ArrayList<ForceFieldPair>();
        forcefieldPairsCopy.addAll(listForceFieldPairs);
        for (ForceFieldPair forceFieldPair : forcefieldPairsCopy) {
            if (forceFieldPair.contains((ForceField) changedForceField)) {
                forceFieldPair.prepareToRemove();
                listForceFieldPairs.remove(forceFieldPair);
            }
        }
    }


    private void checkForceField(ForceField forceField1, ForceField forceField2) {
        boolean contains = false;
        for (ForceFieldPair forceFieldPair : listForceFieldPairs) {
            if (forceFieldPair.contains(forceField1) && forceFieldPair.contains(forceField2))
                contains = true;
        }
        if (!contains)
            for (Direction direction : Direction.values()) {
                if (withinRange(listForceFields.get(forceField1), listForceFields.get(forceField2), direction)) {
                    List<Square> squaresBetween = getSquaresBetween(listForceFields.get(forceField1), listForceFields.get(forceField2), direction);
                    ForceFieldPair forceFieldPair = new ForceFieldPair(forceField1, forceField2, squaresBetween);
                    listForceFieldPairs.add(forceFieldPair);
                }
            }
    }

    private List<Square> getSquaresBetween(Square square1, Square square2, Direction direction) {
        List<Square> result = new ArrayList<Square>();
        result.add(square1);
        Square neighbor = square1;
        for (int i = 0; i < maxRange; i++) {
            neighbor = neighbor.getNeighbour(direction);
            result.add(neighbor);
            if (neighbor.equals(square2))
                return result;
        }
        return result;
    }

    private boolean withinRange(Square currentSquare, Square square, Direction direction) {
        Square neighbor = currentSquare;
        for (int i = 0; i < maxRange; i++) {
            neighbor = neighbor.getNeighbour(direction);
            if (neighbor == null)
                return false;
            //TODO uncomment this test when obstructed is changed
            //if (neighbor.isObstructed())
            //   return false;
            if (neighbor.equals(square))
                return true;
        }
        return false;
    }
}
