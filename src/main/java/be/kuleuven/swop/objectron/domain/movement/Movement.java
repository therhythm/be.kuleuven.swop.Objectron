package be.kuleuven.swop.objectron.domain.movement;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.LightTrail;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.Wall;
import be.kuleuven.swop.objectron.domain.exception.*;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.item.forceField.ForceField;
import be.kuleuven.swop.objectron.domain.square.Square;

/**
 * @author : Nik Torfs
 *         Date: 24/05/13
 *         Time: 13:14
 */
public class Movement {
    public static final int POWERFAILURE_ACTION_REDUCTION = 1;

    private Movable movable;
    private Direction direction;
    protected Square currentSquare;
    protected TurnManager manager;
    protected MovementRangeStrategy movementRangeStrategy;
    private boolean isTeleporting = false;



    public Movement(Movable movable, Direction direction, Square currentSquare,
                    MovementRangeStrategy movementRangeStrategy, TurnManager manager) {
        this.movable = movable;
        this.direction = direction;
        this.currentSquare = currentSquare;
        this.movementRangeStrategy = movementRangeStrategy;
        this.manager = manager;
    }

    public void move(){
        // iterator could make this easier to use, move the responsibility to movement Range Strategy to reduce the range
        while(movementRangeStrategy.hasNext()){
            try {
                moveOneStep();
            } catch (InvalidMoveException e) {
                break; //stop looping.. it's over
            }

            movementRangeStrategy.moved();
        }
    }

    private void moveOneStep() throws InvalidMoveException {
        Square neighbour = currentSquare.getNeighbour(direction);
        if(neighbour != null){
            newSquare(neighbour);
        }else{
            throw new InvalidMoveException();
        }
    }

    //wrap isTeleporting boolean?
    public void teleport(Square newSquare){
        disrupted();
        if(isTeleporting){
            return;
        }

        isTeleporting = true;
        try {
            newSquare(newSquare);
        } catch (InvalidMoveException e) {
            //todo
        }
        isTeleporting = false;
    }

    private void newSquare(Square newSquare) throws InvalidMoveException {
        Square temp = currentSquare;
        newSquare.stepOn(this, manager);
        if(currentSquare.equals(temp)){
            currentSquare = newSquare;
        }
    }

    public void hitPlayer(Player player) throws InvalidMoveException {
        throw new InvalidMoveException();
    }

    public void hitWall(Wall wall) throws InvalidMoveException {
        throw new InvalidMoveException();
    }

    public void hitForceField(ForceField forceField) throws InvalidMoveException {
        throw new InvalidMoveException();
    }

    public void hitLightTrail(LightTrail trail) throws InvalidMoveException {
        throw new InvalidMoveException();
    }

    public void disrupted() {
        movable.disrupted();
    }

    public Movable getMovable() {
        return movable;
    }

    public Square getCurrentSquare() {
        return currentSquare;
    }

    public void powerFailure(boolean hasLightMine) {
        if(hasLightMine){
            manager.getCurrentTurn().addPenalty(POWERFAILURE_ACTION_REDUCTION);
        } else{
            manager.getCurrentTurn().setMoved();
            manager.endTurn();
        }
    }
}
