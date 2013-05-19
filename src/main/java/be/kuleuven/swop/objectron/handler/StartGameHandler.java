package be.kuleuven.swop.objectron.handler;

import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.gamestate.CTFGame;
import be.kuleuven.swop.objectron.domain.gamestate.Game;
import be.kuleuven.swop.objectron.domain.gamestate.RaceGame;
import be.kuleuven.swop.objectron.domain.grid.GeneratedGridBuilder;
import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.grid.GridBuilder;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.viewmodel.GameStartViewModel;
import be.kuleuven.swop.objectron.viewmodel.PlayerViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Nik Torfs
 *         Date: 13/03/13
 *         Time: 01:14
 */
public class StartGameHandler {

    public GameStartViewModel startNewRaceGame(List<String> playerNames,
                                               Dimension dimension) throws GridTooSmallException {
        GridBuilder builder = new GeneratedGridBuilder(dimension, playerNames.size());
        builder.buildWalls();
        builder.buildItems();
        Grid grid = builder.buildGrid();

        Game game = new RaceGame(playerNames, grid);// todo building the dependency graph the right way and inject it
        // all

        HandlerCatalog catalog = new HandlerCatalog();
        catalog.addHandler(new EndTurnHandler(game));
        catalog.addHandler(new PickUpItemHandler(game));
        catalog.addHandler(new MovePlayerHandler(game));
        catalog.addHandler(new UseItemHandler(game));

        List<PlayerViewModel> playerViewModels = new ArrayList<>();
        //todo fill list

        PlayerViewModel p1 = game.getPlayers().get(0).getPlayerViewModel();
        PlayerViewModel p2 = game.getPlayers().get(1).getPlayerViewModel();

        return new GameStartViewModel(catalog, dimension, p1, p2, game.getTurnManager().getCurrentTurn().getViewModel
                (), game.getGrid().getWalls(), game.getGrid().getItems(), game.getGrid().getEffects(), game);
    }

    public GameStartViewModel startNewCTFGame(List<String> playerNames,
                                              Dimension dimension) throws GridTooSmallException {
        GridBuilder builder = new GeneratedGridBuilder(dimension, playerNames.size());
        builder.buildWalls();
        builder.buildItems();
        Grid grid = builder.buildGrid();

        Game game = new CTFGame(playerNames, grid);     // todo building the dependency graph the right way and
        // inject it all

        HandlerCatalog catalog = new HandlerCatalog();
        catalog.addHandler(new EndTurnHandler(game));
        catalog.addHandler(new PickUpItemHandler(game));
        catalog.addHandler(new MovePlayerHandler(game));
        catalog.addHandler(new UseItemHandler(game));

        List<PlayerViewModel> playerViewModels = new ArrayList<>();
        //todo fill list

        PlayerViewModel p1 = game.getPlayers().get(0).getPlayerViewModel();
        PlayerViewModel p2 = game.getPlayers().get(1).getPlayerViewModel();

        return new GameStartViewModel(catalog, dimension, p1, p2, game.getTurnManager().getCurrentTurn().getViewModel(), game.getGrid().getWalls(), game.getGrid().getItems(), game.getGrid().getEffects(), game);
    }
}
