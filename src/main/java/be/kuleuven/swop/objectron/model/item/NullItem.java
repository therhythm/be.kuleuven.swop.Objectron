package be.kuleuven.swop.objectron.model.item;

import be.kuleuven.swop.objectron.model.Player;
import be.kuleuven.swop.objectron.model.Square;
import be.kuleuven.swop.objectron.model.exception.SquareOccupiedException;

/**
 * @author : Nik Torfs
 *         Date: 01/03/13
 *         Time: 13:37
 */
public class NullItem implements Item {
    private static final String name = "No Item";

    @Override
    public void use(Square square) throws SquareOccupiedException {
        //do nothing, null object
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void activate(Player player) {
        //do nothing, null object
    }

    @Override
    public boolean isNull() {
        return true;
    }
}
