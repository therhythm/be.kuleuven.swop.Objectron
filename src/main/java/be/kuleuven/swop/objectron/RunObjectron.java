package be.kuleuven.swop.objectron;

import be.kuleuven.swop.objectron.gui.GameView;
import be.kuleuven.swop.objectron.handler.EndTurnHandler;
import be.kuleuven.swop.objectron.handler.MovePlayerHandler;
import be.kuleuven.swop.objectron.handler.PickUpItemHandler;
import be.kuleuven.swop.objectron.handler.UseItemHandler;
import be.kuleuven.swop.objectron.viewmodel.PlayerViewModel;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

/**
 * Startup class for objectron
 *
 * @author : Nik Torfs
 *         Date: 26/02/13
 *         Time: 20:57
 */
public class RunObjectron {
    @Parameter(names = "-htiles", description = "number of horizontal tiles")
    private int horizontalTiles = 10;

    @Parameter(names = "-vtiles", description = "number of vertical tiles")
    private int verticalTiles = 10;

    @Parameter(names = "-p1", description = "player 1 name")
    private String player1Name = "Player 1";

    @Parameter(names = "-p2", description = "player 2 name")
    private String player2Name = "Player 2";

    public void parseArgs(String[] args) {
        new JCommander(this, args);
    }

    public void run() {
        GameStateImpl state = new GameStateImpl(player1Name, player2Name, horizontalTiles, verticalTiles);

        EndTurnHandler endTurnHandler = new EndTurnHandler(state);
        PickUpItemHandler pickUpItemHandler = new PickUpItemHandler(state);
        MovePlayerHandler movePlayerHandler = new MovePlayerHandler(state);
        UseItemHandler useItemHandler = new UseItemHandler(state);

        PlayerViewModel p1 = state.getCurrentPlayer().getPlayerViewModel();
        state.nextPlayer();
        PlayerViewModel p2 = state.getCurrentPlayer().getPlayerViewModel();
        state.nextPlayer();
        //TODO clean this up maybe a sort of catalog for all the handlers!
        GameView view = new GameView(endTurnHandler, pickUpItemHandler, movePlayerHandler, useItemHandler, horizontalTiles, verticalTiles, p1, p2, state.getGrid().getWalls());
        view.run();
    }

    public static void main(String[] args) {
        RunObjectron runObjectron = new RunObjectron();
        runObjectron.parseArgs(args);
        runObjectron.run();
    }
}
