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

    void update(TurnViewModel vm, List<PlayerViewModel> players, GridViewModel gridModel);

    void itemPlaced(Item item, Position position);
}
