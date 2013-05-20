package be.kuleuven.swop.objectron.domain.item.effect;

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

/**
 * Created with IntelliJ IDEA.
 * User: Thomas
 * Date: 19/05/13
 * Time: 17:21
 * To change this template use File | Settings | File Templates.
 */
public class PrimaryPowerFailure implements Effect, TurnSwitchObserver {

    public static final int PF_PRIMARY_TURNS = 3;

    private boolean clockWise = false;
    private Direction prevDirection;
    private int rotateCounter;
    private Square square;
    private boolean active = true;

    private int turnsLeft = PF_PRIMARY_TURNS;

    public PrimaryPowerFailure(Square square, Observable<TurnSwitchObserver> observable){
        this.square = square;
        square.addEffect(this);
        initiateSecondaryPowerFailure(observable);
        observable.attach(this);
    }

    private void initiateSecondaryPowerFailure(Observable<TurnSwitchObserver> observable){
        if(Math.random() < 0.5){
            this.clockWise = true;
        }

        int startSquare = (int) Math.floor(Math.random() * Direction.values().length);
        int i = 0;
        for (Direction direction: Direction.values()){
           if(i == startSquare){
               if(square.getNeighbour(direction) != null){
                    new SecondaryPowerFailure(square.getNeighbour(direction), direction, observable);
               }
               this.prevDirection = direction;
           }
           i++;
        }
    }

    private void rotate(Observable<TurnSwitchObserver> observable){
        rotateCounter ++;
        if(rotateCounter >= 2){
            rotateCounter = 0;
            if(clockWise)
                this.prevDirection = prevDirection.next();
            else
                this.prevDirection = prevDirection.previous();

            Square secondaryFailure =  square.getNeighbour(prevDirection);
            if(secondaryFailure != null){
                new SecondaryPowerFailure(secondaryFailure, prevDirection, observable);
            }

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
        turnsLeft --;
        if(turnsLeft == 0){
            active = false;
            observable.detach(this);
        }
    }

    @Override
    public void update(Turn turn) {
        //nothing to do
    }

    @Override
    public void actionReduced() {
       //do nothing
    }

    @Override
    public void actionHappened(Observable<TurnSwitchObserver> observable) {
        rotate(observable);
    }
}
