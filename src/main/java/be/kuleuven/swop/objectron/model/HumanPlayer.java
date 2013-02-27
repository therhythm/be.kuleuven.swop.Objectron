package be.kuleuven.swop.objectron.model;

/**
 * @author : Nik Torfs
 *         Date: 22/02/13
 *         Time: 00:06
 */
public class HumanPlayer implements Player {
    private Square currentSquare;
    private LightTrail lightTrail;

    @Override
    public boolean isInventoryFull() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Square getCurrentSquare() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addToInventory(Item itemToAdd) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void move(Square newPosition) {
        lightTrail.expand(currentSquare);
        currentSquare = newPosition;
        newPosition.setObstructed(true);
    }
}
