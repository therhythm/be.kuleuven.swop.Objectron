package be.kuleuven.swop.objectron.handler;

import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.exception.InvalidFileException;
import be.kuleuven.swop.objectron.domain.gamestate.*;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.viewmodel.GameStartViewModel;
import be.kuleuven.swop.objectron.viewmodel.PlayerViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Nik Torfs
 *         Date: 13/03/13
 *         Time: 01:14
 */
public class StartGameHandler {

    public GameStartViewModel startNewRaceGame(List<String> playerNames,
                                               Dimension dimension, String file) throws GridTooSmallException, InvalidFileException {
        GameBuilder gameBuilder = new RaceGameBuilder(playerNames, dimension);
        if(!file.isEmpty()){
            gameBuilder.withFile(file);
        }
        gameBuilder.withItems();
        gameBuilder.withWalls();

        Game game = gameBuilder.buildGame();

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
                                              Dimension dimension, String file) throws GridTooSmallException, InvalidFileException {
        GameBuilder gameBuilder = new CTFGameBuilder(playerNames, dimension);
        if(!file.isEmpty()){
            gameBuilder.withFile(file);
        }

        //todo this should probably be standard..
        gameBuilder.withItems();
        gameBuilder.withWalls();

        Game game = gameBuilder.buildGame();

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
}
