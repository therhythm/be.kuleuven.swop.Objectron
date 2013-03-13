package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.Player;

/**
 * @author : Nik Torfs
 *         Date: 10/03/13
 *         Time: 22:21
 */
public interface Effect {
    void activate(Player player);
}
