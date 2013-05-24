package be.kuleuven.swop.objectron.domain.gamestate;

import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.util.Position;
import be.kuleuven.swop.objectron.viewmodel.GridViewModel;
import be.kuleuven.swop.objectron.viewmodel.PlayerViewModel;
import be.kuleuven.swop.objectron.viewmodel.TurnViewModel;

import java.util.List;

/**
 * @author : Nik Torfs
 *         Date: 15/03/13
 *         Time: 16:18
 */
public interface GameObserver {

    /**
     * Receive an update a bout the game
     * @param vm A viewmodel of the turn
     * @param players A list of viewmodels of the players
     * @param gridModel A viewmodel of the grid
     */
    void update(TurnViewModel vm, List<PlayerViewModel> players, GridViewModel gridModel);

    void gameWon(PlayerViewModel player);
}
