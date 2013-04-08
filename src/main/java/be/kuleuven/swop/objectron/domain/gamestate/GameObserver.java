package be.kuleuven.swop.objectron.domain.gamestate;

import be.kuleuven.swop.objectron.viewmodel.PlayerViewModel;

/**
 * @author : Nik Torfs
 *         Date: 15/03/13
 *         Time: 16:18
 */
public interface GameObserver {

    void playerUpdated(PlayerViewModel vm);
}
