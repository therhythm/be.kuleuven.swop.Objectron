package be.kuleuven.swop.objectron.domain.item.forceField;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.item.EffectActivation;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.square.Square;

/**
 * A class of ForcefieldGenerators involving a ForceFieldArea implementing Item.
 * User: Peter
 * Date: 28/04/13
 * Time: 9:50
 */
public class ForcefieldGenerator implements Item {
    private final static int MAX_IN_BAG = Integer.MAX_VALUE;

    private final String name = "Force Field";
    private ForceFieldArea forcefieldArea;

    /**
     * Initialize this ForcefieldGenerator with a given ForceFieldArea.
     * @param forcefieldArea
     *        The ForceFieldArea for this ForcefieldGenerator.
     */
    public ForcefieldGenerator(ForceFieldArea forcefieldArea) {
        this.forcefieldArea = forcefieldArea;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void place(Square targetSquare) throws SquareOccupiedException {
        forcefieldArea.placeForceField(this, targetSquare);
    }

    @Override
    public void throwMe(Square sourceSquare, Direction targetDirection, TurnManager turnManager) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void pickedUp() {
        forcefieldArea.pickUpForceField(this);
    }

    @Override
    public int getMaxInBag() {
        return MAX_IN_BAG;
    }

    @Override
    public void effectActivated(EffectActivation activation) {
        // I don't do anything when an effect is activated
    }
}
