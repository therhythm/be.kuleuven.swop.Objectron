package be.kuleuven.swop.objectron.domain.item.effect;

import be.kuleuven.swop.objectron.domain.effect.Effect;
import be.kuleuven.swop.objectron.domain.effect.EffectVisitor;
import be.kuleuven.swop.objectron.domain.effect.PowerFailureEffectVisitor;
import be.kuleuven.swop.objectron.domain.exception.GameOverException;
import be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException;
import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.gamestate.TurnObserver;
import be.kuleuven.swop.objectron.domain.gamestate.TurnSwitchObserver;
import be.kuleuven.swop.objectron.domain.movement.Movable;
import be.kuleuven.swop.objectron.domain.square.Square;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas
 * Date: 19/05/13
 * Time: 17:58
 * To change this template use File | Settings | File Templates.
 */
public class TertiaryPowerFailure implements Effect, TurnSwitchObserver {

    public static final int PF_TERTIARY_ACTIONS = 1;
    private int actionsLeft = PF_TERTIARY_ACTIONS;
    private boolean active = true;
    private Square square;

    public TertiaryPowerFailure(Square square) {
        this.square = square;
        this.square.notifyPowerFailure();
        this.square.addEffect(this);
    }

    @Override
    public void activate(Movable movable, TurnManager manager) throws GameOverException, NotEnoughActionsException, SquareOccupiedException {
        if(active){
            PowerFailureEffectVisitor visitor = new PowerFailureEffectVisitor();
            for(Effect effect : square.getEffects()){
                effect.accept(visitor);
            }
            movable.getMovementStrategy().powerFailure(visitor.hasLightMine());
        }
    }

    @Override
    public void accept(EffectVisitor visitor) {
        //do nothing
    }

    @Override
    public void turnEnded(Turn newTurn) {
        //do nothing
    }

    @Override
    public void update(Turn turn) {
        //do nothing
    }


    @Override
    public void actionReduced() {
        //do nothing
    }

    @Override
    public void actionHappened() {
        actionsLeft --;
        if(actionsLeft == 0){
            this.active = false;
            square.notifyPowered();
        }
    }
}
