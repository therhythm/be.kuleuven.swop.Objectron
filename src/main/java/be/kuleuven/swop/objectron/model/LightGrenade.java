package be.kuleuven.swop.objectron.model;

/**
 * @author : Kasper Vervaecke
 *         Date: 25/02/13
 *         Time: 18:22
 */
public class LightGrenade implements Item {

    private int identifier;

    /**
     * Use the item on the specified square
     *
     * @param square: square where the item is used
     */
    @Override
    public void use(Square square) {

    }

    @Override
    public int getIdentifier() {
        return identifier;
    }

    @Override
    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }
}
