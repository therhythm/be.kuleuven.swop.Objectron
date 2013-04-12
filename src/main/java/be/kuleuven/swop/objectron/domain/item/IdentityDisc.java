package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
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
    private boolean isTeleporting = false;

    public IdentityDisc(IdentityDiscBehavior identityDiscBehavior) {
        this.identityDiscBehavior = identityDiscBehavior;

    }

    @Override
    public String getName() {
        return identityDiscBehavior.getName();
    }

    @Override
    public boolean pickupAble() {
        return true;
    }

    @Override
    public void activate(ActivateRequest activateRequest) {
        if (activateRequest.getPlayerHit().equals(activateRequest.getCurrentPlayer()))
            activateRequest.getGamestate().endTurn();

        activateRequest.getCurrentTurn().extraTurn();
    }


    @Override
    public void useItem(UseItemRequest useItemRequest) throws SquareOccupiedException {
        if (!validDirection(useItemRequest.getDirection()))
            throw new IllegalArgumentException("the direction can't be diagonal");
        identityDiscBehavior.useItem(useItemRequest, this);
    }

    @Override
    public boolean isTeleporting() {
        return this.isTeleporting;
    }

    public boolean playerHit(UseItemRequest useItemRequest, Square squareItem) {
        for (Player player : useItemRequest.getPlayers()) {
            if (player.getCurrentSquare().equals(squareItem)) {
                this.activate(new ActivateRequest(player, useItemRequest.getGameState()));
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

    public Square getNextSquare(Square currentSquare,Direction direction){
        Square neighbor = currentSquare.getNeighbour(direction);
        if(neighbor==null)
            return null;
        Teleporter teleportItem = neighbor.getTeleportItem();
        if(teleportItem!=null){
            neighbor = this.teleport(teleportItem);
        }
        return neighbor;
    }

    public Square teleport(Teleporter teleporter) {
        isTeleporting = true;
        Square destination = teleporter.getDestination().getLocation();
        // if(destination.getTeleportItem()!=null)
        //    return teleport(destination.getTeleportItem());

        isTeleporting = false;
        return destination;
    }


    public String toString() {
        String result = "";
        result += "name: " + this.getName();

        return result;
    }

}
