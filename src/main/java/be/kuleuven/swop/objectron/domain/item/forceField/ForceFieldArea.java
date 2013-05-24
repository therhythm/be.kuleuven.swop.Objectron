package be.kuleuven.swop.objectron.domain.item.forceField;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.gamestate.TurnObserver;
import be.kuleuven.swop.objectron.domain.gamestate.TurnSwitchObserver;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.util.Observable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Peter
 * Date: 28/04/13
 * Time: 9:49
 * To change this template use File | Settings | File Templates.
 */
public class ForceFieldArea implements TurnSwitchObserver {
    private final int maxRange = 3;
    private Map<ForcefieldGenerator, Square> listForceFieldGenerators;
    private List<ForceField> listForceFields;

    public ForceFieldArea() {
        listForceFieldGenerators = new HashMap<ForcefieldGenerator, Square>();
        listForceFields = new ArrayList<ForceField>();
    }

    /**
     * @param changedForceFieldGenerator
     * @param currentSquare
     * @throws SquareOccupiedException Thrown when the square already contains a ForceFieldGenerator
     * @effect the square will be assigned to the "changedForceFieldGenerator" in the list listForceFieldGenerators
     * @effect if there is a focrefieldGenerator within range and fullfills the requirements to create a forcefield,
     * a forcefield will be created
     */
    public void placeForceField(Item changedForceFieldGenerator, Square currentSquare) throws SquareOccupiedException {
        if (listForceFieldGenerators.values().contains(currentSquare))
            throw new SquareOccupiedException("This square already contains a force field");
        currentSquare.addItem(changedForceFieldGenerator);
        listForceFieldGenerators.put((ForcefieldGenerator) changedForceFieldGenerator, currentSquare);
        for (ForcefieldGenerator forceField : listForceFieldGenerators.keySet()) {
            if (!changedForceFieldGenerator.equals(forceField)) {

                checkForceField((ForcefieldGenerator) changedForceFieldGenerator, forceField);
            }
        }
    }

    /**
     * @param changedForceFieldGenerator
     * @Effect The corresponding square in the "listForceFields" is set to null
     * @Effect if there is a ForceField that contains that ForceFieldGenerator,   that forcefield is removed from the
     * list forceFields.
     */
    public void pickUpForceField(Item changedForceFieldGenerator) {
        listForceFieldGenerators.put((ForcefieldGenerator) changedForceFieldGenerator, null);
        List<ForceField> forcefieldPairsCopy = new ArrayList<ForceField>();
        forcefieldPairsCopy.addAll(listForceFields);
        for (ForceField forceFieldPair : forcefieldPairsCopy) {
            if (forceFieldPair.contains((ForcefieldGenerator) changedForceFieldGenerator)) {
                forceFieldPair.prepareToRemove();
                listForceFields.remove(forceFieldPair);
            }
        }
    }


    private void checkForceField(ForcefieldGenerator forceField1, ForcefieldGenerator forceField2) {
        boolean contains = false;
        for (ForceField forceFieldPair : listForceFields) {
            if (forceFieldPair.contains(forceField1) && forceFieldPair.contains(forceField2))
                contains = true;
        }
        if (!contains)
            for (Direction direction : Direction.values()) {
                if (withinRange(listForceFieldGenerators.get(forceField1), listForceFieldGenerators.get(forceField2),
                        direction)) {
                    List<Square> squaresBetween = getSquaresBetween(listForceFieldGenerators.get(forceField1),
                            listForceFieldGenerators.get(forceField2), direction);
                    ForceField forceFieldPair = new ForceField(forceField1, forceField2, squaresBetween);
                    listForceFields.add(forceFieldPair);
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

    @Override
    public void turnEnded(Observable<TurnSwitchObserver> observable) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void update(Turn turn) {

    }

    @Override
    public void actionReduced() {

    }

    @Override
    public void actionHappened(TurnManager turnManager) {
        for (ForceField forceFieldPair : listForceFields) {
            forceFieldPair.update(turnManager);
        }
    }
}
