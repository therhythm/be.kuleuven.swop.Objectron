package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.item.effect.Teleporter;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
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

    private void activate(Player player, GameState state) {
        if(player.equals(state.getCurrentPlayer())){
            state.endTurn();
        }

        state.getCurrentTurn().extraTurn();
    }

    @Override
    public void place(Square targetSquare) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void throwMe(Square sourceSquare, Direction targetDirection, GameState state) {
        if (!validDirection(targetDirection)) {
            throw new IllegalArgumentException("No diagonal direction allowed"); //todo domain exception (invariant!)
        }

        identityDiscBehavior.throwMe(sourceSquare, targetDirection, this, state);
    }

    public boolean playerHit(Square square, GameState state) {
        for (Player player : state.getPlayers()) {  //todo state.getplayers could probably removed with the right abstraction..
            if (player.getCurrentSquare().equals(square)) {
                this.activate(player, state);
                return true;
            }
        }
        return false;
    }

    private boolean validDirection(Direction direction) {
        if (direction == Direction.UP_LEFT)
            return false;

        if (direction == Direction.UP_RIGHT)
            return false;

        if (direction == Direction.DOWN_LEFT)
            return false;

        if (direction == Direction.DOWN_RIGHT)
            return false;

        return true;
    }

    public Square getNextSquare(Square currentSquare, Direction direction) {
        Square neighbor = currentSquare.getNeighbour(direction);
        if (neighbor == null)
            return null;
        Teleporter teleportItem = neighbor.getTeleportItem();
        if (teleportItem != null) {
            neighbor = this.teleport(teleportItem);
        }
        return neighbor;
    }

    //todo is teleporting removed.. didn't do anything useful here
    public Square teleport(Teleporter teleporter) {
        return teleporter.getDestination().getLocation();
    }


    public String toString() {
        String result = "";
        result += "name: " + this.getName();

        return result;
    }

}
