package be.kuleuven.swop.objectron.handler;

import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.viewmodel.GameStartViewModel;
import be.kuleuven.swop.objectron.viewmodel.PlayerViewModel;

/**
 * @author : Nik Torfs
 *         Date: 13/03/13
 *         Time: 01:14
 */
public class StartGameHandler {

    public GameStartViewModel startNewGame(String p1Name, String p2Name, Dimension dimension) throws GridTooSmallException{
        GameState state = new GameState(p1Name, p2Name, dimension);

        HandlerCatalog catalog = new HandlerCatalog();
        catalog.addHandler(new EndTurnHandler(state));
        catalog.addHandler(new PickUpItemHandler(state));
        catalog.addHandler(new MovePlayerHandler(state));
        catalog.addHandler(new UseItemHandler(state));

        PlayerViewModel p1 = state.getPlayers().get(0).getPlayerViewModel();
        PlayerViewModel p2 = state.getPlayers().get(1).getPlayerViewModel();

        return new GameStartViewModel(catalog, dimension, p1, p2, state.getCurrentTurn().getViewModel(), state.getGrid().getWalls(), state.getGrid().getItems(), state);
    }
}
