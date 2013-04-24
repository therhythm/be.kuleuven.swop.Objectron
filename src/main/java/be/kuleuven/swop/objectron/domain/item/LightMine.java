package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;

/**
 * @author : Peter Bosmans
 *         Date: 27/02/13
 *         Time: 20:56
 */
public class LightMine implements Item {
    private static final int NB_ACTIONS_BLINDED = 3;

    private static final String name = "Light Mine";

    @Override
    public String getName() {
        return name;
    }

    public void activate(ActivateRequest activateRequest){
       activateRequest.getCurrentTurn().reduceRemainingActions(NB_ACTIONS_BLINDED);
    }

    @Override
    public boolean pickupAble() {
        return true;
    }

    @Override
    public void useItem(UseItemRequest useItemRequest) throws SquareOccupiedException {
        useItemRequest.getSquare().setActiveItem(this);
    }

    public String toString(){
        String result = "";
        result += "name: " + this.getName();

        return result;
    }
    @Override
    public boolean isTeleporting() {
        return false;
    }
}
