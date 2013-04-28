package be.kuleuven.swop.objectron.domain.item.forceField;

import be.kuleuven.swop.objectron.domain.square.ForceFieldSquareState;
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
    private ForceField forceField1;
    private ForceField forceField2;
    private final int turnSwitch = 2;
    private int currentTurnSwitch;
    private List<Square> affectedSquares;
    private State state = State.ACTIVE;

    public ForceFieldPair(ForceField forceField1, ForceField forceField2, List<Square> squaresBetween) {

        this.forceField1 = forceField1;
        this.forceField2 = forceField2;
        this.affectedSquares = squaresBetween;
    }

    private void activate() {
        state = State.ACTIVE;
        for(Square square : affectedSquares){
           // square.transitionPowerState(new ForceFieldSquareState());
        }
    }

    private void disactivate() {
          state = State.DISACTIVE;
    }

    private void switchActivation(){
        currentTurnSwitch = turnSwitch;
        if(state==State.ACTIVE)
            disactivate();
        else
            activate();

    }

    public void update() {
        currentTurnSwitch--;
        if(currentTurnSwitch==0)
            this.switchActivation();
    }


    private enum State {
        ACTIVE,DISACTIVE;
    }
}
