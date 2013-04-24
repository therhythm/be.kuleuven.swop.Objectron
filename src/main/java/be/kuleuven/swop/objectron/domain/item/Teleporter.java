package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.square.Square;

/**
 * @author : Kasper Vervaecke
 *         Date: 09/04/13
 *         Time: 16:32
 */
public class Teleporter implements Item {
    private static final String name = "Teleporter";

    private Teleporter destination;
    private Square location;

    public Teleporter(Square location) {
        this.location = location;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean pickupAble() {
        return false;
    }

    @Override
    public void activate(ActivateRequest activateRequest) {
        if (activateRequest.getCurrentPlayer() != null) {
            if (!activateRequest.getCurrentPlayer().isTeleporting() && !destination.getLocation().isObstructed()) {
                activateRequest.getCurrentPlayer().teleport(destination.getLocation());
            }
        /*}else{
            if(activateRequest.getIdentityDisc()!=null){
                if (!activateRequest.getIdentityDisc().isTeleporting() && !destination.getLocation().isObstructed()) {
                    activateRequest.setDestinationSquare(destination.getLocation());
                }
            }*/
        }


    }

    @Override
    public void useItem(UseItemRequest useItemRequest) throws SquareOccupiedException {
        //Teleporters can not be used, only activated.
    }

    @Override
    public boolean isTeleporting() {
        return false;
    }

    public Teleporter getDestination() {
        return destination;
    }

    public void setDestination(Teleporter destination) {
        this.destination = destination;
    }

    public Square getLocation() {
        return location;
    }
}
