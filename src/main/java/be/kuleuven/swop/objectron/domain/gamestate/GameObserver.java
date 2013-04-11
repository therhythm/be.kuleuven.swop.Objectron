package be.kuleuven.swop.objectron.domain.gamestate;

import be.kuleuven.swop.objectron.domain.util.Position;
import be.kuleuven.swop.objectron.viewmodel.PlayerViewModel;
import be.kuleuven.swop.objectron.viewmodel.TurnViewModel;

import java.util.List;

/**
 * @author : Nik Torfs
 *         Date: 15/03/13
 *         Time: 16:18
 */
public interface GameObserver {

    void update(TurnViewModel vm, List<PlayerViewModel> players);

    void noPower(Position position);

    void regainedPower(Position position);
}
