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
    private IdentityDiscType identityDiscType;

    public IdentityDisc(IdentityDiscTypeState identityDiscTypeEnum) {
        switch (identityDiscTypeEnum) {
            case CHARGED_IDENTITY_DISC:
                setIdentityState(new ChargedIdentityDisc());
                break;
            case UNCHARGED_IDENTITY_DISC:
                setIdentityState(new UnchargedIdentityDisc());
                break;
        }
    }

    private void setIdentityState(IdentityDiscType identityDiscType) {
        this.identityDiscType = identityDiscType;
    }

    @Override
    public String getName() {
      return  identityDiscType.getName();
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
        identityDiscType.useItem(useItemRequest, this);
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

    public String toString() {
        String result = "";
        result += "name: " + this.getName();

        return result;
    }

    public enum IdentityDiscTypeState {
        CHARGED_IDENTITY_DISC, UNCHARGED_IDENTITY_DISC;
    }
}
