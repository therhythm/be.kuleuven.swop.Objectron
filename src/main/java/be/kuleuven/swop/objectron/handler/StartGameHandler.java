package be.kuleuven.swop.objectron.handler;

import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.gamestate.GameStateImpl;
import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.viewmodel.GameStartViewModel;
import be.kuleuven.swop.objectron.viewmodel.PlayerViewModel;

/**
 * @author : Nik Torfs
 *         Date: 13/03/13
 *         Time: 01:14
 */
public class StartGameHandler {

    public GameStartViewModel startNewGame(String p1Name, String p2Name, int nbHorizontalTiles, int nbVerticalTiles) throws GridTooSmallException{
        GameState state = new GameStateImpl(p1Name, p2Name, nbHorizontalTiles, nbVerticalTiles);

        HandlerCatalog catalog = new HandlerCatalog();
        catalog.addHandler(new EndTurnHandler(state));
        catalog.addHandler(new PickUpItemHandler(state));
        catalog.addHandler(new MovePlayerHandler(state));
        catalog.addHandler(new UseItemHandler(state));

        PlayerViewModel p1 = state.getCurrentPlayer().getPlayerViewModel();
        state.nextPlayer();
        PlayerViewModel p2 = state.getCurrentPlayer().getPlayerViewModel();
        state.nextPlayer();
        //TODO clean this up maybe a sort of catalog for all the handlers!
        return new GameStartViewModel(catalog, nbHorizontalTiles, nbVerticalTiles, p1, p2, state.getGrid().getWalls(), state);
    }
}
