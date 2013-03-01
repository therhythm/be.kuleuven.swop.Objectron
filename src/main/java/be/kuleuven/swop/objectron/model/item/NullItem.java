package be.kuleuven.swop.objectron.model.item;

import be.kuleuven.swop.objectron.model.Square;
import be.kuleuven.swop.objectron.model.SquareOccupiedException;

/**
 * @author : Nik Torfs
 *         Date: 01/03/13
 *         Time: 13:37
 */
public class NullItem implements Item{
    private static ItemSpecification itemSpecification;

    static{
        itemSpecification = new ItemSpecification("No Item", "");
    }

    @Override
    public void use(Square square) throws SquareOccupiedException {
        //TODO
    }

    @Override
    public ItemSpecification getSpecification() {
        return itemSpecification;
    }
}
