package be.kuleuven.swop.objectron.handler;

import be.kuleuven.swop.objectron.GameState;
import be.kuleuven.swop.objectron.GameStateImpl;
import be.kuleuven.swop.objectron.ui.GameView;
import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.viewmodel.PlayerViewModel;

/**
 * @author : Nik Torfs
 *         Date: 13/03/13
 *         Time: 01:14
 */
public class StartGameHandler {

    public void startNewGame(String p1Name, String p2Name, int nbHorizontalTiles, int nbVerticalTiles) throws GridTooSmallException{
        GameState state = new GameStateImpl(p1Name, p2Name, nbHorizontalTiles, nbVerticalTiles);

        EndTurnHandler endTurnHandler = new EndTurnHandler(state);
        PickUpItemHandler pickUpItemHandler = new PickUpItemHandler(state);
        MovePlayerHandler movePlayerHandler = new MovePlayerHandler(state);
        UseItemHandler useItemHandler = new UseItemHandler(state);

        PlayerViewModel p1 = state.getCurrentPlayer().getPlayerViewModel();
        state.nextPlayer();
        PlayerViewModel p2 = state.getCurrentPlayer().getPlayerViewModel();
        state.nextPlayer();
        //TODO clean this up maybe a sort of catalog for all the handlers!
        GameView view = new GameView(endTurnHandler, pickUpItemHandler, movePlayerHandler, useItemHandler, nbHorizontalTiles, nbVerticalTiles, p1, p2, state.getGrid().getWalls());
        view.run();
    }
}
