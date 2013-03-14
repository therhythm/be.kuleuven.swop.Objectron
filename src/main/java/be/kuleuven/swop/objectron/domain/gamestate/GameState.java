package be.kuleuven.swop.objectron.domain.gamestate;

import be.kuleuven.swop.objectron.domain.Grid;
import be.kuleuven.swop.objectron.domain.Player;

/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 12/03/13
 * Time: 15:13
 * To change this template use File | Settings | File Templates.
 */
public interface GameState {
    Player getCurrentPlayer();

    Grid getGrid();

    void nextPlayer();
}
