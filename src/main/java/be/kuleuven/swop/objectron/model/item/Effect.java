package be.kuleuven.swop.objectron.model.item;

import be.kuleuven.swop.objectron.model.Player;

/**
 * @author : Nik Torfs
 *         Date: 10/03/13
 *         Time: 22:21
 */
public interface Effect {
    void activate(Player player);
}
