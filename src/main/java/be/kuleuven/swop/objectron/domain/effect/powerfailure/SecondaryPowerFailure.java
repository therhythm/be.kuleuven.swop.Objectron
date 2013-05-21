package be.kuleuven.swop.objectron.domain.effect.powerfailure;

import be.kuleuven.swop.objectron.domain.Direction;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas
 * Date: 19/05/13
 * Time: 17:42
 * To change this template use File | Settings | File Templates.
 */
public class SecondaryPowerFailure implements Effect, TurnSwitchObserver {

    public static final int PF_SECONDARY_ACTIONS = 2;
    private boolean active = true;
    private int actionsLeft = PF_SECONDARY_ACTIONS;
    private Square square;

    public SecondaryPowerFailure(Square square, Direction direction, Observable<TurnSwitchObserver> observable) {
        this.square = square;

            initiateTertiaryPowerFailure(direction, observable);
            square.addEffect(this);
            observable.attach(this);

    }

    private void initiateTertiaryPowerFailure(Direction direction, Observable<TurnSwitchObserver> observable) {
        List<Direction> possibleDirections = new ArrayList<Direction>();
        possibleDirections.add(direction);
        possibleDirections.add(direction.previous());
        possibleDirections.add(direction.next());

        int index = (int) Math.floor(Math.random() * possibleDirections.size());
        Square neighbour = square.getNeighbour((possibleDirections.get(index)));
        if(neighbour != null){
            new TertiaryPowerFailure(neighbour, observable);
        }
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
    public void turnEnded(Observable<TurnSwitchObserver> observable) {
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
    public void actionHappened(Observable<TurnSwitchObserver> observable) {
        actionsLeft --;
        if(actionsLeft == 0){
            this.active = false;
            observable.detach(this);
            square.removeEffect(this);
        }
    }
}
