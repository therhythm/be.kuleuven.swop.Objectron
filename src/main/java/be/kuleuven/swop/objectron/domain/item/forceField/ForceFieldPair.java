package be.kuleuven.swop.objectron.domain.item.forceField;

import be.kuleuven.swop.objectron.domain.square.Square;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Peter
 * Date: 28/04/13
 * Time: 9:49
 * To change this template use File | Settings | File Templates.
 */
public class ForceFieldPair {
    public static final int TURNSWITCH = 2;

    private ForceField forceField1;
    private ForceField forceField2;
    private int currentTurnSwitch = TURNSWITCH;
    private List<Square> affectedSquares;
    private boolean active;

    public ForceFieldPair(ForceField forceField1, ForceField forceField2, List<Square> squaresBetween) {

        this.forceField1 = forceField1;
        this.forceField2 = forceField2;
        this.affectedSquares = squaresBetween;

        deactivate();
    }

    private void activate() {
        active = true;
        for (Square square : affectedSquares) {
            square.setObstructed(true);
        }
    }

    private void deactivate() {
        active = false;
        for (Square square : affectedSquares) {
            square.setObstructed(false);
        }
    }

    private void switchActivation() {
        currentTurnSwitch = TURNSWITCH;
        if (active)
            deactivate();
        else
            activate();

    }

    public void update() {
        currentTurnSwitch--;
        if (currentTurnSwitch == 0)
            this.switchActivation();

    }

    public void prepareToRemove() {
        this.deactivate();
    }

    public boolean contains(ForceField forcefield) {
        return forcefield.equals(forceField1) || forcefield.equals(forceField2);
    }
}
