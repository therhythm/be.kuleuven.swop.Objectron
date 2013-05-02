package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.item.effect.Teleporter;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.square.Square;

/**
 * Created with IntelliJ IDEA.
 * User: Peter
 * Date: 8/04/13
 * Time: 20:04
 * To change this template use File | Settings | File Templates.
 */
public class IdentityDisc implements Item {
    private IdentityDiscBehavior identityDiscBehavior;

    public IdentityDisc(IdentityDiscBehavior identityDiscBehavior) {
        this.identityDiscBehavior = identityDiscBehavior;

    }

    @Override
    public String getName() {
        return identityDiscBehavior.getName();
    }

    private void activate(Player player, TurnManager turnManager) {
        if (player.equals(turnManager.getCurrentTurn().getCurrentPlayer())) {
            turnManager.endTurn();
        }

        turnManager.getCurrentTurn().extraTurn();
    }

    @Override
    public void place(Square targetSquare) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void throwMe(Square sourceSquare, Direction targetDirection, TurnManager turnManager) {
        if (!validDirection(targetDirection)) {
            throw new IllegalArgumentException("No diagonal direction allowed"); //todo domain exception (invariant!)
        }

        Square currentSquare = sourceSquare;
        Square neighbor = getNextSquare(currentSquare, targetDirection);

        //todo clean>>?
        while (identityDiscBehavior.getRemainingRange() > 0) {
            if (neighbor == null)
                break;
            if (playerHit(neighbor, turnManager)) {//todo find a way to hit players .. maybe make player an
            // obstruction?? REMOVE player
                currentSquare = neighbor;
                break;
            } else /*!state.getGrid().isWall(neighbor)*/ { //todo let obstruction handle obstructions remove grid
                currentSquare = neighbor;
                neighbor = getNextSquare(currentSquare, targetDirection);
                identityDiscBehavior.moved();
            }
        }
        currentSquare.addItem(this);

        //identityDiscBehavior.throwMe(sourceSquare, targetDirection, this, turnManager);
    }

    public boolean playerHit(Square square, TurnManager turnManager) {
        for (Player player : turnManager.getPlayers()) {  //todo getplayers could probably removed with the right
        // abstraction..
            if (player.getCurrentSquare().equals(square)) {
                this.activate(player, turnManager);
                return true;
            }
        }
        return false;
    }

    @Override
    public void addToSquare(Square targetSquare) {
    }

    private boolean validDirection(Direction direction) {
        return direction != Direction.UP_LEFT && direction != Direction.UP_RIGHT && direction != Direction.DOWN_LEFT
                && direction != Direction.DOWN_RIGHT;
    }

    public Square getNextSquare(Square currentSquare, Direction direction) {
        Square neighbor = currentSquare.getNeighbour(direction);
        if (neighbor == null)
            return null;

        //todo this should be handled by the effect abstraction
        /*Teleporter teleportItem = neighbor.getTeleportItem();
        if (teleportItem != null) {
            neighbor = this.teleport(teleportItem);
        } */
        return neighbor;
    }

    public String toString() {
        String result = "";
        result += "name: " + this.getName();

        return result;
    }

}
