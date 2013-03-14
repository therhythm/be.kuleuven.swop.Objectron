package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.Player;

/**
 * @author : Nik Torfs
 *         Date: 21/02/13
 *         Time: 23:52
 */
public interface Item{

    String getName();

    /**
     * activate the item
     */
    void activate(Player player);
}
