package be.kuleuven.swop.objectron.domain.effect.powerfailure;

import be.kuleuven.swop.objectron.domain.effect.Effect;
import be.kuleuven.swop.objectron.domain.effect.EffectVisitor;
import be.kuleuven.swop.objectron.domain.effect.PowerFailureEffectVisitor;
import be.kuleuven.swop.objectron.domain.exception.GameOverException;
import be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException;
import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.gamestate.TurnSwitchObserver;
import be.kuleuven.swop.objectron.domain.movement.Movable;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.util.Observable;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas
 * Date: 24/05/13
 * Time: 8:22
 * To change this template use File | Settings | File Templates.
 */
public abstract class PowerFailure implements Effect, TurnSwitchObserver {

    protected Square square;
    private boolean actionLost;
    @Override
    public void activate(Movable movable, TurnManager manager) throws GameOverException, NotEnoughActionsException, SquareOccupiedException {
        PowerFailureEffectVisitor visitor = new PowerFailureEffectVisitor();
        for(Effect effect : square.getEffects()){
            effect.accept(visitor);
        }
        movable.getMovementStrategy().powerFailure(visitor.hasLightMine());
    }

    @Override
    public void turnEnded(Observable<TurnSwitchObserver> observable) {
        actionLost = false;
    }

    @Override
    public void actionReduced() {
        //do nothing
    }

    @Override
    public void update(Turn turn) {
        if(turn.getCurrentPlayer().getCurrentSquare().equals(square) && ! actionLost){
            actionLost = true;
            turn.addPenalty(1);
        }
    }

    @Override
    public void accept(EffectVisitor visitor) {
        //do nothing
    }

}
