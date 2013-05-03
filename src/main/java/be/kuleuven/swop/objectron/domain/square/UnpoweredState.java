package be.kuleuven.swop.objectron.domain.square;

import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.item.effect.Effect;
import be.kuleuven.swop.objectron.domain.item.effect.PowerFailureEffectVisitor;
import be.kuleuven.swop.objectron.domain.movement.MovementStrategy;

/**
 * @author : Nik Torfs
 *         Date: 13/03/13
 *         Time: 03:01
 */
public class UnpoweredState implements PowerState {
    public static final int ACTIONS_TO_REDUCE = 1;

    private Square context;
    private int remainingTurns = 0;
    private int remainingActions = 0;

    public UnpoweredState(Square context) {
        this.context = context;
    }

    @Override
    public void newTurn(Turn currentTurn) {
        if (currentTurn.getCurrentPlayer().getCurrentSquare().equals(context)) {
            currentTurn.reduceRemainingActions(ACTIONS_TO_REDUCE);
        }
        remainingTurns--;
        checkTransition();
    }

    private void checkTransition() {
        if (remainingTurns <= 0 && remainingActions <= 0) {
            remainingActions = 0;
            remainingTurns = 0;
            context.transitionState(new PoweredState(context));
            context.notifyPowered();
        }
    }

    @Override
    public void stepOn(MovementStrategy movementStrategy) {
        PowerFailureEffectVisitor visitor = new PowerFailureEffectVisitor();
        for(Effect effect : context.getEffects()){
            effect.accept(visitor);
        }

        movementStrategy.powerFailure(visitor.hasLightMine());
    }

    @Override
    public void powerFailure(int turns, int actions) {
        remainingTurns += turns;
        remainingActions += actions;
    }

    @Override
    public void endAction() {
        remainingActions--;
        checkTransition();
    }
}
