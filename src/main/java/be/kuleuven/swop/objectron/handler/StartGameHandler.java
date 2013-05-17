package be.kuleuven.swop.objectron.handler;

import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.gamestate.gamemode.RaceMode;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.viewmodel.GameStartViewModel;
import be.kuleuven.swop.objectron.viewmodel.PlayerViewModel;
import com.sun.xml.internal.org.jvnet.fastinfoset.stax.LowLevelFastInfosetStreamWriter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Nik Torfs
 *         Date: 13/03/13
 *         Time: 01:14
 */
public class StartGameHandler {

    public GameStartViewModel startNewGame(String p1Name, String p2Name, Dimension dimension) throws GridTooSmallException {
       List<String> players = new ArrayList<String>();
        players.add(p1Name);
        players.add(p2Name);

        //todo implement gamemode choice
        GameState state = new GameState(players, dimension,new RaceMode());

        HandlerCatalog catalog = new HandlerCatalog();
        catalog.addHandler(new EndTurnHandler(state));
        catalog.addHandler(new PickUpItemHandler(state));
        catalog.addHandler(new MovePlayerHandler(state));
        catalog.addHandler(new UseItemHandler(state));

        PlayerViewModel p1 = state.getPlayers().get(0).getPlayerViewModel();
        PlayerViewModel p2 = state.getPlayers().get(1).getPlayerViewModel();

        return new GameStartViewModel(catalog, dimension, p1, p2, state.getTurnManager().getCurrentTurn().getViewModel(), state.getGrid().getWalls(), state.getGrid().getItems(),state.getGrid().getEffects(), state);
    }
}
