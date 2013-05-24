package be.kuleuven.swop.objectron.domain.effect.powerfailure;

import be.kuleuven.swop.objectron.domain.Player;
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
import be.kuleuven.swop.objectron.domain.util.Observable;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas
 * Date: 19/05/13
 * Time: 17:58
 * To change this template use File | Settings | File Templates.
 */
public class TertiaryPowerFailure extends PowerFailure {

    public static final int PF_TERTIARY_ACTIONS = 1;
    private int actionsLeft = PF_TERTIARY_ACTIONS;

    public TertiaryPowerFailure(Square square, Observable<TurnSwitchObserver> observable) {
        super();
        this.square = square;
        this.square.addEffect(this);
        observable.attach(this);
    }

    @Override
    public void actionHappened(Observable<TurnSwitchObserver> observable) {
        actionsLeft --;
        if(actionsLeft == 0){
            observable.detach(this);
            square.removeEffect(this);
        }
    }
}
