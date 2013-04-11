package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.Settings;
import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.square.Square;

/**
 * Created with IntelliJ IDEA.
 * User: Peter
 * Date: 8/04/13
 * Time: 20:04
 * To change this template use File | Settings | File Templates.
 */
public class IdentityDisc implements Item {
    private static final String name = "Identity Disc";
    private int maxRange = 4;
    private IdentityDiscType identityDiscType;

    @Override
    public String getName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void activate(Turn currentTurn) {
        currentTurn.reduceRemainingActions(Settings.LIGHTMINE_NB_ACTIONS_BLINDED);
    }

    @Override
    public void activate(Player player) {
        //TODO fix action to skipTurn

    }

    @Override
    public void useItem(UseItemRequest useItemRequest) throws SquareOccupiedException {
        if(!validDirection(useItemRequest.getDirection()))
            throw new IllegalArgumentException("the direction can't be diagonal");
        Square currentSquare = useItemRequest.getSquare();
        Square neighbor =  currentSquare.getNeighbour(useItemRequest.getDirection());
        for(int i = 0;i<maxRange;i++){
            System.out.println(neighbor);
            if (neighbor == null)
                break;
            if(playerHit(useItemRequest,neighbor)){
                currentSquare = neighbor;
                break;

            }
            else if (!useItemRequest.getGrid().isWall(neighbor)){
                currentSquare = neighbor;
                neighbor = neighbor.getNeighbour(useItemRequest.getDirection());
            } else
                break;
        }
        System.out.println("currentSquare = " + currentSquare);
        currentSquare.addItem(this);
    }

    private boolean playerHit(UseItemRequest useItemRequest,Square squareItem){
     for(Player player : useItemRequest.getPlayers() ){
         if(player.getCurrentSquare().equals(squareItem))      {
             this.activate(player);
             return true;
         }
     }
        return false;
    }

    private boolean validDirection(Direction direction){
         if(direction == Direction.UP_LEFT)
             return false;

        if(direction == Direction.UP_RIGHT)
            return false;

        if(direction == Direction.DOWN_LEFT)
            return false;

        if(direction == Direction.DOWN_RIGHT)
            return false;

        return true;
    }
}
