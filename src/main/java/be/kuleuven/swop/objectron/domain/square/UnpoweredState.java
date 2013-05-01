package be.kuleuven.swop.objectron.domain.square;

import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.item.effect.Effect;
import be.kuleuven.swop.objectron.domain.item.effect.PowerFailureEffectVisitor;

/**
 * @author : Nik Torfs
 *         Date: 13/03/13
 *         Time: 03:01
 */
public class UnpoweredState implements PowerState {
    public static final int TURNS_WITHOUT_POWER = 3;
    private static final int ACTIONS_TO_REDUCE = 1;

    private Square context;
    private int remainingTurns;

    public UnpoweredState(Square context) {
        this.context = context;
        this.remainingTurns = TURNS_WITHOUT_POWER;
    }

    @Override
    public void newTurn(Turn currentTurn) {
        if (currentTurn.getCurrentPlayer().getCurrentSquare().equals(context)) {
            currentTurn.reduceRemainingActions(ACTIONS_TO_REDUCE);
        }
        remainingTurns--;
        if (remainingTurns == 0) {
            context.transitionState(new PoweredState(context));
            context.notifyPowered();
        }
    }

    @Override
    public void stepOn(TurnManager turnManager) {
        PowerFailureEffectVisitor visitor = new PowerFailureEffectVisitor();
        for(Effect effect : context.getEffects()){
            effect.accept(visitor);
        }

        if (visitor.hasLightMine()) {
            turnManager.getCurrentTurn().reduceRemainingActions(ACTIONS_TO_REDUCE);
        } else {
            turnManager.endTurn();
        }
    }

    @Override
    public void powerFailure() {
        remainingTurns = TURNS_WITHOUT_POWER;
    }
}
