package be.kuleuven.swop.objectron.handler;

import be.kuleuven.swop.objectron.domain.game.Game;
import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
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
        Game state = new Game(p1Name, p2Name, dimension);

        HandlerCatalog catalog = new HandlerCatalog();
        catalog.addHandler(new EndTurnHandler(state));
        catalog.addHandler(new PickUpItemHandler(state));
        catalog.addHandler(new MovePlayerHandler(state));
        catalog.addHandler(new UseItemHandler(state));

        PlayerViewModel p1 = state.getCurrentPlayer().getPlayerViewModel();
        state.endTurn();
        PlayerViewModel p2 = state.getCurrentPlayer().getPlayerViewModel();
        state.endTurn();
        return new GameStartViewModel(catalog, dimension, p1, p2, state.getCurrentTurn().getViewModel(), state.getGrid().getWalls(), state);
    }
}
