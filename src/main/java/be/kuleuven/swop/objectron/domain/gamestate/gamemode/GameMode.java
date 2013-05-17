package be.kuleuven.swop.objectron.domain.gamestate.gamemode;

import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.grid.Grid;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 15/05/13
 * Time: 21:44
 * To change this template use File | Settings | File Templates.
 */
public interface GameMode {
     void initialize(List<Player> players);

}
