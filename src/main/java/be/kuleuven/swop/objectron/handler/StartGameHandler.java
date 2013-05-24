package be.kuleuven.swop.objectron.handler;

import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.exception.InvalidFileException;
import be.kuleuven.swop.objectron.domain.exception.NumberOfPlayersException;
import be.kuleuven.swop.objectron.domain.gamestate.*;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.viewmodel.GameStartViewModel;
import be.kuleuven.swop.objectron.viewmodel.PlayerViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A class of StartGameHandlers involving GameStartViewModels.
 * @author : Nik Torfs
 *         Date: 13/03/13
 *         Time: 01:14
 */
public class StartGameHandler {

    /**
     * Start a new race game.
     * @param playerNames
     *        A list of player names.
     * @param dimension
     *        The dimensions of the grid.
     * @param file
     *        An optional file containing the grid.
     * @return A GameStartViewModel.
     * @throws GridTooSmallException
     *         The specified dimensions result in a grid that is too small.
     *         | dimension.getWidth < 10 || dimension.getHeight < 10
     * @throws InvalidFileException
     *         Generating a grid from the file would result in an invalid grid.
     *         | file.hasUnreachableSquares() || file.StartingPositions != playerNames.size()
     * @throws NumberOfPlayersException
     *         The number of players is invalid for this grid.
     *         | playerNames.size() < 2 || playerNames.size() > 9
     */
    public GameStartViewModel startNewRaceGame(List<String> playerNames,
                                               Dimension dimension, String file) throws GridTooSmallException, InvalidFileException, NumberOfPlayersException {
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
        for(Player player : game.getPlayers()){
            playerViewModels.add(player.getPlayerViewModel());
        }

        return new GameStartViewModel(catalog, dimension, playerViewModels, game.getTurnManager().getCurrentTurn().getViewModel
                (), game.getGrid().getWalls(), game.getGrid().getItems(), game.getGrid().getEffects(), game);
    }

    /**
     * Start a new Capture The Flag game
     * @param playerNames
     *        A list of player names.
     * @param dimension
     *        The dimensions of the grid.
     * @param file
     *        An optional file containing the grid.
     * @return A GameStartViewModel.
     * @throws GridTooSmallException
     *         The specified dimensions result in a grid that is too small.
     *         | dimension.getWidth < 10 || dimension.getHeight < 10
     * @throws InvalidFileException
     *         Generating a grid from the file would result in an invalid grid.
     *         | file.hasUnreachableSquares() || file.StartingPositions != playerNames.size()
     * @throws NumberOfPlayersException
     *         The number of players is invalid for this grid.
     *         | playerNames.size() < 2 || playerNames.size() > 9
     */
    public GameStartViewModel startNewCTFGame(List<String> playerNames,
                                              Dimension dimension, String file) throws GridTooSmallException, InvalidFileException, NumberOfPlayersException {
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
        for(Player player : game.getPlayers()){
            playerViewModels.add(player.getPlayerViewModel());
        }

        return new GameStartViewModel(catalog, dimension, playerViewModels, game.getTurnManager().getCurrentTurn().getViewModel
                (), game.getGrid().getWalls(), game.getGrid().getItems(), game.getGrid().getEffects(), game);
    }
}
