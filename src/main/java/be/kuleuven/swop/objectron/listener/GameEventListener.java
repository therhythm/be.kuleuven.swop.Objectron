package be.kuleuven.swop.objectron.listener;

import be.kuleuven.swop.objectron.viewmodel.PlayerViewModel;

import java.util.List;

/**
 * @author : Nik Torfs
 *         Date: 01/03/13
 *         Time: 10:03
 */
public interface GameEventListener {
    void playerUpdated(PlayerViewModel playerViewModel);

}
