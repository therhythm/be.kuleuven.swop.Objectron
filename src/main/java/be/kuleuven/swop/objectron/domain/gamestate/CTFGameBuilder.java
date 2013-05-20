package be.kuleuven.swop.objectron.domain.gamestate;

import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.util.Dimension;

import java.util.List;

/**
 * @author : Nik Torfs
 *         Date: 20/05/13
 *         Time: 02:46
 */
public class CTFGameBuilder extends GameBuilder {
    public CTFGameBuilder(List<String> playerNames, Dimension dimension) {
        super(playerNames, dimension);
    }

    @Override
    protected List<Player> initializePLayers() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
